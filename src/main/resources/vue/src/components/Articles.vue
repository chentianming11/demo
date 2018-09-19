<template>
    <div class="articles">
        <div class="center">文章列表</div>
        <el-table
            :data="articlePage.list"
            style="width: 100%;margin:10px">
        <el-table-column
                label="头像"
                width="60">
            <template slot-scope="scope">
                <img :src="scope.row.headUrl" @click="toSelf(scope.row.userId)" style="width:30px"/>
            </template>
        </el-table-column>
        <el-table-column
                label="用户名"
                width="80">
                <template slot-scope="scope" >
                    <a @click="toSelf(scope.row.userId)">{{scope.row.username}}</a>
                </template>
        </el-table-column>
        <el-table-column
                prop="collectionName"
                label="文集名称"
                width="150">
                <!-- <template slot-scope="scope" >
                    <div @click="toSelf(scope.row.userId,scope.row.collectionId)">{{scope.row.collectionName}}</div>
                </template> -->
        </el-table-column>
        <el-table-column
                prop="title"
                width="200"
                label="标题">
                <template slot-scope="scope" >
                    <a @click="articleDetail(scope.row.articleId)">{{scope.row.title}}</a>
                </template>
        </el-table-column>
      
        <el-table-column
                width="200"
                label="创建时间">
            <template slot-scope="scope">
                {{scope.row.articleCreateTime | formatDate}}
            </template>
        </el-table-column>
    </el-table>

    <el-pagination class="center"
    layout="prev, pager, next"
    @current-change="currentChange"
    :current-page="articlePage.pageNum"
    :total="articlePage.total">
    </el-pagination>
    </div>
</template>

<script>
export default {
  props: ["query"],
  data() {
    return {
      articlePage: {}
    };
  },

  watch: {
    query(){
        this.getArticles();
    }
  },

  mounted(){
      this.getArticles();
  },

  methods: {
    toSelf(userId) {
      this.$router.push({ path: `/userPage/${userId}` });
    },

    articleDetail(articleId){
        this.$router.push({ path: `/article/detail/${articleId}` });
    },

    currentChange(currentPage) {
      this.$emit("change-page", currentPage);
    },

    getArticles() {
      this.$http
        .get(`/v1/blog/article/list`, this.query)
        .then(res => {
          this.articlePage = res.data.data;
        })
        .catch(res => {
          this.$message({
            type: "error",
            message: "获取文章列表失败"
          });
        });
    }
  }
};
</script>


<style>
.articles {
  margin: 20px;
}
</style>