backendUrl = 'http://localhost:8080';

module.exports = {
  /*
  ** Headers of the page
  */

  head: {
    title: 'Vue Frontend Hello World',
    meta: [
      {charset: 'utf-8'},
      {name: 'viewport', content: 'width=device-width, initial-scale=1'},
      {hid: 'description', name: 'description', content: 'Vue Frontend Hello World'}
    ],
    link: [
      {rel: 'icon', type: 'image/x-icon', href: '/favicon.ico'},
      {rel: 'stylesheet', href: 'https://fonts.googleapis.com/css?family=Lato:300,400,500,700|Material+Icons'},
      {rel: 'stylesheet', href: 'https://cdn.firebase.com/libs/firebaseui/2.6.3/firebaseui.css'}
    ]
  },
  modules: [
    '@nuxtjs/axios'
  ],
  plugins: [
    '~/plugins/vuetify'
  ],
  router: {
    middleware: ['sessionSetup']
  },
  css: [
    '~/assets/style/app.styl'
  ],
  /*
  ** Customize the progress bar color
  */
  loading: {color: '#3B8070'},
  /*
  ** Build configuration
  */
  axios: {
    https: process.env.NODE_ENV === 'production',
    proxy: true,
    credentials: true
  },
  proxy: {
    '/api': backendUrl,
    '/logout': backendUrl
  },
  build: {
    extractCSS: true,
    cssSourceMap: false,
    /*
    ** Run ESLint on save
    */
    extend(config, {isDev, isClient}) {
      // if (isDev && isClient) {
      //   config.module.rules.push({
      //     enforce: 'pre',
      //     test: /\.(js|vue)$/,
      //     loader: 'eslint-loader',
      //     exclude: /(node_modules)/
      //   })
      // }
    }
  }
}
