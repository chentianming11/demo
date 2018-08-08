import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login.vue';
import Register from '../components/Register.vue';
import Write from '../components/Write.vue';
import Home from '../components/Home.vue';
import SelfPage from '../components/SelfPage.vue';

Vue.use(Router)

export default new Router({
    routes: [
        {
            exact: false,
            path: '/',
            name: 'Home',
            component: Home
        }, {
            path: '/login',
            name: 'Login',
            component: Login
        }, {
            path: '/register',
            name: 'Register',
            component: Register
        }, {
            path: '/write/:userId',
            name: 'Write',
            component: Write
        }, {
            path: '/selfPage/:userId',
            name: 'SelfPage',
            component: SelfPage
        }
    ],

})
