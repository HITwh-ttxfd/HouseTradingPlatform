<template>
  <div>
    <el-main id="messageList">
      <div v-for="message in messages.messageList"
           :class="{wrapper: true,me: isMine(message.sender),other: !isMine(message.sender)}">
        <p class="time">{{message.time}}</p>
        <div class="content">
          <pre>{{message.content}}</pre>
        </div>
      </div>
    </el-main>
    <el-footer>
      <p class="input" contenteditable="true" placeholder="按Enter发送，Shift+Enter换行"
           @keydown.enter="sendMessage" @keydown.shift.enter="enter">
      </p>
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
        entered: false
      }
    },
    methods:{
      load(){
        this.$refs.messageList.scrollTop=this.$refs.messageList.scrollHeight;
      },
      isMine(sender){
        return sender === localStorage['username'];
      },
      sendMessage($event){
       setTimeout(()=>{
         if (this.entered)
           this.entered=false;
         else{
           $event.target.innerText= $event.target.innerText.slice(0,-2);
           this.messages.messageList.push({
             sender: localStorage['username'],
             receiver: this.messages.id,
             time: '2020-4-28 11:00',
             content: $event.target.innerText,
             read: false
           });
           $event.target.innerText='';
           $event.preventDefault();
           this.$nextTick(()=>{
             document.getElementById('messageList').scrollTop=document.getElementById('messageList').scrollHeight;
           })
         }
       },60)
      },
      enter(){
        this.innerText += '\n';
        this.entered=true;
      }
    },
    computed:{

    },
    mounted() {
      // load();
      setTimeout(()=>{
        document.getElementById('messageList').scrollTop=document.getElementById('messageList').scrollHeight;
      },30);
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
  .content pre{
    text-align: left;
  }
  .time{
    color: rgba(175, 175, 175, 0.83);
    font-size: 10px;
  }
  .other{
    float: left;
  }
  .other .time{
    text-align: left;
  }
  .me{
    float: right;
  }
  .me .time{
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
  .input{
    margin-top: 10px;
    margin-right: 0;
    margin-left: 0;
    text-align: left;
    outline: none;
    width: 100%;
    min-height: 100%;
  }
  .input:empty:before{
    content:attr(placeholder);
    font-size: 13px;
    color: #999999;
  }
  .input:focus:before {
    content: none;
  }
  pre {
    white-space: pre-wrap;
    white-space: -moz-pre-wrap;
    white-space: -o-pre-wrap;
    *word-wrap: break-word;
    *white-space : normal ;
  }
</style>
