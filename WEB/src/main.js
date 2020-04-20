// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import VueCookie from "vue-cookie";
import 'element-ui/lib/theme-chalk/index.css'
import Axios from "axios";
import VueAMap from 'vue-amap';



Vue.use(VueAMap);


Vue.use(ElementUI);
Vue.use(VueCookie);
Vue.prototype.$axios = Axios;
Vue.config.productionTip = false;
VueAMap.initAMapApiLoader({
  // 高德的key
  key: '820bdc5337a157dda6db8335da5c2460',
  // 插件集合
  plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor'],
  // 高德 sdk 版本，默认为 1.4.4
  v: '1.4.4'
});

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
