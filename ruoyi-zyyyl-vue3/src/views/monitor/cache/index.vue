<template>
  <div class="app-container">
    <div class="page-toolbar"><el-button :icon="Refresh" circle title="刷新缓存报表" @click="getList" /></div>
    <el-tabs v-model="activeTab">
      <el-tab-pane label="总览" name="overview">
        <el-row :gutter="16" class="summary-row"><el-col v-for="card in summaryCards" :key="card.label" :xl="4" :lg="6"
            :md="8" :sm="12" :xs="24" class="card-box"><el-card shadow="hover" class="summary-card">
              <div class="summary-label">{{ card.label }}</div>
              <div class="summary-value">{{ card.value }}</div>
              <div v-if="card.extra" class="summary-extra">{{ card.extra }}</div>
            </el-card></el-col></el-row>
        <el-row :gutter="16">
          <el-col :span="24" class="card-box">
            <el-card>
              <template #header>
                <div class="card-header">
                  <span>JetCache 配置概览</span>
                  <el-tag type="primary">
                    {{ cacheSummary.cacheType || '-' }}
                  </el-tag>
                </div>
              </template>
              <el-descriptions :column="4" border>
                <el-descriptions-item label="Area">
                  {{ cacheSummary.area || '-' }}
                </el-descriptions-item>
                <el-descriptions-item label="本地 Provider">
                  {{ cacheSummary.localProvider || '-' }}
                </el-descriptions-item>
                <el-descriptions-item label="远端 Provider">
                  {{ cacheSummary.remoteProvider || '未启用' }}
                </el-descriptions-item>
                <el-descriptions-item label="统计周期">
                  {{ cacheSummary.statIntervalMinutes || 0 }}
                  分钟
                </el-descriptions-item>
                <el-descriptions-item label="默认 TTL">
                  {{ cacheSummary.defaultExpire || '-' }}
                </el-descriptions-item>
                <el-descriptions-item label="本地 TTL">
                  {{ cacheSummary.localExpire || '-' }}
                </el-descriptions-item>
                <el-descriptions-item label="本地容量">
                  {{ cacheSummary.localLimit || 0
                  }}</el-descriptions-item>
                <el-descriptions-item label="多级缓存">
                  {{ cacheSummary.multiLevelEnabled ? '已开启' : '未开启' }}
                </el-descriptions-item>
                <el-descriptions-item label="本地同步">
                  {{ cacheSummary.syncLocal ? '已开启' : '未开启' }}
                </el-descriptions-item>
                <el-descriptions-item label="穿透保护">
                  {{ cacheSummary.penetrationProtect ? '已开启' : '未开启' }}
                </el-descriptions-item>
                <el-descriptions-item label="活跃缓存">
                  {{ cacheSummary.activeCacheCount || 0 }}
                </el-descriptions-item>
                <el-descriptions-item label="监控缓存">
                  {{ cacheSummary.cacheCount || 0 }}
                </el-descriptions-item>
              </el-descriptions>
            </el-card>
          </el-col>
          <el-col :lg="12" :xs="24" class="card-box"><el-card><template #header><span>缓存键数量分布</span></template>
              <div ref="keyChartRef" class="chart-panel" />
            </el-card>
          </el-col>
          <el-col :lg="12" :xs="24" class="card-box">
            <el-card>
              <template #header>
                <span>命中率 / QPS</span>
              </template>
              <div ref="hitRateChartRef" class="chart-panel" />
            </el-card>
          </el-col>
          <el-col :span="24" class="card-box">
            <el-card><template #header>
                <span>缓存统计明细</span>
              </template>
              <el-table :data="cacheStats" stripe>
                <el-table-column label="缓存名称" prop="cacheName" min-width="180" show-overflow-tooltip>
                  <template #default="scope">{{ displayCacheName(scope.row.cacheName) }}</template>
                </el-table-column>
                <el-table-column label="键数量" prop="keyCount" width="100" align="center" />
                <el-table-column label="命中率" width="100" align="center"><template #default="scope">
                    {{ formatPercent(scope.row.hitRate) }}
                  </template>
                </el-table-column>
                <el-table-column label="QPS" prop="qps" width="100" align="center" /><el-table-column label="GET"
                  prop="getCount" width="100" align="center" />
                <el-table-column label="PUT" prop="putCount" width="100" align="center" />
                <el-table-column label="REMOVE" prop="removeCount" width="100" align="center" />
                <el-table-column label="LOAD" prop="loadCount" width="100" align="center" />
                <el-table-column label="默认 TTL" prop="defaultExpire" width="110" align="center" />
                <el-table-column label="操作" width="90" fixed="right"><template #default="scope">
                    <el-button link type="primary" @click="openCacheList(scope.row.cacheName)">查看</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane v-for="provider in visibleProviders" :key="provider.id" :name="providerTabName(provider)" lazy>
        <template #label>
          <span>{{ provider.name }}</span>
          <el-tag class="provider-status" size="small" :type="provider.available ? 'success' : 'danger'">
            {{ provider.available ? '运行中' : '不可用' }}</el-tag>
        </template>
        <ProviderReport :provider="provider" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup name="Cache">
import { Refresh } from '@element-plus/icons-vue'
import { getCacheReport } from '@/api/monitor/cache'
import ProviderReport from './components/ProviderReport.vue'
import * as echarts from 'echarts'
import { computed, getCurrentInstance, nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'

const { proxy } = getCurrentInstance()
const activeTab = ref('overview')
const cacheSummary = ref({})
const cacheStats = ref([])
const providerReports = ref([])
const keyChartRef = ref(null)
const hitRateChartRef = ref(null)
let keyChart
let hitRateChart
const visibleProviders = computed(() => providerReports.value.filter(provider => provider.enabled))
const summaryCards = computed(() => [
  { label: '缓存模式', value: cacheSummary.value.cacheType || '-', extra: (cacheSummary.value.localProvider || '-') + ' / ' + (cacheSummary.value.remoteProvider || '无远端') },
  { label: '监控缓存数', value: cacheSummary.value.cacheCount || 0, extra: '活跃 ' + (cacheSummary.value.activeCacheCount || 0) },
  { label: '缓存键数量', value: cacheSummary.value.applicationKeyCount || cacheSummary.value.keyCount || 0, extra: 'Area: ' + (cacheSummary.value.area || '-') },
  { label: '命中率', value: formatPercent(cacheSummary.value.hitRate), extra: 'GET ' + (cacheSummary.value.getCount || 0) + ' / HIT ' + (cacheSummary.value.hitCount || 0) },
  { label: 'QPS', value: cacheSummary.value.qps || 0, extra: 'PUT ' + (cacheSummary.value.putCount || 0) + ' / REMOVE ' + (cacheSummary.value.removeCount || 0) },
  { label: '默认 TTL', value: cacheSummary.value.defaultExpire || '-', extra: '本地 TTL ' + (cacheSummary.value.localExpire || '-') }
])
function formatPercent(value) { return Number(value || 0).toFixed(2) + '%' }
function displayCacheName(value) { return String(value || '-').replace(/:$/, '') }
function openCacheList(cacheName) { proxy.$router.push({ path: '/monitor/cache/list', query: { cacheName: displayCacheName(cacheName) } }) }
function providerTabName(provider) { return 'provider:' + provider.id }
function buildKeyChart() {
  if (!keyChartRef.value) return; keyChart && keyChart.dispose();
  keyChart = echarts.init(keyChartRef.value, 'macarons');
  const topCaches = [...cacheStats.value].sort((a, b) => (b.keyCount || 0) - (a.keyCount || 0)).slice(0, 8); keyChart.setOption({ tooltip: { trigger: 'axis' }, grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true }, xAxis: { type: 'category', data: topCaches.map(item => displayCacheName(item.cacheName)), axisLabel: { interval: 0, rotate: 20 } }, yAxis: { type: 'value' }, series: [{ name: '缓存键数量', type: 'bar', barWidth: '40%', data: topCaches.map(item => item.keyCount || 0) }] })
}
function buildHitRateChart() { if (!hitRateChartRef.value) return; hitRateChart && hitRateChart.dispose(); hitRateChart = echarts.init(hitRateChartRef.value, 'macarons'); const topCaches = [...cacheStats.value].sort((a, b) => (b.getCount || 0) - (a.getCount || 0)).slice(0, 8); hitRateChart.setOption({ tooltip: { trigger: 'axis' }, legend: { data: ['命中率', 'QPS'] }, grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true }, xAxis: { type: 'category', data: topCaches.map(item => displayCacheName(item.cacheName)), axisLabel: { interval: 0, rotate: 20 } }, yAxis: [{ type: 'value', name: '命中率(%)' }, { type: 'value', name: 'QPS' }], series: [{ name: '命中率', type: 'bar', data: topCaches.map(item => Number(item.hitRate || 0)) }, { name: 'QPS', type: 'line', yAxisIndex: 1, smooth: true, data: topCaches.map(item => Number(item.qps || 0)) }] }) }
function renderOverviewCharts() { nextTick(() => { buildKeyChart(); buildHitRateChart() }) }
function getList() { proxy.$modal.loading('正在加载缓存监控数据，请稍候！'); getCacheReport().then(response => { cacheSummary.value = response.data.summary || {}; cacheStats.value = response.data.caches || []; providerReports.value = response.data.providers || []; renderOverviewCharts() }).catch(() => { cacheSummary.value = {}; cacheStats.value = []; providerReports.value = []; proxy.$modal.msgError('缓存监控数据加载失败') }).finally(() => proxy.$modal.closeLoading()) }
watch(activeTab, tab => { if (tab === 'overview') renderOverviewCharts() })
onMounted(getList)
onBeforeUnmount(() => { keyChart && keyChart.dispose(); hitRateChart && hitRateChart.dispose() })
</script>

<style scoped>
.page-toolbar {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 12px;
}

.summary-row {
  margin-bottom: 16px;
}

.summary-card {
  min-height: 110px;
}

.summary-label {
  font-size: 14px;
  color: var(--el-text-color-secondary);
}

.summary-value {
  margin-top: 10px;
  font-size: 24px;
  font-weight: 600;
}

.summary-extra {
  margin-top: 8px;
  font-size: 12px;
  color: var(--el-text-color-secondary);
}

.card-box {
  margin-bottom: 16px;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.chart-panel {
  height: 320px;
}

.provider-status {
  margin-left: 8px;
}
</style>
