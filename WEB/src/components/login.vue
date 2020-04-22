<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">二手房交易系统</div>
      <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="param.username" placeholder="请输入身份证号或手机号" @keyup.enter.native="submitForm()">
            <el-button slot="prepend" icon="el-icon-user"/>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            placeholder="请输入密码"
            v-model="param.password"
            show-password
            @keyup.enter.native="submitForm()">
            <el-button slot="prepend" icon="el-icon-lock"/>
          </el-input>
        </el-form-item>
        <div class="btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>
        <div class="btn">
          <el-button type="success" @click="dialogFormVisible=true">注册</el-button>
        </div>
        <el-dialog title="注册信息" :visible.sync="dialogFormVisible" destroy-on-close>
          <register @close="closeForm"></register>
        </el-dialog>
      </el-form>
    </div>
  </div>
</template>

<script>
  import Register from "./register";
  export default {
    components: {Register},
    data: function() {
      return {
        dialogFormVisible: false,
        formLabelWidth: '70px',
        registration:{
          username:'',
          password:'',
          name:'',
          phoneNumber:'',
          id:'',
          radio:''
        },
        param: {
          username: '',
          password: '',
          // radio: ''
        },
        rules: {
          username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
          password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
          // radio: [{ required:true, message:' ', trigger:'change'}]
        }
      };
    },
    methods: {
      submitForm() {
        this.$refs.login.validate((valid) => {
          if (valid) {
            let params = {
              loginName: this.param.username,
              password: this.param.password,
            };
            this.$axios({
              url: "http://localhost:8080/login/"+params.loginName+"?password="+params.password,
              method: "GET"
            }).then(response=>{
                if (response.data.message==="success") {
                  this.$message.success("登陆成功");
                  window.localStorage["username"]= response.data.username;
                  window.localStorage["radio"]= response.data.radio;
                  this.$router.push(window.localStorage["radio"]);
                } else {
                  this.$message.error("用户名或密码错误");
                }
              })
              .catch(error=>{
                this.$message.error("服务器开小差了");
              });
            // this.$router.push(this.param.radio);
          }
          else {
            this.$message.error('请输入用户名密码');
            // console.log('error submit!!');
            // return false;
          }
        });
      },
      closeForm(){
        this.dialogFormVisible=false;
      },
    },
  };
</script>

<style scoped>
  .login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-size: 100%;
    background: url("../assets/login.jpg") no-repeat center ;
    background-size: cover;
  }
  .ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
  }
  .ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(150, 161, 170, 0.95);
    overflow: hidden;
  }
  .ms-content {
    padding: 30px 30px;
    text-align: center;
  }
  .btn{
    text-align: center;
  }
  .btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
  }
  .el-dialog{
    width: 30%;
  }
  .el-dialog .el-form-item{
    margin-left: 5%;
    width: 60%;
  }
  .el-dialog{
    /*float: left;*/
    text-align: center;
  }
  .el-dialog .radio{
    margin-right: 30%;
  }
</style>
