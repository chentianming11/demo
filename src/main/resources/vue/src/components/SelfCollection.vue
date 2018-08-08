<template>
<div>
        <div>个人文集：</div>
        <div v-for="coll in collections" :key="coll.id" class="coll">
            <i class="el-icon-document"></i>
            {{coll.name}}
        </div> 
    </div>
</template>


<script>
export default {
  props: ["userId"],

  data() {
    return {
      collections: []
    };
  },

  beforeMount(){
      this.getCollections(this.userId);
  },

  methods: {
    // 获取文集列表
    getCollections(userId) {
      this.axios
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
    }
  }
};
</script>

<style>
.coll {
    margin: 10px;
}
</style>