import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login.vue';
import Register from '../components/Register.vue';
import Write from '../components/Write.vue';
import Home from '../components/Home.vue';
import UserPage from '../components/UserPage.vue';
import ArticleDetail from '../components/ArticleDetail.vue';

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
            path: '/userPage/:userId',
            name: 'UserPage',
            component: UserPage
        }, {
            path: '/article/detail/:articleId',
            name: 'ArticleDetail',
            component: ArticleDetail
        }
    ],

})
