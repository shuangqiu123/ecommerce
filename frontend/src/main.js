import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en'
import router from './router'

Vue.use(ElementUI,{locale});

import axios from 'axios'
Vue.prototype.axios = axios;
// axios.defaults.baseURL = "https://data.shuangqiu.blog/";
axios.defaults.baseURL = "http://localhost:4500/"
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
