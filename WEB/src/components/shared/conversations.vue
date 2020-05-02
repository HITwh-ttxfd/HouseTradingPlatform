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
        <el-dialog width="500px" :visible.sync="messageVisible" :title="messages.name" destroy-on-close>
            <chatting :messages="messages"></chatting>
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
                loading: true,
                messageVisible:false,
                searchInfo:'',
                conversations:[{        //消息列表
                    id:'18030864538',       //对方id
                    username:'王大锤',         //对方用户名
                    time:'2020-4-28 10:00',     //最近一条消息的发送时间
                    recentMessage:'Hello World!',    //最近一条消息
                    unreadCount: 0
                }],
                messages: {
                    name:'王大锤',
                    id: '17861080088',
                    messageList:[{
                        sender: '18030864538',
                        receiver: '17861080088',
                        time: '2020-4-28 10:00',
                        content: 'Hello World!',
                        read: false
                    },{
                        sender: '17861080088',
                        receiver: '1803084538',
                        time: '2020-4-28 11:00',
                        content: 'Hello Vue!',
                        read: false
                    }]
                }
            }
        },
        methods:{
            load(){
                this.$axios({
                    method: 'GET',
                    url: 'http://localhost:8080/SRservice/selectConversation/'+localStorage.username
                }).then(res=>{
                    this.conversations=res.data;
                    this.loading=false;
                })
            },
            detail(row){
                this.messageVisible=true;
                this.$axios({
                    method: 'GET',
                    url: 'http://localhost:8080/SRservice/receiveMessages/'+localStorage.username+'/'+row.id
                }).then(res=>{
                    this.messages.name=row.username;
                    this.messages.id=row.id;
                    this.messages.messageList=res.data.reverse();
                    this.messageVisible=true;
                }).catch(e=>{
                    console.log(e);
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