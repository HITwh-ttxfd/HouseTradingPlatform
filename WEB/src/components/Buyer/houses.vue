<template>
    <el-table
      :data="houses">
      <el-table-column
        prop="id"
        label="房源号"
        align="center">
      </el-table-column>
      <el-table-column
        prop="location"
        label="位置"
        align="center">
      </el-table-column>
      <el-table-column
        prop="size"
        label="大小"
        align="center">
      </el-table-column>
      <el-table-column
        prop="price"
        label="价格"
        align="center">
      </el-table-column>
    </el-table>
</template>

<script>
    export default {
      name: "buyerHouses",
      data(){
          return{
            houses: [],
          }
      }
    }
</script>

<style scoped>

</style>
