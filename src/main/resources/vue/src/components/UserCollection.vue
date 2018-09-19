<template>
<div>
        <div>个人文集：</div>
        <div v-for="coll in collections" :key="coll.id" @click="handleCollectionClick(coll.id)"
        class="coll">
            <i class="el-icon-document"></i>
            {{coll.name}}
        </div> 
    </div>
</template>


<script>
export default {
  props: ["query"],

  data() {
    return {
      collections:[],
    };
  },

  mounted(){
      this.getCollections();
  },

  methods: {
    
    handleCollectionClick(collectionId){
        this.$emit('change-collection', collectionId);
    },

    // 获取文集列表
    getCollections() {
      this.$http
        .get(`/v1/blog/collection`, this.query)
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
  }
};
</script>

<style>
.coll {
    margin: 5px;
}
</style>