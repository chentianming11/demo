<template>
    <div class="ql-snow">
          <div class="ql-editor">
            <div v-html="content"></div>
        </div>
    </div>
</template>

<script>
export default {
    props:['content'],
}
</script>



