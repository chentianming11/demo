<template>

    <div class="userInfo">
        
        <div class="user-content">
            <img :src="userInfo.headUrl" alt="用户头像" 
            style="border-radius: 50%; width: 100px;">
            <div class="font-36 padding-20">{{userInfo.username}}</div>
        </div>

        <div class="user-content">
            个人介绍, 可支持编辑。
        </div>
    </div>
</template>

<script>
export default {
  props: ["query"],
  data() {
    return {
      userInfo:{},
    };
  },

  mounted(){
      this.getUserInfo();
  },

  methods: {
    getUserInfo() {
      this.$http
        .get(`/v1/blog/user`,  this.query)
        .then(response => {
          console.log(response.data);
          this.userInfo = response.data;
        })
        .catch(error => {
          alert("获取个人信息异常：" + error);
        });
    },
  }
};
</script>

<style>
.userInfo {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.user-content {
  display: flex;
  align-items: center;
  margin: 20px;
}
</style>
