<template>
    <el-table
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
                        @click="detail(scope.row)"
                        size="mini">详情
                </el-button>
                <el-button
                        v-if="scope.row.status==='0'|| scope.row.status==='2'"
                        @click="this.delete(scope.row)"
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
</template>

<script>
    export default {
        name: "sendRequests",
        data() {
            return {
                Requests: []
            }
        },
        methods: {
            delete() {

            },
            detail() {

            },
            load(){
                this.$axios({
                    url: 'http://localhost:8080/SRservice/receiveRequests/'+localStorage['username'],
                    method: 'GET',
                }).then(res=>{
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
                    case '3': return '已完成';
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
