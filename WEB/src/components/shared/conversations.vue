<template>
    <div>
        <el-table stripe :data="conversations" >
            <el-table-column
                    prop="guestID"
                    label="用户名"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="date"
                    label="日期"
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
        <el-dialog :visible.sync="messageVisible" :title="name" destroy-on-close>
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
                name:'王大锤',
                conversations:[{
                    guestID:'王大锤',
                    date:'14:00',
                    recentMessage:'Hello World!'
                }],
                conversation:{
                    receiverID:'',
                    date:'',
                    recentMessage:''
                },
                messages:[]
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