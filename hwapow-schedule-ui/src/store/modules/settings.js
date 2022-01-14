import variables from '@/assets/styles/element-variables.scss'
import defaultSettings from '../../../public/settings'

const { title,title_abbr,sideTheme, showSettings, tagsView, fixedHeader, sidebarLogo } = defaultSettings

const state = {
  title: title,
  title_abbr:title_abbr,
  theme: variables.theme,
  sideTheme: sideTheme,
  showSettings: showSettings,
  tagsView: tagsView,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo
}

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  }
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

