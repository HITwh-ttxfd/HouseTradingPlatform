<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>浏览房源</el-breadcrumb-item>
        </el-breadcrumb>
        <el-table
                v-loading="loading"
                :data="houses">
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
                            disabled
                            show-score
                            text-color="#ff9900"
                            v-model="scope.row.score">
                    </el-rate>
                </template>
            </el-table-column>
            <el-table-column
                    align="center">
                <template slot="header" slot-scope="scope">
                    <el-button
                            @click="select()"
                            size="small"
                            type="primary"><i class="el-icon-setting"> 筛选</i>
                    </el-button>
                    <el-button
                            @click="reset()"
                            size="small"
                            type="info"><i class="el-icon-refresh-left"> 重置</i>
                    </el-button>
                </template>
                <template slot-scope="scope">
                    <el-button
                            @click="detail(scope.row.houseID)"
                            size="small">详情
                    </el-button>
                    <el-button
                            type="success"
                            @click="request(scope.row.sellerID,scope.row.houseID)"
                            size="small">看房
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog :visible.sync="detailVisible" title="详细信息" destroy-on-close @closed="destroyMap">
            <detail :house-detail="houseDetail"></detail>
        </el-dialog>
        <el-dialog :visible.sync="requestVisible" title="预约看房" destroy-on-close>
            <request :form="requestForm" @confirm="requestConfirmed" @close="closeRequest"></request>
        </el-dialog>
        <el-dialog :visible.sync="filterVisible" title="筛选" destroy-on-close>
            <search :form="filter" @selectionEnabled="selectionConfirmed" @close="closeFilter"></search>
        </el-dialog>
    </div>
</template>

<script>
    import Detail from "./detail";
    import Request from "./request";
    import search from "./search";
    let map;
    export default {
        name: "buyerHouses",
        components: {search, Request, Detail},
        data() {
            return {
                filterVisible: false,
                requestVisible: false,
                detailVisible: false,
                loading: true,
                houses: [],
                filter: {
                    province: '',
                    city: '',
                    district: '',
                    street:'',
                    community:'',
                    mimSize:10,
                    maxSize:1000,
                    mimPrice: 50000,
                    maxPrice: 100000000,
                    age:50,
                    rate:0.0
                },
                filterBackup:{
                    province: '',
                    city: '',
                    district: '',
                    street:'',
                    community:'',
                    mimSize:10,
                    maxSize:1000,
                    mimPrice: 50000,
                    maxPrice: 100000000,
                    age:50,
                    rate:0.0
                },
                requestForm:{
                    senderID:'',
                    sellerID:'',
                    houseId:'',
                    phoneNumber:'',
                    date:'',
                    time:''
                },
                houseDetail: {}
            }
        },
        methods: {
            reset(){
                this.load()
                Object.assign(this.$data.request, this.$options.data().request);
            },
            select() {
                this.filterVisible=true;
                this.filterBackup = JSON.parse(JSON.stringify(this.filter));
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
            request(sellerID,houseID){
                this.requestVisible=true;
                this.requestForm.senderID=window.localStorage['username'];
                this.requestForm.sellerID=sellerID;
                this.requestForm.houseId=houseID;
            },
            closeRequest(){
                this.requestVisible=false;

            },
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
            requestConfirmed(id){
                this.requestVisible=false;
                let request=this.requestForm;
                this.$axios({
                    url: 'http://localhost:8080/SRservice/sendRequest/'+request.senderID+'/'+request.sellerID +'/'+request.houseId+'/'+request.phoneNumber,
                    method: 'GET',
                    params:{
                        date: request.date,
                        time: request.time
                    }
                }).then(res=>{
                    this.$notify({
                        title: '申请成功',
                        message: '等待卖家做出回应',
                        type: 'success'
                    });
                }).catch(e=>{
                    this.$notify({
                        title: '申请失败',
                        message: '请稍后再试',
                        type: 'error'
                    });
                });
                Object.assign(this.$data.requestForm, this.$options.data().requestForm);
            },
            selectionConfirmed(){
                let filter=this.filter;
                this.filterVisible=false;
                this.$axios({
                    url: 'http://localhost:8080/select',
                    method: 'GET',
                    params:{
                        village: filter.community,
                        position: filter.province+filter.city+filter.district+filter.street,
                        mimSize: filter.mimSize,
                        maxSize: filter.maxSize,
                        mimPrice: filter.mimPrice,
                        maxPrice: filter.maxPrice,
                        score: filter.rate,
                        time: filter.age
                    }
                }).then(res=>{
                    this.houses=res.data;
                }).catch(e=>{
                    this.$message({
                        message: '筛选失败',
                        type: 'error'
                    });
                })
            },
            closeFilter(){
                this.filterVisible=false;
                Object.assign(this.$data.filter, this.$options.data().filter);
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
    .el-table-column {
        overflow: hidden;
    }

    #AMap {
        margin-top: 0;
        width: 100%;
        height: 200px;
    }

    .el-table {
        margin-top: 10px;
    }
</style>
