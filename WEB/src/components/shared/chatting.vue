<template>
  <div>

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
      search(){

      },
      detail(index,row){

      },
      delete(index,row){

      }
    },
    mounted() {
      // load();
    }
  }
</script>

<style scoped>
  .message{
    display: block;
    overflow: auto;
    max-width: 300px;
    background-color: rgba(231, 231, 231, 0.8);
  }
  .other{
    float: left;
    margin-left: 10px;
  }
  .me{
    float: right;
    margin-right: 10px;
  }
  .chattingWrapper{
    border-bottom: 1px solid #e1e8ec;
    border-top: 1px solid #e1e8ec;

  }
</style>
