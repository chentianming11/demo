<template>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-menu-item index="0">
            <img src="../assets/logo.png" style="width: 40px"/>
        </el-menu-item>
        <el-menu-item index="1">
            <router-link to="/">首页</router-link>
        </el-menu-item>
        <el-submenu index="2">
            <template slot="title">我的工作台</template>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
            <el-menu-item index="2-3">选项3</el-menu-item>
            <el-submenu index="2-4">
                <template slot="title">选项4</template>
                <el-menu-item index="2-4-1">选项1</el-menu-item>
                <el-menu-item index="2-4-2">选项2</el-menu-item>
                <el-menu-item index="2-4-3">选项3</el-menu-item>
            </el-submenu>
        </el-submenu>
        <el-menu-item index="3">发现</el-menu-item>
        <el-menu-item index="4">关注</el-menu-item>


        <el-menu-item index="login" v-if="!loginUser">
            <router-link to="/login">登录</router-link>
        </el-menu-item>
        <el-menu-item index="register" v-if="!loginUser">
            <router-link to="/register">注册</router-link>
        </el-menu-item>

        <el-submenu index="6" v-if="loginUser">
            <template slot="title">
                <img :src="loginUser.headUrl" style="border-radius:50%;width: 40px"/>
            </template>
            <el-menu-item index="6-1">
                <div @click="handleLogout">退出</div>
            </el-menu-item>
        </el-submenu>

        <el-menu-item index="write" v-if="loginUser">
            <router-link to="/write">写文章</router-link>
        </el-menu-item>
    </el-menu>
</template>

<script>
    export default {
        name: 'NavBar',

        props: ['loginUser'],
        data() {
            return {
                activeIndex: '1',
                activeIndex2: '1'
            };
        },

        methods: {
            handleSelect(key, keyPath) {
//                console.log(key, keyPath);
            },

            handleLogout(){
                this.$emit('logout');
            }

        }
    }
</script>

<style>

</style>