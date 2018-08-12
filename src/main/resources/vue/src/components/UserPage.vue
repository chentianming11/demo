<template>

    <div class="self-page">
        <UserInfo query="userQuery" />
        <div class="self-buttom">
            <el-tabs v-model="activeName">
                <el-tab-pane label="文章管理" name="first">
                    <Articles :query="articleQuery" @change-page="changePage"/>
                </el-tab-pane>
                <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>
                <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
                <el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane>
            </el-tabs>
            <UserCollection :query="collectionQuery" @change-collection="changeCollection"
            class="padding-10 margin-10"/>
        </div>
    </div>
</template>

<script>
import UserInfo from "./UserInfo";
import Articles from "./Articles";
import UserCollection from "./UserCollection";
export default {
  data() {
    return {
      activeName: "first",
      userId: this.$route.params.userId,
      articleQuery:{
        pageNum:1,
        userId: this.$route.params.userId,
        collectionId: null,
      },

      collectionQuery:{
          userId: this.$route.params.userId,
      },

      userQuery:{
          userId: this.$route.params.userId,
      },
    };
  },

  components: {
    UserInfo,
    Articles,
    UserCollection
  },



  methods: {
    changeCollection(collectionId) {
      this.articleQuery = Object.assign({}, this.articleQuery, {collectionId});
    },

    

    

    changePage(pageNum) {
      this.articleQuery = Object.assign({}, this.articleQuery, {pageNum});
    }
  }
};
</script>

<style>
.self-buttom {
  display: flex;
  justify-content: space-between;
}
</style>
