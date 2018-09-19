// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

// 引入全局js
import  CookieUtils from './common/util/CookieUtils';
import  DateUtils from './common/util/DateUtils';
import  FormatUtils from './common/util/FormatUtils';
import  Http from './common/util/Http';

Vue.prototype.cookieUtils = CookieUtils;
Vue.prototype.dateUtils = DateUtils;
Vue.prototype.formatUtils = FormatUtils;
Vue.prototype.$http = Http;


// QuillEditor富文本编辑器
import VueQuillEditor from 'vue-quill-editor';
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
Vue.use(VueQuillEditor);
// element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

// 全局css
import './common/style/main.less';
// 全局注册组件
import './common/component/global';
// 全局过滤器
import './common/filter/filter';





Vue.config.productionTip = false




/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: { App },
    template: '<App />',
})
