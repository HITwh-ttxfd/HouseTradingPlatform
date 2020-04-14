<template>
  <el-container>
    <el-header>
      <el-row :gutter="20" type="flex">
        <el-col :span="12"><div class="grid-content bg-purple">二手房交易系统</div></el-col>
        <el-col :span="6" :offset="20">
          <div>
          <router-link to="/">
            <el-button type="text">
              退出登录
            </el-button>
          </router-link>
          </div>
        </el-col>
      </el-row>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
          default-active="$route.path"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          router
          unique-opened>
          <el-menu-item index="myHouses">
            <span slot="title">我的房源</span>
          </el-menu-item>
          <el-submenu index="info">
            <template slot="title">
              <span>消息中心</span>
            </template>
            <el-menu-item index="requests">看房请求</el-menu-item>
            <el-menu-item index="conversations">会话</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "patient"
  }
</script>

<style scoped>
  .el-header{
    background-color: #414850;
    color: #E9EEF3;
    text-align: left;
    line-height: 60px;
    margin-top: 0;
    border-bottom: none;
    float: right;
  }
  .el-main {
    /*background-color: #E9EEF3;*/
    color: #333;
    text-align: center;
    line-height: 160px;
  }
  .el-aside{
    background: #545c64;
    border-right: none;
  }
  .el-menu{
    border-right: none;
  }
  .grid-content{
    font-size: 20px;
    font-weight: bold;
    position: absolute;
  }
</style>
