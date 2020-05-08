<template>
  <div>
    <el-table height="650px" stripe :data="houses" v-loading="loading">
      <el-table-column
              align="center"
              label="位置"
              prop="location">
      </el-table-column>
      <el-table-column
              align="center"
              label="小区名称"
              prop="village">
      </el-table-column>
      <el-table-column
              align="center"
              label="大小"
              prop="size">
      </el-table-column>
      <el-table-column
              align="center"
              label="价格"
              prop="price">
      </el-table-column>
      <el-table-column
              align="center"
              label="评分"
              prop="rank">
        <template slot-scope="scope">
          <el-rate
                  v-if="scope.row.count>0"
                  disabled
                  text-color="#ff9900"
                  v-model="scope.row.score">
          </el-rate>
          <p v-else>暂无评分</p>
        </template>
      </el-table-column>
      <el-table-column align="center" width="250px">
        <template slot="header" slot-scope="scope" style="align-content: center">
          <el-button
                  size="mini"
                  type="primary"
                  @click="add()"><i class="el-icon-circle-plus-outline"></i>
          </el-button>
        </template>
        <template slot-scope="scope">
          <el-button
                  type="primary"
                  @click="detail(scope.row.houseID)"
                  size="small"><i class="el-icon-more"></i>
          </el-button>
          <el-button
                  v-if="scope.row.count>0"
                  type="success"
                  @click="comment(scope.row.houseID)"
                  size="small"><i class="el-icon-chat-dot-square"></i>
          </el-button>
          <el-button
                  type="info"
                  @click="setPrice(scope.row.houseID)"
                  size="small"><i class="el-icon-edit"></i>
          </el-button>
          <el-button
                  size="mini"
                  type="danger"
                  @click="defeat(scope.row.houseID)"><i class="el-icon-delete"></i>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="detailVisible" title="详细信息" destroy-on-close @closed="destroyMap">
      <detail :house-detail="houseDetail"></detail>
    </el-dialog>
    <el-dialog width="500px" :visible.sync="configVisible" title="添加房源" destroy-on-close>
      <new-house @close="configVisible=false" @done="done"></new-house>
    </el-dialog>
    <el-dialog width="500px" :visible.sync="commentVisible" title="评价" destroy-on-close>
      <comment :comments="comments"></comment>
    </el-dialog>
  </div>
</template>

<script>
  import Detail from "../Buyer/detail";
  import NewHouse from "./newHouse";
  import Comment from "../Buyer/comment";
  let map;
  export default {
    name: "myHouse",
    components: {Comment, NewHouse, Detail},
    data(){
      return{
        loading: true,
        commentVisible: false,
        detailVisible:false,
        configVisible: false,
        searchInfo:'',
        comments:[],
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
      setPrice(id){
        this.$prompt('请输入新价格', '修改价格', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /^\d{5,10}$/,
          inputErrorMessage: '请输入5-10位阿拉伯数字（单位：元）'
        }).then(({ value }) => {
          this.$axios({
            method: 'GET',
            url: 'http://localhost:8080/changeHousePrice/'+id+'/'+value
          }).then(res=>{
            this.$message({
              type: 'success',
              message: '新价格: ' + value + '元'
            });
            this.load();
          }).catch(e=>{
            this.$message({
              type: 'error',
              message: '修改失败'
            });
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });
        });
      },
      load() {
        this.$axios({
          url: 'http://localhost:8080/sellerHouse/'+localStorage.username,
          method: 'GET'
        }).then(res=>{
          this.houses=res.data;
          this.loading = false;
        }).catch(e=>{
          console.log(e);
        })
      },
      comment(id){
        this.$axios({
          method: 'GET',
          url: 'http://localhost:8080/SRservice/houseReceiveComments/'+id,
        }).then(res=>{
          this.comments=res.data;
          this.commentVisible=true;
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
      defeat(id){
        this.loading = true;
        this.$axios({
          method: 'GET',
          url: 'http://localhost:8080/deletePastHouse/'+id
        }).then(res=>{
          this.$message.success("删除成功");
          this.load();
        }).catch(e=>{
          this.$message.error("删除失败");
          this.load();
          console.log(e);
        })
      },
      done(){
        this.configVisible=false;
        this.load();
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
  p{
    color: rgba(175, 175, 175, 0.83);
  }
</style>
