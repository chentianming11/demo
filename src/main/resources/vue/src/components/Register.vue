<template>
    <el-form :label-position="labelPosition" label-width="80px" :model="register" :rules="rules" ref="register">
        <el-form-item label="用户名：" prop="username">
            <el-input v-model="register.username"></el-input>
        </el-form-item>
        <el-form-item label="密码：" prop="password">
            <el-input v-model="register.password"></el-input>
        </el-form-item>

        <el-form-item label="手机号：" prop="phone">
            <el-input v-model="register.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱：" prop="email">
            <el-input v-model="register.email"></el-input>
        </el-form-item>

        <el-form-item label="生日：" prop="birthday">
            <el-date-picker
                    v-model="register.birthday"
                    type="date"
                    placeholder="选择日期">
            </el-date-picker>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="submitForm('register')">注册</el-button>
            <el-button @click="resetForm('register')">重置</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        data() {
            return {
                labelPosition: 'right',
                register: {
                    username: '',
                    password: '',
                    phone: '',
                    email: '',
                    birthday: null,
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 5, message: '长度在 3 到 12 个字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请选择密码', trigger: 'change'}
                    ],
                    phone: [
                        {required: true, message: '请选择手机号', trigger: 'blur'}
                    ],
                    email: [
                        {required: true, message: '请选择邮箱', trigger: 'blur'}
                    ],
                    birthday: [
                        {required: true, message: '请选择生日', trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {

            submitForm(formName) {
                // 获取指定ref的虚拟dom对象
                console.log(this.$refs[formName]);
                this.$refs[formName].validate((valid) => {
                    if (valid) {

                        this.$http.post('/v1/blog/register', this.register)
                            .then((response) => {
                                // 注册成功，重定向到登录页
                                console.log(response);
                                this.$router.push({path: '/login'})
                            })
                            .catch((error) => {
                                console.log(error);
                                alert(error.response.data.message);
                            });

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