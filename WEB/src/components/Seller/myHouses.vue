<template>
  <div>
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
                  @click="detail(scope.row.houseID)">详情</el-button>
          <el-button
                  size="mini"
                  type="danger"
                  @click="delete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="detailVisible" title="详细信息" destroy-on-close @closed="destroyMap">
      <detail :house-detail="houseDetail"></detail>
    </el-dialog>
    <el-dialog width="500px" :visible.sync="configVisible" title="添加房源" destroy-on-close>
      <new-house></new-house>
    </el-dialog>
  </div>
</template>

<script>
  import Detail from "../Buyer/detail";
  import NewHouse from "./newHouse";
  let map;
  export default {
    name: "myHouse",
    components: {NewHouse, Detail},
    data(){
      return{
        detailVisible:false,
        configVisible: false,
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
        },
        houseDetail:{}
      }
    },
    methods:{
      load() {
        this.$axios({
          url: 'http://localhost:8080/houseList',
          method: 'GET'
        }).then(res=>{
          this.loading=false;
          this.houses=res.data;
        }).catch(e=>{
          console.log(e);
        })
      },
      add(){
        this.configVisible=true;
      },
      detail(id) {
        this.$axios({
          url: 'http://localhost:8080/house/'+id,
          method: 'GET'
        }).then(res=>{
          this.detailVisible = true;
          this.houseDetail=res.data;
          this.$axios({
            url: 'http://localhost:8080/imgManage/selectImg/'+id,
            method: 'GET',
          }).then(res=>{
            this.$set(this.houseDetail,'imgs',res.data);
          }).catch(e=>{
            console.log(e);
          })
          setTimeout(function () {
            map = new AMap.Map('AMap', {
              resizeEnable: true,
              center: [res.data.locationX,res.data.locationY],
              zoom: 13
            });
            let marker = new AMap.Marker({
              position: [res.data.locationX,res.data.locationY]
            })
            map.add(marker);
          }, 30);
        }).catch(e=>{
          console.log(e);
        })
      },
      delete(index,row){

      },
      destroyMap(){
        map.destroy();
      }
    },
    mounted() {
      this.load();
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
