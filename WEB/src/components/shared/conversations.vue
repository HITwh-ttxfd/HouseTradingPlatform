<template>
  <el-table stripe :data="conversations" >
    <el-table-column
      prop="guestID"
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
    <el-table-column
            align="center">
      <template slot="header" slot-scope="scope">
        <el-input
                v-model="searchInfo"
                size="mini"
                style="width: 70%"
                placeholder="输入用户名搜索"/>
        <el-button
                size="mini"
                type="primary"
                @click="search()">搜索</el-button>
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
    name: "conversation",
    data(){
      return{
        searchInfo:'',
        conversations:[{
          guestID:'王大锤',
          date:'14:00',
          recentMessage:'Hello World!'
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
      search(){

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

</style>
