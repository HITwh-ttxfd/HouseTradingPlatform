<template>
    <div>
        <el-table stripe :data="conversations" >
            <el-table-column
                    prop="username"
                    label="用户名"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="time"
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
                messageVisible:false,
                searchInfo:'',
                conversations:[{        //消息列表
                    id:'18030864538',       //对方id
                    username:'王大锤',         //对方用户名
                    time:'2020-4-28 10:00',     //最近一条消息的发送时间
                    recentMessage:'Hello World!'    //最近一条消息
                    //能不能在排序的时候顺便算出来未读消息的条数
                }],
                messages: {
                    name:'王大锤',
                    id: '18030864538',
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

            },
            detail(index){
                this.messageVisible=true;
            }
        },
        mounted() {
            // load();
        }
    }
</script>

<style scoped>

</style>