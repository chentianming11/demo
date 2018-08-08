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
        </el-table>
    </div>
</template>

<script>
export default {
    props:['author']
}
</script>

<style>

</style>


