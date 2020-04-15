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
        <el-dialog title="注册信息" :visible.sync="dialogFormVisible">
          <el-form :model="registration">
            <el-form-item label="用户名" :label-width="formLabelWidth">
              <el-tooltip class="item" effect="dark" content="3到16位字母,数字,汉字" placement="top">
              <el-input v-model="registration.username" placeholder="请输入用户名"></el-input>
              </el-tooltip>
            </el-form-item>
            <el-form-item label="密码" :label-width="formLabelWidth">
              <el-tooltip class="item" effect="dark" content="8-16个字符，至少1个大写字母，1个小写字母和1个数字" placement="top">
                <el-input v-model="registration.password" placeholder="请输入密码" show-password></el-input>
              </el-tooltip>
            </el-form-item>
            <el-form-item label="真实姓名" :label-width="formLabelWidth">
              <el-input v-model="registration.name" placeholder="请输入真实姓名"></el-input>
            </el-form-item>
            <el-form-item label="手机号" :label-width="formLabelWidth">
              <el-input v-model="registration.phoneNumber" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <el-form-item label="身份证号" :label-width="formLabelWidth">
              <el-input v-model="registration.id" placeholder="请输入身份证号"></el-input>
            </el-form-item>
            <el-form-item label="我是:" :label-width="formLabelWidth">
              <div class="radio">
                <el-radio v-model="registration.radio" label="seller">卖家</el-radio>
                <el-radio v-model="registration.radio" label="buyer">买家</el-radio>
              </div>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="closeForm">取 消</el-button>
            <el-button type="primary" @click="register">确 定</el-button>
          </div>
        </el-dialog>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
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
      register(){
        let regs=this.registration;
        let regUsrName=/^[\u4e00-\u9fff\w]{3,16}$/;
        let regPassword=/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/;
        let regName=/^[\u4E00-\u9FA5]{2,4}$/;
        let regPhoneNUmber=/^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/;
        let regId=/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (!(regs.id&&regs.name&&regs.password&&regs.phoneNumber&&regs.username&&regs.radio)){
          this.$message.error('请将注册信息填写完整');
        }
        else if(!regUsrName.test(regs.username)){
          this.$message.error('请按正确的格式输入用户名');
          regs.username='';
        }
        else if (!regPassword.test(regs.password)){
          this.$message.error('请按正确的格式输入密码');
          regs.password='';
        }
        else if(!regName.test(regs.name)){
          this.$message.error('请按正确的格式输入姓名');
          regs.name='';
        }
        else if(!regPhoneNUmber.test(regs.phoneNumber)){
          this.$message.error('请按正确的格式输入手机号');
          regs.phoneNumber='';
        }
        else if(!regId.test(regs.id)){
          this.$message.error('请按正确的格式输入身份证号');
          regs.id='';
        }
        else {
          this.$axios({
            url: "http://localhost:8080/verify",
            method: 'GET',
            params: {
              username: regs.username,
              password: regs.password,
              realName: regs.name,
              id: regs.id,
              phone: regs.phoneNumber,
              type: regs.radio
            }
          }).then(res => {
            if (res.data.message === "available") {
              this.$axios({
                url: 'https://zidv2.market.alicloudapi.com/idcard/VerifyIdcardv2',
                headers: {
                  Authorization: 'APPCODE c564c14eb27642c4858d05f0290d2d06'
                },
                method: 'GET',
                params: {
                  cardNo: regs.id,
                  realName: regs.name
                }
              }).then(response => {
                let res = response.data;
                if (res['error_code'] === 0) {
                  console.log(res);
                  this.addUser();
                  this.closeForm();
                  this.$message.success("注册成功");
                } else if (res['error_code'] === 206501) {
                  this.$message.error("姓名或身份证号错误");
                } else {
                  console.log(res);
                }
              }).catch(e => {
                this.$message.error("服务器开小差了");
              })
            } else if (res.data.message === "phoneNumber engaged") {
              this.$message.error("电话号码已被占用");
            } else {
              this.$message.error("身份证号已被占用");
            }
          }).catch(e => {
            this.$message.error("服务器开小差了");
          });
        }
      },
      closeForm(){
        this.dialogFormVisible=false;
        let regs=this.registration;
        regs.username='';
        regs.password='';
        regs.name='';
        regs.phoneNumber='';
        regs.id='';
        regs.radio='';
      },
      addUser(){
        this.$axios({
          url:'http://localhost:8080/register/',
          method: 'GET',
          params:{
            username: this.registration.username,
            password: this.registration.password,
            realName: this.registration.name,
            id: this.registration.id,
            phone: this.registration.phoneNumber,
            type: this.registration.radio
          }
        }).then(res=>{
          console.log(res.data);
        }).catch()
      }
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
