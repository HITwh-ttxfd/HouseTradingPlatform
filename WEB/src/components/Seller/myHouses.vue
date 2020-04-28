<template>
  <el-table stripe :data="houses" >
    <el-table-column
            prop="location"
            label="位置"
            align="center">
    </el-table-column>
    <el-table-column
            prop="size"
            label="面积"
            align="center">
    </el-table-column>
    <el-table-column
            prop="price"
            label="价格"
            align="center">
    </el-table-column>
    <el-table-column
            align="center">
      <template slot="header" slot-scope="scope" style="align-content: center">
        <el-button
                size="mini"
                type="primary"
                style="width: 40%;"
                @click="add()">添加房源</el-button>
      </template>
      <template slot-scope="scope">
        <el-button
                size="mini"
                @click="detail(scope.$index, scope.row)">详情</el-button>
        <el-button
                size="mini"
                type="danger"
                @click="delete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    name: "myHouse",
    data(){
      return{
        searchInfo:'',
        houses:[{
          location:'山东省威海市文化西路2号',
          size:'150平米',
          price:'1500000元'
        }],
        conversation:{
          receiverID:'',
          date:'',
          recentMessage:''
        }
      }
    },
    methods:{
      load(){

      },
      add(){

      },
      detail(index,row){

      },
      delete(index,row){

      }
    },
    mounted() {
      // load();
    }
  }
</script>

<style scoped>
  .el-table__header tr,
  .el-table__header th {
    padding: 0;
    height: 40px;
  }
  .el-table__body tr,
  .el-table__body td {
    padding: 0;
    height: 40px;
  }
</style>
