<template>

    <div v-if="articleDetail" class="article-detail" >
        <h1>{{articleDetail.title}}</h1>

        <div class="user">
                <img :src="articleDetail.headUrl" alt="用户头像" 
                    style="border-radius: 50%; width: 60px;height: 60px;">
                    <div style="padding-left:10px">
                        <div class="font-24">{{articleDetail.username}}</div>
                        <div class="font-12">{{articleDetail.articleCreateTime | formatDate}} 字数 86 阅读 3511评论 43喜欢 99</div>
                    </div>
                    
        </div>

        <EditorView :content="articleDetail.content" />

    </div>
    
</template>

<script>
export default {
  data() {
    return {
      articleId: this.$route.params.articleId,
      articleDetail: {}
    };
  },

  mounted() {
    this.axios
      .get(`/v1/blog/article/${this.articleId}`)
      .then(response => {
        this.articleDetail = response.data;
      })
      .catch(error => {
        console.log(error);
        alert("获取文章详情失败");
      });
  }
};
</script>

<style lang="less">
.article-detail {
  width: 620px;

  .user {
    display: flex;
  }
}
</style>


