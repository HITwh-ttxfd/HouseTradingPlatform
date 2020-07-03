<template>
    <div>
        <el-table height="650px" stripe :data="conversations" v-loading="loading">
            <el-table-column
                    prop="username"
                    label="用户名"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="date"
                    label="时间"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="recentMessage"
                    label="最近消息"
                    align="center">
            </el-table-column>
            <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="detail(scope.row)">详情</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog width="500px" :visible.sync="messageVisible" :title="username" @close="clear">
            <chatting v-if="loaded" :username="username" :id="id"></chatting>
        </el-dialog>
    </div>
</template>

<script>
    import Chatting from "./chatting";
    export default {
        name: "conversation",
        components: {Chatting},
        data(){
            return{
                loaded: false,
                loading: true,
                messageVisible:false,
                username:'',
                id:'',
                searchInfo:'',
                conversations:[{        //消息列表
                    id:'',       //对方id
                    username:'',         //对方用户名
                    time:'',     //最近一条消息的发送时间
                    recentMessage:'',    //最近一条消息
                    unreadCount: 0
                }]
            }
        },
        methods:{
            load(){
                this.$axios({
                    method: 'GET',
                    url: 'http://192.168.11.1:10080/SRservice/selectConversation/'+localStorage.username
                    // http://localhost:8080/SRservice/selectConversation
                }).then(res=>{
                    this.conversations=res.data;
                    this.loading=false;
                })
            },
            detail(row){
                new Promise((resolve, reject)=>{
                    while (this.username!==row.username||this.id!==row.id) {
                        this.username = row.username;
                        this.id = row.id;
                    }
                    resolve('success');
                }).then(res=>{
                    this.messageVisible=true;
                    this.loaded=true;
                })
            },
            clear(){
                this.username='';
                this.id='';
                this.loaded=false;
            }
        },
        mounted() {
            this.load();
        }
    }
</script>

<style scoped>

</style>