<template>
    <div>
        <!-- 图片上传组件辅助-->
      <el-upload
        class="avatar-uploader"
        :action="serverUrl"
        name="img"
        :headers="header"
        :show-file-list="false"
        :on-success="uploadSuccess"
        :on-error="uploadError"
        :before-upload="beforeUpload">
      </el-upload>

        <quill-editor 
        class="editor"
        v-model="content"
        ref="myQuillEditor" 
        :options="editorOption" 
        @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
        @change="onEditorChange($event)">


            <!-- 自定义toolbaar -->
            <div id="toolbar" slot="toolbar">
              <span class="ql-formats">
              <button type="button" :class="'ql-bold'"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-italic"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-underline"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-strike"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-blockquote"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-code-block"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-header" value="1"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-header" value="2"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-list" value="ordered"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-list" value="bullet"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-script" value="sub"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-script" value="super"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-indent" value="-1"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-indent" value="+1"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-direction" value="rtl"></button>
              </span>
              <span class="ql-formats"><select class="ql-size">
              <option value="small">10px</option>
              <option selected>14px</option>
              <option value="large">18px</option>
              <option value="huge">32px</option>
              </select></span>
              <span class="ql-formats"><select class="ql-header">
              <option value="1">标题 1</option>
              <option value="2">标题 2</option>
              <option value="3">标题 3</option>
              <option value="4">标题 4</option>
              <option value="5">标题 5</option>
              <option value="6">标题 6</option>
              <option selected="selected">普通文本</option>
              </select></span>
              <span class="ql-formats"><select class="ql-color">
              <option selected="selected"></option>
              <option value="#e60000"></option>
              <option value="#ff9900"></option>
              <option value="#ffff00"></option>
              <option value="#008a00"></option>
              <option value="#0066cc"></option>
              <option value="#9933ff"></option>
              <option value="#ffffff"></option>
              <option value="#facccc"></option>
              <option value="#ffebcc"></option>
              <option value="#ffffcc"></option>
              <option value="#cce8cc"></option>
              <option value="#cce0f5"></option>
              <option value="#ebd6ff"></option>
              <option value="#bbbbbb"></option>
              <option value="#f06666"></option>
              <option value="#ffc266"></option>
              <option value="#ffff66"></option>
              <option value="#66b966"></option>
              <option value="#66a3e0"></option>
              <option value="#c285ff"></option>
              <option value="#888888"></option>
              <option value="#a10000"></option>
              <option value="#b26b00"></option>
              <option value="#b2b200"></option>
              <option value="#006100"></option>
              <option value="#0047b2"></option>
              <option value="#6b24b2"></option>
              <option value="#444444"></option>
              <option value="#5c0000"></option>
              <option value="#663d00"></option>
              <option value="#666600"></option>
              <option value="#003700"></option>
              <option value="#002966"></option>
              <option value="#3d1466"></option>
              </select></span>
              <span class="ql-formats"> <select class="ql-background">
              <option value="#000000"></option>
              <option value="#e60000"></option>
              <option value="#ff9900"></option>
              <option value="#ffff00"></option>
              <option value="#008a00"></option>
              <option value="#0066cc"></option>
              <option value="#9933ff"></option>
              <option selected="selected"></option>
              <option value="#facccc"></option>
              <option value="#ffebcc"></option>
              <option value="#ffffcc"></option>
              <option value="#cce8cc"></option>
              <option value="#cce0f5"></option>
              <option value="#ebd6ff"></option>
              <option value="#bbbbbb"></option>
              <option value="#f06666"></option>
              <option value="#ffc266"></option>
              <option value="#ffff66"></option>
              <option value="#66b966"></option>
              <option value="#66a3e0"></option>
              <option value="#c285ff"></option>
              <option value="#888888"></option>
              <option value="#a10000"></option>
              <option value="#b26b00"></option>
              <option value="#b2b200"></option>
              <option value="#006100"></option>
              <option value="#0047b2"></option>
              <option value="#6b24b2"></option>
              <option value="#444444"></option>
              <option value="#5c0000"></option>
              <option value="#663d00"></option>
              <option value="#666600"></option>
              <option value="#003700"></option>
              <option value="#002966"></option>
              <option value="#3d1466"></option>
              </select></span>
              <span class="ql-formats"><select class="ql-font">
              <option selected="selected">标准字体</option>
              <option value="serif">衬线字体</option>
              <option value="monospace">等宽字体</option>
              </select></span>
              <span class="ql-formats">
              <select class="ql-align">
              <option selected="selected"></option>
              <option value="center"></option>
              <option value="right"></option>
              <option value="justify"></option>
              </select>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-clean"></button>
              </span>
              <span class="ql-formats">
              <button type="button" class="ql-link"></button>
              </span>
              <span class="ql-formats">
              <button type="button" @click="fileClick('image')">
              <svg viewBox="0 0 18 18">
              <rect class="ql-stroke" height="10" width="12" x="3" y="4"></rect>
              <circle class="ql-fill" cx="6" cy="7" r="1"></circle>
              <polyline class="ql-even ql-fill"
              points="5 12 5 11 7 9 8 10 11 7 13 9 13 12 5 12">
              </polyline>
              </svg>
              </button>
              </span>
              <span class="ql-formats" @click="fileClick('video')">
              <button type="button">
              <svg viewBox="0 0 18 18">
              <rect class="ql-stroke" height="12" width="12" x="3" y="3"></rect>
              <rect class="ql-fill" height="12" width="1" x="5" y="3"></rect>
              <rect class="ql-fill" height="12" width="1" x="12" y="3"></rect>
              <rect class="ql-fill" height="2" width="8" x="5" y="8"></rect>
              <rect class="ql-fill" height="1" width="3" x="3" y="5"></rect>
              <rect class="ql-fill" height="1" width="3" x="3" y="7"></rect>
              <rect class="ql-fill" height="1" width="3" x="3" y="10"></rect>
              <rect class="ql-fill" height="1" width="3" x="3" y="12"></rect>
              <rect class="ql-fill" height="1" width="3" x="12" y="5"></rect>
              <rect class="ql-fill" height="1" width="3" x="12" y="7"></rect>
              <rect class="ql-fill" height="1" width="3" x="12" y="10"></rect>
              <rect class="ql-fill" height="1" width="3" x="12" y="12"></rect>
              </svg>
              </button>
              </span>
              <span class="ql-formats">
              </span>
            </div>

        </quill-editor>
    </div>
</template>
<script>
// 工具栏配置
const toolbarOptions = [

  ["bold", "italic", "underline", "strike"], // 加粗 斜体 下划线 删除线
  ["blockquote", "code-block"], // 引用  代码块
  [{ header: 1 }, { header: 2 }], // 1、2 级标题
  [{ list: "ordered" }, { list: "bullet" }], // 有序、无序列表
  [{ script: "sub" }, { script: "super" }], // 上标/下标
  [{ indent: "-1" }, { indent: "+1" }], // 缩进
  // [{'direction': 'rtl'}],                         // 文本方向
  [{ size: ["small", false, "large", "huge"] }], // 字体大小
  [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题
  [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色
  [{ font: [] }], // 字体种类
  [{ align: [] }], // 对齐方式
  ["clean"], // 清除文本格式
  ["link", "image", "video"] // 链接、图片、视频
];

import { quillEditor } from "vue-quill-editor";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";

export default {
  props: {
    /*编辑器的内容*/
    value: {
      type: String
    },
    /*图片大小*/
    maxSize: {
      type: Number,
      default: 4000 //kb
    }
  },

  components: {
    quillEditor
  },

  data() {
    return {
      content: this.value,
      quillUpdateImg: false, // 根据图片上传状态来确定是否显示loading动画，刚开始是false,不显示
      editorOption: {
        placeholder: "",
        theme: "snow", // or 'bubble'
        modules: {
          toolbar: "#toolbar"
          },
          placeholder: "您想说点什么？"
        // modules: {
        //   toolbar: {
        //     container: toolbarOptions,
        //     handlers: {
        //       image: function(value) {
        //         if (value) {
        //           // 触发input框选择图片文件
        //           document.querySelector(".avatar-uploader input").click();
        //         } else {
        //           this.quill.format("image", false);
        //         }
        //       }
        //     }
        //   }
        // }
      },
      serverUrl: "/v1/blog/imgUpload", // 这里写你要上传的图片服务器地址
      header: {
        // token: sessionStorage.token
      } // 有的图片服务器要求请求头需要有token
    };
  },

  methods: {
    onEditorBlur() {
      //失去焦点事件
    },
    onEditorFocus() {
      //获得焦点事件
    },
    onEditorChange() {
      //内容改变事件
      console.log(this.content);
      this.$emit("input", this.content);
    },

    // 富文本图片上传前
    beforeUpload() {
      // 显示loading动画
      this.quillUpdateImg = true;
    },

    uploadSuccess(res, file) {
      // res为图片服务器返回的数据
      // 获取富文本组件实例
      console.log(res);
      let quill = this.$refs.myQuillEditor.quill;
      // 如果上传成功
      if (res.code == 200) {
        // 获取光标所在位置
        let length = quill.getSelection().index;
        // 插入图片  res.url为服务器返回的图片地址
        quill.insertEmbed(length, "image", res.url);
        // 调整光标到最后
        quill.setSelection(length + 1);
      } else {
        this.$message.error("图片插入失败");
      }
      // loading动画消失
      this.quillUpdateImg = false;
    },
    // 富文本图片上传失败
    uploadError() {
      // loading动画消失
      this.quillUpdateImg = false;
      this.$message.error("图片插入失败");
    }
  }
};
</script> 

<style>
.editor {
  line-height: normal !important;
  height: 800px;
}
</style>
