<template>
    <div>
        <el-form :model="registration">
            <el-form-item :label-width="formLabelWidth" label="用户名">
                <el-tooltip class="item" content="3到16位字母,数字,汉字" effect="dark" placement="top">
                    <el-input placeholder="请输入用户名" v-model="registration.username"></el-input>
                </el-tooltip>
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="密码">
                <el-tooltip class="item" content="8-16个字符，至少1个大写字母，1个小写字母和1个数字" effect="dark" placement="top">
                    <el-input placeholder="请输入密码" show-password v-model="registration.password"></el-input>
                </el-tooltip>
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="真实姓名">
                <el-input placeholder="请输入真实姓名" v-model="registration.name"></el-input>
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="手机号">
                <el-input placeholder="请输入手机号" v-model="registration.phoneNumber"></el-input>
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="身份证号">
                <el-input placeholder="请输入身份证号" v-model="registration.id"></el-input>
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="我是:">
                <div class="radio">
                    <el-radio label="seller" v-model="registration.radio">卖家</el-radio>
                    <el-radio label="buyer" v-model="registration.radio">买家</el-radio>
                </div>
            </el-form-item>
        </el-form>
        <div class="dialog-footer" slot="footer">
            <el-button @click="$emit('close')">取 消</el-button>
            <el-button @click="register" type="primary">确 定</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "register",
        data() {
            return {
                formLabelWidth: '70px',
                registration: {
                    username: '',
                    password: '',
                    name: '',
                    phoneNumber: '',
                    id: '',
                    radio: ''
                },
            }
        },
        methods: {
            register() {
                let regs = this.registration;
                let regUsrName = /^[\u4e00-\u9fff\w]{3,16}$/;
                let regPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/;
                let regName = /^[\u4E00-\u9FA5]{2,4}$/;
                let regPhoneNUmber = /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/;
                let regId = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                if (!(regs.id && regs.name && regs.password && regs.phoneNumber && regs.username && regs.radio)) {
                    this.$message.error('请将注册信息填写完整');
                } else if (!regUsrName.test(regs.username)) {
                    this.$message.error('请按正确的格式输入用户名');
                    regs.username = '';
                } else if (!regPassword.test(regs.password)) {
                    this.$message.error('请按正确的格式输入密码');
                    regs.password = '';
                } else if (!regName.test(regs.name)) {
                    this.$message.error('请按正确的格式输入姓名');
                    regs.name = '';
                } else if (!regPhoneNUmber.test(regs.phoneNumber)) {
                    this.$message.error('请按正确的格式输入手机号');
                    regs.phoneNumber = '';
                } else if (!regId.test(regs.id)) {
                    this.$message.error('请按正确的格式输入身份证号');
                    regs.id = '';
                } else {
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
                                    this.$emit('close');
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
            addUser() {
                this.$axios({
                    url: 'http://localhost:8080/register/',
                    method: 'GET',
                    params: {
                        username: this.registration.username,
                        password: this.registration.password,
                        realName: this.registration.name,
                        id: this.registration.id,
                        phone: this.registration.phoneNumber,
                        type: this.registration.radio
                    }
                }).then(res => {
                    console.log(res.data);
                }).catch()
            }
        }
    }
</script>

<style scoped>
    .el-form-item {
        margin-left: 5%;
        width: 60%;
    }

    .el-dialog {
        /*float: left;*/
        text-align: center;
    }

    .el-dialog .radio {
        margin-right: 30%;
    }

    .dialog-footer{
        margin-top: 30px;
        margin-left: 70%;
    }
</style>