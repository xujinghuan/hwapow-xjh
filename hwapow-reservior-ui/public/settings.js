module.exports = {
  //网站标题，在登陆页展示
  title: '浍河水库大坝无线监测系统',
  //网站缩写标题，在主界面左上方展示（最多支持十个字）
  title_abbr:'浍河水库大坝无线监测',
  //后端地址和端口
  //backIPPort:'192.168.1.105:8080/hwapow-reservior',
  backIPPort:'127.0.0.1:8080',
  /**
   * 侧边栏主题 深色主题theme-dark，浅色主题theme-light
   */
  sideTheme: 'theme-dark',

  /**
   * 是否系统布局配置
   */
  showSettings: false,

  /**
   * 是否显示 tagsView
   */
  tagsView: true,

  /**
   * 是否固定头部
   */
  fixedHeader: false,

  /**
   * 是否显示logo
   */
  sidebarLogo: true,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production'
}
