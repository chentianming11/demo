<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
        <h2>Essential Links</h2>
        <ul>
            <li>
                <a
                        href="https://vuejs.org"
                        target="_blank"
                >
                    Core Docs
                </a>
            </li>
            <li>
                <a
                        href="https://forum.vuejs.org"
                        target="_blank"
                >
                    Forum
                </a>
            </li>
            <li>
                <a
                        href="https://chat.vuejs.org"
                        target="_blank"
                >
                    Community Chat
                </a>
            </li>
            <li>
                <a
                        href="https://twitter.com/vuejs"
                        target="_blank"
                >
                    Twitter
                </a>
            </li>
            <br>
            <li>
                <a
                        href="http://vuejs-templates.github.io/webpack/"
                        target="_blank"
                >
                    Docs for This Template
                </a>
            </li>
        </ul>
        <h2>Ecosystem</h2>
        <ul>
            <li>
                <a
                        href="http://router.vuejs.org/"
                        target="_blank"
                >
                    vue-router
                </a>
            </li>
            <li>
                <a
                        href="http://vuex.vuejs.org/"
                        target="_blank"
                >
                    vuex
                </a>
            </li>
            <li>
                <a
                        href="http://vue-loader.vuejs.org/"
                        target="_blank"
                >
                    vue-loader
                </a>
            </li>
            <li>
                <a
                        href="https://github.com/vuejs/awesome-vue"
                        target="_blank"
                >
                    awesome-vue
                </a>
            </li>
        </ul>
    </div>
</template>

<script>
    import NavBar from  './components/Navbar';
    export default {
        name: 'App',
        components: {
            NavBar
        },
        data() {
            return {
                loginUser: null,
                loading: true,
            }
        },

        methods: {
            logout() {
                this.axios.post('/v1/blog/logout')
                    .then((res) => {
                        this.loginUser = null;
                        this.$router.push('/login')
                    }).
                catch((res) => {
                    alert('退出失败'
                    )
                    ;
                })

            },


            login(login) {
                this.axios.post('/v1/blog/login', login)
                    .then((response) => {
                        this.loginUser = response.data;
                        // 重定向到首页
                        this.$router.push({path: '/', props: response.data})
                    })
                    .
                    catch((error) => {
                        alert(error.response.data.message
                        )
                        ;
                    })
                ;
            }
        },

        mounted() {
            console.log('组件装载完成，调用自动登陆接口');
            this.axios.get('/v1/blog/autoLogin')
                .then((res) => {
                    this.loginUser = res.data;
                    this.loading = false;
                })
                .
                catch((error) => {
                    console.log('尚未登陆');
                    this.loading = false;
                })
        },


    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h1, h2 {
        font-weight: normal;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }
</style>
