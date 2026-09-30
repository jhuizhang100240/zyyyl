import { defineStore } from 'pinia'
import { useLocalStorage, useAsyncState } from '@vueuse/core';
import defaultSettings from '@/settings'
import { getConfigKey } from '@/api/system/config'

const resolveMenuLayout = (menuLayout?: string | null, topNav?: boolean) =>
  menuLayout === 'left' || menuLayout === 'mix' || menuLayout === 'top' ? menuLayout : topNav ? 'mix' : 'left'
type SettingsConfig = typeof defaultSettings & { menuLayout: 'left' | 'mix' | 'top' }
type ChangeSettingPayload = {
  [K in keyof SettingsConfig]: { key: K, value: SettingsConfig[K] }
}[keyof SettingsConfig]
const storageSettings = useLocalStorage<Partial<SettingsConfig>>('layout-setting', {})
const useSettingsStore = defineStore('settings', {
  state: (): SettingsConfig & { inited: boolean } => ({
    ...defaultSettings,
    menuLayout: resolveMenuLayout(defaultSettings.menuLayout, defaultSettings.topNav),
    inited: false
  }),
  actions: {
    async initSetting() {
      if (this.inited) return
      try {
        const toString = (value: string) => value
        const toBoolean = (value: string) => value === 'true'
        async function getConfig<T>(key: string, transform: (value: string) => T, defaultValue: T) {
          try { return transform((await getConfigKey(key)).msg) }
          catch { return defaultValue }
        }
        const [theme, sideTheme, topNav, menuLayout, tagsView, fixedHeader, sidebarLogo, dynamicTitle] = await Promise.all([
          getConfig('sys.index.theme', toString, defaultSettings.theme),
          getConfig('sys.index.sideTheme', toString, defaultSettings.sideTheme),
          getConfig('sys.index.topNav', toBoolean, defaultSettings.topNav),
          getConfig('sys.index.menuLayout', toString, defaultSettings.menuLayout),
          getConfig('sys.index.tagsView', toBoolean, defaultSettings.tagsView),
          getConfig('sys.index.fixedHeader', toBoolean, defaultSettings.fixedHeader),
          getConfig('sys.index.sidebarLogo', toBoolean, defaultSettings.sidebarLogo),
          getConfig('sys.index.dynamicTitle', toBoolean, defaultSettings.dynamicTitle),
        ])
        const settings: SettingsConfig = {
          ...defaultSettings,
          theme, sideTheme, topNav, tagsView, fixedHeader, sidebarLogo, dynamicTitle,
          menuLayout: resolveMenuLayout(menuLayout, topNav),
          ...storageSettings.value,
        }
        settings.menuLayout = resolveMenuLayout(settings.menuLayout, settings.topNav)
        settings.topNav = settings.menuLayout !== 'left'
        Object.assign(this, settings)
      } finally {
        this.inited = true
      }
    },
    saveSetting() {
      storageSettings.value = {
        "theme": this.theme,
        "sideTheme": this.sideTheme,
        "topNav": this.topNav,
        "menuLayout": this.menuLayout,
        "tagsView": this.tagsView,
        "fixedHeader": this.fixedHeader,
        "sidebarLogo": this.sidebarLogo,
        "dynamicTitle": this.dynamicTitle,
      }
    },
    changeSetting(data: ChangeSettingPayload) {
      Object.assign(this, { [data.key]: data.value })

      if (data.key === 'menuLayout') {
        this.menuLayout = resolveMenuLayout(data.value)
      } else if (data.key === 'topNav') {
        this.menuLayout = data.value ? 'mix' : 'left'
      }

      this.topNav = this.menuLayout !== 'left'
    },
    setTitle(title: string) {
      this.title = title
      this.refreshDynamicTitle()
    },
    refreshDynamicTitle() {
      document.title = this.dynamicTitle && this.title.trim()
        ? `${this.title} - ${defaultSettings.title}`
        : defaultSettings.title
    }
  },
  getters: {
    isLeftMenu: state => state.menuLayout === 'left',
    isMixMenu: state => state.menuLayout === 'mix',
    isTopMenu: state => state.menuLayout === 'top'
  }
})

export default useSettingsStore
