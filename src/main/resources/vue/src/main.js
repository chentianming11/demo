// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import VueAxios from 'vue-axios'

Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(VueAxios, axios);


var loginUser = null;

/*router.beforeEach((to, from, next) => {

 if (to.name !== 'Login' && to.name !== 'Register'){
 axios.get('/v1/blog/autoLogin')
 .then((res) => {
 loginUser = res.data;
 console.log(loginUser);
 next();
 })
 .catch((error) => {
 console.log('未登录，跳转到登录页');
 loginUser = null;
 next('/login')
 })
 }else {
 next();
 }

 })*/

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: {App},
    template: '<App />',
})
