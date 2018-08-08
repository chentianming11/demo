<template>
    <div class="home">
        <Articles :article-list="articleList" />
        <Author :author="author"/>
    </div>  
</template>

<script>
import Articles from "./Articles";
import Author from "./Author";
export default {
  data() {
    return {
      articleList: [],
      author: []
    };
  },

  components: {
    Articles,
    Author
  },

  
  created() {
      this.getArticles();
      this.getAuthor();
  },

  methods: {
    toSelf(userId) {
      // console.log(`进入个人中心${userId}`);
    },
    getArticles() {
      this.axios
        .get("/v1/blog/article/list")
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

    getAuthor(){
            this.axios
        .get("/v1/blog/user/list")
        .then(res => {
            this.author = res.data;
        })
        .catch(res => {
            this.$message({
            type: "error",
            message: "获取作者列表失败"
            });
        });
    }
  },
};
</script>


<style>
.home {
  display: flex;
  justify-content:center;
}
</style>