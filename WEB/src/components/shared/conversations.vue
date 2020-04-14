<template>
  <el-table
    :data="conversations">
    <el-table-column
      prop="receiverID"
      label="用户名"
      align="center">
    </el-table-column>
    <el-table-column
      prop="date"
      label="日期"
      align="center">
    </el-table-column>
    <el-table-column
      prop="recentMessage"
      label="最近消息"
      align="center">
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    name: "conversation",
    data(){
      return{
        conversations:[],
        conversation:{
          receiverID:'',
          date:'',
          recentMessage:''
        }
      }
    },
    methods:{

    }
  }
</script>

<style scoped>

</style>
