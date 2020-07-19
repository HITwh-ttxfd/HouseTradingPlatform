<template>
    <router-view id="app"/>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: left;
  color: #2c3e50;
  height: 100%;
}
::-webkit-scrollbar{
    width: 5px;
    height: 5px;
}
::-webkit-scrollbar-thumb{
    border-radius: 1em;
    background-color: rgba(50,50,50,.3);
}
::-webkit-scrollbar-track{
    border-radius: 1em;
    background-color: rgba(50,50,50,.1);
}
*{
  padding: 0;
  margin: 0;
}
html,body{
  width: 100%;
  height: 100%;
}

</style>
