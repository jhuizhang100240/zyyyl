<template>
  <el-card>
    <template #header><div class="card-header"><span>{{ provider.name }}</span><el-tag :type="provider.available ? 'success' : 'danger'">{{ provider.available ? '运行中' : '不可用' }}</el-tag></div></template>
    <el-alert v-if="provider.error" :title="provider.error" type="error" :closable="false" />
    <template v-else>
      <el-descriptions v-if="scalarEntries.length" :column="3" border>
        <el-descriptions-item v-for="item in scalarEntries" :key="item.key" :label="item.key">{{ item.value }}</el-descriptions-item>
      </el-descriptions>
      <section v-for="table in tableEntries" :key="table.key" class="data-table">
        <h3>{{ table.key }}</h3>
        <el-table :data="table.rows" stripe>
          <el-table-column v-for="column in table.columns" :key="column" :prop="column" :label="column" min-width="140" show-overflow-tooltip />
        </el-table>
      </section>
      <el-empty v-if="!scalarEntries.length && !tableEntries.length" description="Provider 未提供报表数据" />
    </template>
  </el-card>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  provider: {
    type: Object,
    required: true
  }
})

const data = computed(() => isPlainObject(props.provider.data) ? props.provider.data : {})
const entries = computed(() => Object.entries(data.value))
const scalarEntries = computed(() => entries.value.filter(([, value]) => !Array.isArray(value) && !isPlainObject(value)).map(([key, value]) => ({ key, value })))
const tableEntries = computed(() => entries.value.filter(([, value]) => Array.isArray(value) && value.every(row => isPlainObject(row))).map(([key, rows]) => ({ key, rows, columns: tableColumns(rows) })))
function isPlainObject(value) { return value !== null && typeof value === 'object' && !Array.isArray(value) }
function tableColumns(rows) { return [...new Set(rows.flatMap(row => Object.keys(row)))] }
</script>

<style scoped>
.card-header { display: flex; align-items: center; justify-content: space-between; }
.data-table { margin-top: 20px; }
.data-table h3 { margin: 0 0 12px; font-size: 14px; font-weight: 600; }
</style>
