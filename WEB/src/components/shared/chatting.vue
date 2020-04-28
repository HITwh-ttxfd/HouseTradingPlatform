<template>
  <div>
    <el-main>
      <div v-for="message in messages.messageList"
           :class="{wrapper: true,me: isMine(message.sender),other: !isMine(message.sender)}">
        <p class="time">{{message.time}}</p>
        <div class="content">
          <p>{{message.content}}</p>
        </div>
      </div>
    </el-main>
    <el-footer>

    </el-footer>
  </div>
</template>

<script>
  export default {
    name: "chatting",
    props:{
      messages:Object
    },
    data(){
      return{

      }
    },
    methods:{
      load(){

      },
      isMine(sender){
        return sender === localStorage['username'];
      }
    },
    computed:{

    },
    mounted() {
      // load();
    }
  }
</script>

<style scoped>
  .content{
    padding: 5px 5px 5px 5px;
    display: block;
    overflow: auto;
    max-width: 200px;
    width: auto;
    background-color: rgba(231, 231, 231, 0.8);
  }
  .time{
    color: rgba(175, 175, 175, 0.83);
    font-size: 10px;
  }
  .other{
    float: left;
    text-align: left;
  }
  .me{
    float: right;
    text-align: right;
  }
  .el-main{
    border-top: 1px solid #e1e8ec;
    height: 300px;
    overflow: scroll;
  }
  .el-footer{
    border-top: 1px solid #e1e8ec;
    height: 100px;
    overflow: scroll;
  }
  .wrapper{
    display: block;
    clear: both;
  }
</style>
