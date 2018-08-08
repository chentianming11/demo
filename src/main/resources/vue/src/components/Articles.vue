<template>
    <div class="articles">
        <div>文章列表：</div>
        <el-table
            :data="articleList"
            style="width: 100%">
        <el-table-column
                label="头像"
                width="80">
            <template slot-scope="scope">
                <img :src="scope.row.headUrl" @click="toSelf(scope.row.userId)" style="width:30px"/>
            </template>
        </el-table-column>
        <el-table-column
                label="用户名"
                width="180">
                <template slot-scope="scope" >
                    <div @click="toSelf(scope.row.userId)">{{scope.row.username}}</div>
                </template>
        </el-table-column>
        <el-table-column
                prop="collectionName"
                label="文集名称"
                width="180">
                <template slot-scope="scope" >
                    <div @click="toSelf(scope.row.userId)">{{scope.row.collectionName}}</div>
                </template>
        </el-table-column>
        <el-table-column
                prop="title"
                width="180"
                label="标题">

        </el-table-column>
        <el-table-column
                prop="content"
                width="300"
                label="内容">
        </el-table-column>
        <el-table-column
                width="180"
                label="创建时间">
            <template slot-scope="scope">
                {{scope.row.articleCreateTime | formatDate}}
            </template>
        </el-table-column>
    </el-table>
    </div>
</template>

<script>
export default {
  props: ['articleList'],
  
  data() {
    return {
        
    };
  },

  methods: {
    toSelf(userId) {
      this.$router.push(`/selfPage/${userId}`);
    }
  }
};
</script>


<style>
.articles {
    margin: 20px;
}
</style>