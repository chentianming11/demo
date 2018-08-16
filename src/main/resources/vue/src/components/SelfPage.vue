<template>

    <div class="self-page">
        <SelfInfo :user-id="this.$route.params.userId" />
        <div class="self-buttom">
            <el-tabs v-model="activeName">
                <el-tab-pane label="文章管理" name="first">
                    <Articles :article-list="articleList" />
                </el-tab-pane>
                <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>
                <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
                <el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane>
            </el-tabs>
            <SelfCollection :user-id="this.$route.params.userId" class="padding-20 margin-30"/>
        </div>
    
        
    </div>
</template>


<script>
import SelfInfo from "./SelfInfo";
import Articles from "./Articles";
import SelfCollection from "./SelfCollection";
export default {
  data() {
    return {
      userInfo: {},
      activeName:'first',
      articleList:[],
      collections:[],
    };
  },

  components: {
    SelfInfo,
    Articles,
    SelfCollection,
  },

  beforeMount() {
    let { userId } = this.$route.params;
    this.getArticles(userId);
  },

  methods: {
    getArticles(userId) {
      this.axios
        .get(`/v1/blog/article/list?userId=${userId}`)
        .then(res => {
          this.articleList = res.data;
        })
        .catch(res => {
          this.$message({
            type: "error",
            message: "获取文章列表失败"
          });
        });
    },


  }
};
</script>

<style>
.self-buttom {
    display: flex;
    justify-content:space-between;
}
</style>
