<template>
    <div class="wrapper">
        <div class="head">
            <el-rate
                    v-model="comment.score"
                    :colors="colors">
            </el-rate>
            <p>{{comment.date}}</p>
        </div>
        <el-input
                type="textarea"
                placeholder="请输入内容"
                v-model="comment.content"
                maxlength="200"
                rows="5"
                show-word-limit>
        </el-input>
        <div class="dialog-footer">
            <el-button @click="$emit('close')">取 消</el-button>
            <el-button type="primary" @click="confirmed">确 定</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "newComment",
        props:{
            house_id:String,
            author_id: String,
            seller_id: String
        },
        data(){
            return{
                colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
                comment:{
                    score: 0,
                    content: '',
                    date: '',
                }
            }
        },
        methods:{
            confirmed(){
                this.$axios({
                    method: 'GET',
                    url: 'http://localhost:8080/SRservice/sendComment/'+this.author_id,
                    params:{
                        content: this.comment.content,
                        score: this.comment.score.toString(),
                        houseID: this.house_id,
                        date: this.comment.date
                    }
                }).then(res=>{
                    if (res.data!=='success')
                        this.$message.error('提交失败');
                }).catch(e=>{
                    this.$message.error('提交失败');
                    console.log(e);
                });
                this.$axios({
                    method: 'GET',
                    url: 'http://localhost:8080/SRservice/changeRequestStatus/'+this.author_id+'/'+this.seller_id+'/'+this.house_id,
                    params:{
                        status: '4'
                    }
                }).then(res=>{
                    this.$message.success('提交成功');
                    this.$emit('close');
                    this.$emit('load');
                }).catch(e=>{
                    console.log(e);
                });
            }
        },
        mounted() {
            this.comment.date=(new Date()).toLocaleDateString(
                [], {year: 'numeric', month: 'long', day: 'numeric'});
        }
    }
</script>

<style scoped>
    .head{
        height: 30px;
    }

    .el-rate{
        float: left;
    }

    p{
        float: right;
        color: rgba(175, 175, 175, 0.83);
    }

    .dialog-footer{
        vertical-align: center;
        float: right;
        margin-top: 20px;
    }

    .wrapper{
        overflow: scroll;
    }
</style>