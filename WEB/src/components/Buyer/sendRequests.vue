<template>
    <div>
        <el-table
                height="650px"
                v-loading="loading"
                :data="undefeatedRequests">
            <el-table-column
                    align="center"
                    property="location"
                    label="地址">
            </el-table-column>
            <el-table-column align="center" label="预约日期">
                <template slot-scope="scope">
                    {{displayDate(scope.row.date)}}
                </template>
            </el-table-column>
            <el-table-column
                    align="center"
                    property="time"
                    label="预约时间">
            </el-table-column>
            <el-table-column
                    align="center"
                    property="phone"
                    label="买家联系电话">
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
                            v-if="scope.row.status==='0'|| scope.row.status==='2'"
                            @click="cancel(scope.row)"
                            size="mini"
                            type="danger">撤销
                    </el-button>
                    <el-button
                            v-else-if="scope.row.status==='3'"
                            @click="comment(scope.row.houseID,scope.row.receiverID)"
                            size="mini"
                            type="primary">评价
                    </el-button>
<!--                    <el-button-->
<!--                            v-else-->
<!--                            @click="defeat(scope.row)"-->
<!--                            size="mini"-->
<!--                            type="info">删除-->
<!--                    </el-button>-->
                </template>
            </el-table-column>
        </el-table>
        <el-dialog :visible.sync="detailVisible" title="详细信息" destroy-on-close @closed="destroyMap">
            <detail :house-detail="houseDetail"></detail>
        </el-dialog>
        <el-dialog width="500px" :visible.sync="commentVisible" title="评价" destroy-on-close>
            <new-comment @load="load" @close="commentVisible=false" :seller_id="sellerID" :author_id="authorID" :house_id="commentHouseID"></new-comment>
        </el-dialog>
    </div>
</template>

<script>
    import Detail from "./detail";
    import NewComment from "./newComment";
    import seller from "../Seller/seller";
    let map;
    export default {
        name: "sendRequests",
        components: {NewComment, Detail},
        data() {
            return {
                Requests: [],
                detailVisible: false,
                commentHouseID: '',
                authorID:'',
                sellerID:'',
                commentVisible: false,
                houseDetail:{},
                loading: true
            }
        },
        methods: {
            cancel(row){
                this.loading=true;
                this.changeStatus(localStorage.username,row.receiverID,row.houseID,'5').then(res=>{
                    this.$message.success("处理成功");
                }).catch(e=>{
                    this.$message.error("处理失败");
                })
                this.load();
            },
            defeat(row) {
                this.loading=true;
                if (row.status!=='7') {
                    this.changeStatus(localStorage.username,row.receiverID,row.houseID,'6').then(res=>{
                        this.$message.success("处理成功");
                    }).catch(e=>{
                        this.$message.error("处理失败");
                    })
                    this.load();
                }
                else{
                    this.changeStatus(localStorage.username,row.receiverID,row.houseID,'8').then(res=>{
                        this.$message.success("处理成功");
                    }).catch(e=>{
                        this.$message.error("处理失败");
                    })
                    this.load();
                }
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
                    case '4': return '已评价';
                    case '5': return '已撤回';
                }
            },
            comment(houseID,sellerID){
                this.commentHouseID=houseID;
                this.authorID=localStorage.username;
                this.sellerID=sellerID;
                this.commentVisible=true;
            },
            changeStatus(buyerID,sellerID,houseID,status){
                return this.$axios({
                    method: 'GET',
                    url: 'http://localhost:8080/SRservice/changeRequestStatus/'+buyerID+'/'+sellerID+'/'+houseID,
                    params:{
                        status: status
                    }
                })
            }
        },
        mounted() {
            this.load();
        },
        computed:{
            undefeatedRequests: function(){
                return this.Requests.filter(v => v.status !== '6' && v.status !== '8')
            }
        }
    }
</script>

<style scoped>

</style>
