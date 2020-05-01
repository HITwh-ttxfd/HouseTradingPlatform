<template>
  <div>
    <el-table
            height="650px"
            v-loading="loading"
            :data="undefeatedRequests">
      <el-table-column
              align="center"
              label="房源"
              prop="location">
      </el-table-column>
      <el-table-column align="center" label="预约日期">
        <template slot-scope="scope">
          {{displayDate(scope.row.date)}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态">
        <template slot-scope="scope">
          {{displayStatus(scope.row.status)}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button
                  @click="detail(scope.row.houseID)"
                  size="mini">详情
          </el-button>
          <el-button
                  v-if="scope.row.status==='0'"
                  @click="permit(scope.row)"
                  size="mini"
                  type="success">同意
          </el-button>
          <el-button
                  v-if="scope.row.status==='0'"
                  @click="refuse(scope.row)"
                  size="mini"
                  type="danger">拒绝
          </el-button>
<!--          <el-button-->
<!--                  v-if="scope.row.status==='3'||scope.row.status==='4'||-->
<!--                  scope.row.status==='5'||scope.row.status==='6'"-->
<!--                  @click="defeat(scope.row)"-->
<!--                  size="mini"-->
<!--                  type="info">删除-->
<!--          </el-button>-->
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="detailVisible" title="详细信息" destroy-on-close @closed="destroyMap">
      <detail :house-detail="houseDetail"></detail>
    </el-dialog>
  </div>
</template>

<script>
  import Detail from "../Buyer/detail";
  let map;
  export default {
    name: "receiveRequests",
    components: {Detail},
    data() {
      return {
        Requests: [],
        detailVisible: false,
        houseDetail:{},
        loading: true
      }
    },
    methods: {
      permit(row){
        this.loading=true;
        if(this.changeStatus(row.senderID,localStorage.username,row.houseID,'2'))
          this.$message.success("处理成功");
        this.load();
      },
      refuse(row){
        this.loading=true;
        if(this.changeStatus(row.senderID,localStorage.username,row.houseID,'1'))
          this.$message.success("处理成功");
        this.load();
      },
      defeat(row) {
        this.loading=true;
        if (row.status!=='6') {
          if (this.changeStatus(row.senderID, localStorage.username, row.houseID, '7'))
            this.$message.success("处理成功");
        }
        else {
          if (this.changeStatus(row.senderID, localStorage.username, row.houseID, '8'))
            this.$message.success("处理成功");
        }
        this.load();
      },
      destroyMap(){
        map.destroy();
      },
      detail(id) {
        this.$axios({
          url: 'http://localhost:8080/house/'+id,
          method: 'GET'
        }).then(res=>{
          this.houseDetail=res.data;
          this.$axios({
            url: 'http://localhost:8080/imgManage/selectImg/'+id,
            method: 'GET',
          }).then(res=>{
            this.$set(this.houseDetail,'imgs',res.data);
          }).catch(e=>{
            console.log(e);
          })
          this.detailVisible = true;
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
      load(){
        this.$axios({
          url: 'http://localhost:8080/SRservice/receiveRequests/'+localStorage['username'],
          method: 'GET',
        }).then(res=>{
          this.loading=false;
          this.Requests=res.data;
        }).catch(e=>{
          this.$message.error("拉取请求列表失败");
        })
      },
      displayDate(date){
        return date.substring(0,10);
      },
      displayStatus(status){
        switch(status){
          case '0': return '等待审核';
          case '1': return '卖家拒绝';
          case '2': return '卖家同意';
          case '3': return '未评价';
          case '6':
          case '4': return '已评价';
          case '5': return '已撤回';

        }
      },
      changeStatus(buyerID,sellerID,houseID,status){
        this.$axios({
          method: 'GET',
          url: 'http://localhost:8080/SRservice/changeRequestStatus/'+buyerID+'/'+sellerID+'/'+houseID,
          params:{
            status: status
          }
        }).then(res=>{
          return true;
        }).catch(e=>{
          console.log(e);
          return false;
        });
      }
    },
    computed:{
      undefeatedRequests: function(){
        return this.Requests.filter(v => v.status !== '7' && v.status !== '8')
      }
    },
    mounted() {
      this.load();
    }
  }
</script>

<style scoped>

</style>
