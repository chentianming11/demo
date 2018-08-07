<template>
    <el-table
            :data="articleList"
            style="width: 100%">
        <el-table-column
                label="头像"
                width="80">
            <template slot-scope="scope">
                <img :src="headUrl" style="width:30px"/>
            </template>
        </el-table-column>
        <el-table-column
                prop="username"
                label="用户名"
                width="180">
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
                prop="articleCreateTime"
                width="180"
                label="创建时间">
        </el-table-column>
    </el-table>
</template>

<script>
    export default {
        data() {
            return {
                articleList: []
            }
        },

        created(){
            this.axios.get('/v1/blog/article/all')
                .then((res) => {
                    this.articleList = res.data;
                }).catch((res) => {
                this.$message({
                    type: 'error',
                    message: '获取文章列表失败',
                })
            })
        }
    }
</script>


<style>

</style>