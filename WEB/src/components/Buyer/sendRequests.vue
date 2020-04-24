<template>
    <el-table
            :data="Requests">
        <el-table-column
                align="center"
                label="房源"
                prop="House">
        </el-table-column>
        <el-table-column
                align="center"
                label="提交日期"
                prop="date">
        </el-table-column>
        <el-table-column
                align="center"
                label="请求状态"
                prop="status">
        </el-table-column>
        <el-table-column align="center" label="操作">
            <template slot-scope="scope">
                <el-button
                        @click="detail(scope.row)"
                        size="mini">详情
                </el-button>
                <el-button
                        @click="this.delete(scope.row)"
                        size="mini"
                        type="danger">删除
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
                    url: 'http://localhost:8080/receiveRequests/'+localStorage['username'],
                    method: 'GET',
                }).then(res=>{
                    this.Requests=res.data;
                }).catch(e=>{
                    this.$message.error("拉取请求列表失败");
                })
            }
        },
        mounted() {
            this.load();
        }
    }
</script>

<style scoped>

</style>
