<template>
  <div id="upload">
    <el-upload
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
            accept="image/png,image/jpg,image/jpeg"
            :multiple="true"
            :limit="10"
            :on-exceed="handleExceed"
            :before-upload="beforeUpload"
            :on-success="handleSuccess"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="imageRemove"
            :file-list="fileList"
            list-type="picture">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过4MB</div>
    </el-upload>
  </div>
</template>
<!--https://element.eleme.cn/#/zh-CN/component/upload-->
<script>
  import axios from 'axios'
  export default {
    name: 'receiveRequests',
    data () {
      return {
        fileList: [],
        str: ''
      }
    },
    methods: {
      handleRemove (file, fileList) {
        console.log(file, fileList)
      },
      imageRemove (file, fileList) {
        console.log(file, fileList)
        var houseID = '000001'
        // 这个有问题 不能用
        axios.get('http://localhost:8080/imgManage/delImg/' + houseID + '/' + file.name).then(function (res) {
          if (res.data === 'fail') {
            return false
          }
        }).catch(function (error) {
          console.log(error)
        })
        return true
      },
      handleSuccess (response, file, fileList) {
        this.$message.success('上传成功')
      },
      handlePreview (file) {
        console.log(file)
        // this.$message.info(file.name)
      },
      beforeUpload (file) {
        console.log('test', file.type)
        const isImage = file.type.indexOf('image') !== -1
        const isLi4M = file.size / 1024 / 1024 < 4
        if (!isImage) {
          this.$message.error('上传的文件不是图片类型')
          return false
        }
        if (!isLi4M) {
          this.$message.error('上传图片大小超过4MB')
          return false
        }
        // 类型匹配
        if (file && file.type.match('image.*')) {
          this.getBase64(file).then(res => {
            // console.log('base', res)
            // 传输文件使用base64编码传输，getBase64是把文件转换为编码的函数
            var houseID = '1'
            var data = {'file': res, 'fileName': file.name, 'houseID': houseID}
            console.log(data)
            // 此处等待传入houseID
            // 测试数据
            // 下面传输图片
            axios.post('http://localhost:8080/imgManage/uploadImg64', data, { headers: { 'Content-Type': 'application/json' } }).then(function (res) {
              console.log(file.name, res.data)
              if (res.data === 'fail') {
                return false
                // this.$message.success('上传成功')
              } else if (res.data === 'Error') {
                // this.$message.error('上传失败')
                return false
              } else {
                return true
              }
            }).catch(err => {
              console.log(err)
            })
          })
        }
      },
      handleExceed (files, fileList) {
        this.$message.warning(`限制选择 10 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
      },
      getBase64 (file) {
        // 把文件转换为base64编码的函数，FileReader类似乎不能直接调用 需要使用Promise封装
        return new Promise(function (resolve, reject) {
          let reader = new FileReader()
          let imgResult = ''
          reader.readAsDataURL(file)
          reader.onload = function () {
            imgResult = reader.result
          }
          reader.onerror = function (error) {
            reject(error)
          }
          reader.onloadend = function () {
            resolve(imgResult)
          }
          return reader.result
        })
      },
      selectImg () {
        var houseID = '000001'
        // 等待传入图片列表
        axios.get('http://localhost:8080/imgManage/selectImg/' + houseID + '/')
      }
    }
  }
</script>

<style scoped>
  @import url("//unpkg.com/element-ui@2.13.1/lib/theme-chalk/index.css");
</style>
