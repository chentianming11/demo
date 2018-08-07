<template>
    <el-form :label-position="labelPosition" label-width="80px" :model="login" :rules="rules" ref="login">
        <el-form-item label="用户名：" prop="username">
            <el-input v-model="login.username"></el-input>
        </el-form-item>
        <el-form-item label="密码：" prop="password">
            <el-input v-model="login.password"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm('login')">登录</el-button>
            <el-button @click="resetForm('login')">重置</el-button>
            <router-link to="/register">没有账号，去注册</router-link>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        data() {
            return {
                labelPosition: 'right',
                login: {
                    username: '',
                    password: '',
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 5, message: '长度在 3 到 12 个字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请选择密码', trigger: 'change'}
                    ],
                }
            };
        },
        methods: {

            submitForm(formName) {
                // 获取指定ref的虚拟dom对象
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$emit('login', this.login);
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style>


</style>