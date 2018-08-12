<template>
    <div class="home">
        <Articles :query="articleQuery" @change-page="changePage" />
        <Author />
    </div>  
</template>

<script>
import Articles from "./Articles";
import Author from "./Author";
export default {
  data() {
    return {
      author: [],
      articleQuery:{
        pageNum:1,
      }

    };
  },

  components: {
    Articles,
    Author
  },

  
  methods: {
    changePage(pageNum) {
      this.articleQuery = Object.assign({}, this.articleQuery, {pageNum});      
    }
  }
};
</script>


<style>
.home {
  display: flex;
  justify-content: center;
}
</style>