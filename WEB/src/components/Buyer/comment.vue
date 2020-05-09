<template>
    <div class="commentList">
        <div class="comment" v-for="comment in comments">
            <p class="date">{{comment.date}}</p>
            <el-rate disabled text-color="#ff9900" v-model="comment.score">
            </el-rate>
            <div class="content">
                <pre>{{comment.content}}</pre>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "comment",
        props:{
            comments: Array
        },
        beforeDestroy() {
            this.$emit('clear');
        }
    }
</script>

<style scoped>
    .comment{
        display: block;
        text-align: left;
        margin: 10px 10px 10px 10px;
    }
    .comment .el-rate{
        float: right;
        display: inline-block;
    }
    .comment .date{
        text-align: left;
        display: inline-block;
    }
    .content{
        background-color: rgba(231, 231, 231, 0.8);
        padding: 5px 5px 5px 5px;
    }
    pre {
        white-space: pre-wrap;
        white-space: -moz-pre-wrap;
        white-space: -o-pre-wrap;
        *word-wrap: break-word;
        *white-space : normal ;
    }
    .commentList{
        max-height: 500px;
        overflow: scroll;
    }
</style>