<template>
    <el-form :model="article" :rules="rules" ref="article" label-width="100px" class="demo-article">
        <el-form-item label="标题" prop="title">
            <el-input v-model="article.title"></el-input>
        </el-form-item>
        <el-form-item label="文集" prop="collectionId">
            <el-select v-model="article.collectionId" placeholder="请选择文集">
                <el-option v-for="collection in collections" :label="collection.name" :value="collection.id"
                           :key="collection.id"></el-option>
            </el-select>
            <el-button type="primary" @click="addCollection">新增文集</el-button>

        </el-form-item>
        <el-form-item label="内容" prop="content">
            <Editor v-model="article.content"/>
        </el-form-item>
        <el-form-item style="text-align: center;">
            <el-button type="primary" @click="submitForm('article')">立即发布</el-button>
            
        </el-form-item>

         <el-form-item>
            {{article.content}}
        </el-form-item>

    </el-form>

</template>

<script>

export default {
  data() {
    return {
      article: {
        title: "",
        collectionId: null,
        content: ""
      },
      collections: null,

      rules: {
        title: [
          { required: true, message: "请输入标题", trigger: "blur" },
          {
            min: 3,
            max: 100,
            message: "长度在 3 到 100 个字符",
            trigger: "blur"
          }
        ],
        collectionId: [
          { required: true, message: "请选择文集", trigger: "blur" }
        ],

        content: [
          { required: true, message: "请填输入内容", trigger: "blur" },
          {
            min: 10,
        
            message: "至少10个字符",
            trigger: "blur"
          }
        ]
      }
    };
  },

  components: {
    
  },

  beforeMount() {
    this.getCollections();
  },

  methods: {
    // 获取文集列表
    getCollections() {
      let { userId } = this.$route.params;
      console.log(userId);
      this.$http
        .get(`/v1/blog/collection?userId=${userId}`)
        .then(res => {
          this.collections = res.data;
        })
        .catch(res => {
          this.$message({
            type: "error",
            message: `获取文集列表失败`
          });
        });
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$http
            .post("/v1/blog/article", this.article)
            .then(res => {
              this.$message({
                type: "success",
                message: "发布成功"
              });
            })
            .catch(res => {
              this.$message({
                type: "error",
                message: "发布失败"
              });
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    addCollection() {
      this.$prompt("请输入文集名称", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /.+/,
        inputErrorMessage: "请输入至少一个字符"
      })
        .then(({ value }) => {
          this.$http
            .post("/v1/blog/collection", { name: value })
            .then(res => {
              this.getCollections();
              this.$message({
                type: "success",
                message: `文集${value}, 添加成功`
              });
            })
            .catch(res => {
              this.$message({
                type: "error",
                message: `文集${value}, 添加失败`
              });
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入"
          });
        });
    }
  }
};
</script>

<style>
.demo-article {
  width: 900px;
}
</style>