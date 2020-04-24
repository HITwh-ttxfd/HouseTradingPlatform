<template>
    <div>
        <el-form :model="form">
            <el-form-item label="联系电话" :label-width="'100px'">
                <el-input v-model="form.phoneNumber" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="预约时间" :label-width="'100px'">
                <el-col :span="11">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 100%;"></el-date-picker>
                </el-col>
                <el-col class="line" :span="2">-</el-col>
                <el-col :span="11">
                    <el-time-picker placeholder="选择时间" v-model="form.time" style="width: 100%;"></el-time-picker>
                </el-col>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="$emit('close')">取 消</el-button>
            <el-button type="primary" @click="$emit('confirm')">确 定</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "request",
        props: {
            form:Object
        },
        data(){
            return{

            }
        },
        methods:{

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