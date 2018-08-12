<template>
    <div>
        <div>作者列表：</div>
        <el-table
            :data="author"
            style="width: 100%">
            <el-table-column
                label="头像"
                width="80">
            <template slot-scope="scope">
                <img :src="scope.row.headUrl" @click="toSelf(scope.row.id)" style="width:30px"/>
            </template>
        </el-table-column>
        <el-table-column
                label="用户名"
                width="180">
                <template slot-scope="scope" >
                    <div @click="toSelf(scope.row.id)">{{scope.row.username}}</div>
                </template>
        </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    data(){
        return {
           author:[],
        }
    },

    mounted(){
        this.getAuthor();
    },

    methods: {
       toSelf(userId) {
        console.log('跳转到个人中心:' + userId);
      this.$router.push({path:`/userPage/${userId}`});
    }, 

    getAuthor() {
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
    },
    
  },
}
</script>

<style>

</style>


