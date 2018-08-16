<template>
    <el-container v-loading="loading">
        <el-header class="header">
            <NavBar :login-user="loginUser" @logout="logout"/>
        </el-header>
        <el-main class="main">
            <router-view @login="login" ></router-view>
        </el-main>
        <el-footer>footer: {{loginUser}}</el-footer>
    </el-container>
</template>

<script>
import NavBar from "./components/Navbar";
export default {
  name: "App",
  components: {
    NavBar
  },
  data() {
    return {
      loginUser: null,
      loading: true
    };
  },

  methods: {
    logout() {
      this.axios
        .post("/v1/blog/logout")
        .then(res => {
          this.loginUser = null;
          this.$router.push("/login");
        })
        .catch(res => {
          alert("退出失败");
        });
    },

    login(user) {
      this.axios
        .post("/v1/blog/login", user)
        .then(response => {
          this.loginUser = response.data;
          // 重定向到首页
          this.$router.push({ path: "/", props: response.data });
        })
        .catch(error => {
          alert(error.response.data.message);
        });
    },
    
  },

  mounted() {
    console.log("组件装载完成，调用自动登陆接口");
    this.axios
      .get("/v1/blog/autoLogin")
      .then(res => {
        this.loginUser = res.data;
        this.loading = false;
      })
      .catch(error => {
        console.log("尚未登陆");
        this.loading = false;
      });
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.header {
  padding-left: 100px;
  padding-right: 100px;
}

.main {
  margin: 30px;
  padding-left: 100px;
  padding-right: 100px;
  display: flex;
  justify-content: center;
}
</style>
