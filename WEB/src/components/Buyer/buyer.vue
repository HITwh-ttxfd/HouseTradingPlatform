<template>
  <el-container>
    <el-header>
      <el-row :gutter="20" type="flex">
        <el-col :span="12"><div class="grid-content bg-purple">二手房交易系统</div></el-col>
        <el-col :span="6" :offset='20'>
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
          background-color="#324157"
          text-color="#bfcbd9"
          active-text-color="#20a0ff"
          router
          unique-opened>
          <el-menu-item index="houses">
            <i class="el-icon-s-home"></i>
            <span slot="title">浏览房源</span>
          </el-menu-item>
          <el-submenu index="Info">

            <template slot="title"><i class="el-icon-s-unfold"></i><span>信息中心</span></template>
          <el-menu-item index="conversations">
            <span slot="title">会话</span>
          </el-menu-item>
          <el-menu-item index="requests">
            <span slot="title">看房请求</span>
          </el-menu-item>
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
        name: "buyer"
    }
</script>

<style scoped>
  .el-header{
    background-color: #242f42;
    color: #E9EEF3;
    text-align: left;
    line-height: 60px;
    margin-top: 0;
    border-bottom: none;
    float: right;
  }
  .el-main {
    background-color: #E9EEF3;
    text-align: center;
    line-height: 20px;
  }
  .el-aside{
    background: #324157;
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
