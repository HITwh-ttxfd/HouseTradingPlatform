<template>
    <el-table
    :data="Requests">
    <el-table-column
      prop="House"
      label="房源"
      align="center">
    </el-table-column>
    <el-table-column
      prop="date"
      label="提交日期"
      align="center">
    </el-table-column>
    <el-table-column
      prop="status"
      label="请求状态"
      align="center">
    </el-table-column>
  </el-table>
</template>

<script>
    export default {
      name: "sendRequests",
      data(){
        return{
          Requests:[]
        }
      },
      methods: {
        searchDepartment(){
          console.log("searched!");
        }
      }
    }
</script>

<style scoped>

</style>
