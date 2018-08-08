// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import VueAxios from 'vue-axios';
import './main.css';

Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(VueAxios, axios);


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


// 定义全局过滤器
Vue.filter('formatDate', (timestamp) => {
    let date = new Date(timestamp);
    let year = date.getFullYear(),
        month = date.getMonth() + 1,//月份是从0开始的
        day = date.getDate(),
        hour = date.getHours(),
        min = date.getMinutes(),
        sec = date.getSeconds();
    let newTime = year + '-' +
        month + '-' +
        day + ' ' +
        hour + ':' +
        min + ':' +
        sec;
    return newTime;
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: { App },
    template: '<App />',
})
