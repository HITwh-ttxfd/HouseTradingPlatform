<template>
    <div>
    <el-table
            height="650px"
            v-loading="loading"
            :data="Requests">
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
                        v-if="scope.row.status==='0'|| scope.row.status==='2'"
                        @click="this.cancel(scope.row)"
                        size="mini"
                        type="danger">撤销
                </el-button>
                <el-button
                        v-else
                        @click="this.delete(scope.row)"
                        size="mini"
                        type="info">删除
                </el-button>
            </template>
        </el-table-column>
    </el-table>
        <el-dialog :visible.sync="detailVisible" title="详细信息" destroy-on-close @closed="destroyMap">
            <detail :house-detail="houseDetail"></detail>
        </el-dialog>
    </div>
</template>

<script>
    import Detail from "./detail";
    let map;
    export default {
        name: "sendRequests",
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
            cancel(){

            },
            delete() {

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
                    case '4': return '已评价'
                }
            }
        },
        mounted() {
            this.load();
        }
    }
</script>

<style scoped>

</style>
