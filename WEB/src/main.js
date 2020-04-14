// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import VueCookie from "vue-cookie";

import 'element-ui/lib/theme-chalk/index.css'
import Axios from "axios";

Vue.use(ElementUI);
Vue.use(VueCookie);
Vue.prototype.$axios = Axios;
Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App)
});

// router.beforeEach((to, from, next) => {
//
//   //发出请求，并且将token以参数形式传递给nodejs服务器
//   axios.post('/api/user/checkLogin', {
//     token: sessionStorage.getItem('token') || ''    //如果没有token则为空字符串
//   })
//     .then(res => {
//
//       // 如果没有返回数据则跳转到登陆页面
//       if (res.data.err === 'fail') router.push('/login')
//       next()
//     })
// });
