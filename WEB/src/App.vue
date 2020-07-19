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
        scrollbar-width: none; /* Firefox */
        -ms-overflow-style: none; /* IE 10+ */
    }

    ::-webkit-scrollbar {
        display: none;
    }


    * {
        padding: 0;
        margin: 0;
    }

    html, body {
        width: 100%;
        height: 100%;
    }

</style>
