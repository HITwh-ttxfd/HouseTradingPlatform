<template>
    <div class="route-content">
        <div class="head">
            <div class="block">
                <el-upload
                        action="https://jsonplaceholder.typicode.com/posts/"
                        list-type="picture-card"
                        accept="image/png,image/jpg,image/jpeg"
                        :multiple="true"
                        :limit="10"
                        name="上传房源图片"
                        :on-exceed="handleExceed"
                        :on-change="handleChange"
                        :on-preview="handlePreview"
                        :auto-upload="false"
                        :file-list="fileList">
                    <i class="el-icon-plus"></i>
                </el-upload>
                <el-dialog :visible.sync="dialogVisible" append-to-body>
                    <img width="100%" :src="dialogImageUrl" alt="">
                </el-dialog>
            </div>
        </div>
        <div class="infoBlock">
            <p class="title">基本属性</p>
            <div class="info">
                <div class="infoLine">
                    <p class="content label">房屋户型:</p>
                    <el-input v-model="newHouse.houseType"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">建筑面积:</p>
                    <el-input v-model="newHouse.buildingArea"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">套内面积:</p>
                    <el-input v-model="newHouse.interiorArea"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">房屋朝向:</p>
                    <el-input v-model="newHouse.houseOrientation"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">装修情况:</p>
                    <el-input v-model="newHouse.decoration"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">供暖方式:</p>
                    <el-input v-model="newHouse.heatingMode"></el-input>
                </div>
            </div>
            <div class="info">
                <div class="infoLine">
                    <p class="content label">所在楼层:</p>
                    <el-input v-model="newHouse.floor"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">户型结构:</p>
                    <el-input v-model="newHouse.houseTypeStructure"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">建筑类型:</p>
                    <el-input v-model="newHouse.buildingType"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">梯户比例:</p>
                    <el-input v-model="newHouse.elevatorProportion"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">配备电梯:</p>
                    <el-input v-model="newHouse.lift"></el-input>
                </div>
            </div>
        </div>
        <div class="infoBlock">
            <p class="title">交易属性</p>
            <div class="info">
                <div class="infoLine">
                    <p class="content label">挂牌时间:</p>
                    <el-input v-model="newHouse.listingTime"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">上次交易:</p>
                    <el-input v-model="newHouse.lastTransaction"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">建造时间:</p>
                    <el-input v-model="newHouse.time"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">抵押信息:</p>
                    <el-input v-model="newHouse.mortgageInformation"></el-input>
                </div>
            </div>
            <div class="info">
                <div class="infoLine">
                    <p class="content label">交易权属:</p>
                    <el-input v-model="newHouse.transactionOwnership"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">房屋用途:</p>
                    <el-input v-model="newHouse.housingPurpose"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">产权所属:</p>
                    <el-input v-model="newHouse.propertyOwnership"></el-input>
                </div>
                <div class="infoLine">
                    <p class="content label">房本备件:</p>
                    <el-input v-model="newHouse.housingParts"></el-input>
                </div>
            </div>
        </div>
        <div class="infoBlock">
            <p class="title">位置属性</p>
            <div class="infoLine" style="width: 400px;margin-left: 5%;">
                <p class="content label">行政区划:</p>
                    <el-select @change="searchLocation(1)"  v-model="province" placeholder="省份">
                        <el-option v-for="(province) in provinces" :key="province.name" :label="province.name" :value="province.name"></el-option>
                    </el-select>
                    <el-select @change="searchLocation(2)"  v-model="city" placeholder="城市">
                        <el-option v-for="(city) in cities" :key="city.name" :label="city.name" :value="city.name"></el-option>
                    </el-select>
                    <el-select  v-model="district" placeholder="区县">
                        <el-option v-for="(district) in districts" :key="districts.name" :label="district.name" :value="district.name"></el-option>
                    </el-select>
            </div>
            <div class="infoLine" style="width: 410px;margin-left: 5%;">
                <p class="content label">街道地址:</p>
                <el-input placeholder="例：文化西路二号" style="width: 300px" v-model="street"></el-input>
            </div>
            <div class="infoLine" style="width: 410px;margin-left: 5%;">
                <p class="content label">小区全称:</p>
                <el-input placeholder="例：XX小区" style="width: 300px" v-model="newHouse.village"></el-input>
            </div>
            <div class="infoLine" style="width: 410px;margin-left: 5%;">
                <p class="content label">详细地址:</p>
                <p class="content">{{location}}</p>
            </div>
        </div>
        <div class="infoBlock">
            <p class="title">价格属性</p>
            <div class="infoLine" style="width: 410px;margin-left: 5%;">
                <p class="content label">房屋价格:</p>
                <el-input placeholder="请输入5-10位阿拉伯数字（单位：元）" style="width: 300px" v-model="newHouse.price"></el-input>
            </div>
        </div>
        <div class="dialog-footer" slot="footer">
            <el-button @click="$emit('close')">取 消</el-button>
            <el-button @click="upload" type="primary">确 定</el-button>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    //   import ajax from "ajax";
    import { Message } from 'element-ui'
    export default {
        name: "newHouse",
        data(){
            return{
                provinces:[],
                cities:[],
                districts:[],
                fileList: [],
                str: '',
                dialogImageUrl: '',
                dialogVisible: false,
                province:'',
                city:'',
                district:'',
                street:'',
                newHouse: {
                    location: '',
                    locationX:0,
                    locationY:0,
                    village:'',
                    buildingArea:80,
                    buildingType:"板楼",
                    count:0,
                    decoration:"精装",
                    elevatorProportion:"1梯2户",
                    floor:"中楼层（4层）",
                    heatingMode:"集中供暖",
                    houseID:"1",
                    houseOrientation:"南北",
                    houseType:"3室1厅1卫",
                    houseTypeStructure:"平层",
                    housingAge:"19",
                    housingParts:"已上传房本配件",
                    housingPurpose:"普通住宅",
                    interiorArea:75,
                    lastTransaction:"2020-02-05",
                    lift:1,
                    listingTime:"2005-07-14",
                    mortgageInformation:"无抵押",
                    price:350000,
                    propertyOwnership:"非公有",
                    score:0,
                    sellerID:"15534280066",
                    size:80,
                    time:"2001-02-07",
                    transactionOwnership:"商品房",
                }
            }
        },
        methods:{
            upload(){
                this.newHouse.location=this.province+this.city+this.district+this.street;
                // 这里是上传图片
                let size = this.fileList.length
                let count = 0
                let fName = new Array(size)
                while (count < size) {
                    console.log(count + '|' + size)
                    let file = this.fileList[count]
                    this.getBase64(file.raw).then(res => {
                        // 第一层then 这里在前面的变量已经不能用了
                        axios.get('http://localhost:8080/getHouseID').then(function (response) {
                            // 第二层 更不能用了
                            console.log(file.raw)
                            let hid = response.data.toString();
                            let data = {'file': res, 'fileName': file.name, 'houseID': hid}
                            // console.log(data)
                            axios.post('http://localhost:8080/imgManage/uploadImg64', data, { headers: { 'Content-Type': 'application/json' } }).then(function (res) {
                                //console.log(file.name, res.data)
                                if (res.data === 'fail') {
                                    Message.error('上传失败')
                                    this.$emit('close');
                                } else if (res.data === 'Error') {
                                    Message.error('上传失败')
                                    this.$emit('close');
                                }
                            }).catch(err => {
                                console.log(err)
                            })
                        })
                    })
                    count = count + 1
                }
                // 上传图片结束
                this.$axios({
                    method: 'POST',
                    url: 'http://localhost:8080/addhouse',
                    data:{
                        house: this.newHouse
                    }
                }).then(res=>{
                    Message.success('上传成功');
                    this.$emit('done');
                }).catch(e=>{
                    Message.error('上传失败');
                })
            },
            searchLocation(level){
                if (level<0||level>2)
                    return
                let location=['中国',this.province,this.city,this.district];
                this.$axios({
                    url: 'https://restapi.amap.com/v3/config/district',
                    method: 'GET',
                    params:{
                        key: '28c1a842e9aa6cd993a8890c34f20253',
                        keywords: location[level],
                    }
                }).then(res=>{
                    let regions=res.data.districts[0].districts;
                    switch (level) {
                        case 0:
                            this.provinces=eval(regions);
                            this.cities=[];
                            this.districts=[];
                            this.streets=[];
                            break;
                        case 1:
                            this.cities=eval(regions);
                            this.districts=[];
                            this.streets=[];
                            this.city='';
                            this.district='';
                            break;
                        case 2:
                            this.districts=eval(regions);
                            this.streets=[];
                            this.district='';
                            break;
                    }
                }).catch(e=>{
                    console.log(e);
                })
            },
            handleChange (file, fileList) {
                // console.log(fileList.indexOf(file))
                const isImage = (file.raw.type).indexOf('image') !== -1
                const isLi4M = file.size / 1024 / 1024 < 4
                if (!isImage) {
                    Message.error('上传的文件不是图片类型')
                    fileList.splice(fileList.indexOf(file),1)
                    return;
                }
                if (!isLi4M) {
                    Message.error('上传图片大小超过4MB')
                    fileList.splice(fileList.indexOf(file),1)
                    return
                }
                // 图片检查
                if (file && file.raw.type.match('image.*')) {
                    this.getBase64(file.raw).then(res => {
                        // console.log('base', res)
                        // 传输文件使用base64编码传输，getBase64是把文件转换为编码的函数
                        var data = {'file': res, 'fileName': file.name}
                        console.log(data)
                        // http://localhost:8080/imgManage/checkImg
                        // http://192.168.11.1:10080/imgManage/checkImg
                        axios.post('http://localhost:8080/imgManage/checkImg', data, { headers: { 'Content-Type': 'application/json' } }).then(function (res) {
                            console.log(file.name, res.data)
                            if (res.data === 'fail') {
                                Message.error('上传的不是房屋图片')
                                fileList.splice(fileList.indexOf(file),1)
                                return
                            }
                        }).catch(err => {
                            console.log(err)
                        })
                    })
                } else {
                    Message.error('上传图片格式有误')
                    fileList.splice(fileList.indexOf(file),1)
                    return
                }
                this.fileList=fileList
                // console.log('add')
            },
            handlePreview (file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
                // this.$message.info(file.name)
            },
            handleExceed (files, fileList) {
                Message.warning(`限制选择 10 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
            },
            getBase64 (file) {
                // 把文件转换为base64编码的函数，FileReader类似乎不能直接调用 需要使用Promise封装
                return new Promise(function (resolve, reject) {
                    let reader = new FileReader()
                    let imgResult = ''
                    reader.readAsDataURL(file)
                    reader.onload = function () {
                        imgResult = reader.result
                    }
                    reader.onerror = function (error) {
                        reject(error)
                    }
                    reader.onloadend = function () {
                        resolve(imgResult)
                    }
                    return reader.result
                })
            }

        },
        computed:{
            location: function(){
                return this.province+this.city+this.district+this.street+this.newHouse.village;
            },
        },
        watch:{
            location: function () {
                this.newHouse.location=this.province+this.city+this.district+this.street;
            }
        },
        mounted() {
            this.searchLocation(0);
            this.newHouse.sellerID=localStorage.username;
        }
    }
</script>

<style scoped>
    .route-content {
        width: 100%;
        text-align: left;
        max-height: 500px;
        overflow: scroll;
    }
    #AMap {
        margin-top: 0;
        width: 100%;
        height: 200px;
    }

    .block{
        margin-bottom: 10px;
    }

    .route-content {
        width: 100%;
        text-align: left;
    }

    .head {
        /*min-height: 60px;*/
        border-bottom: 1px solid #e1e8ec;
        vertical-align: middle;
        text-align: center;
        display: block;
        height: 180px;
    }

    .head .block{
        overflow-x: scroll;
        overflow-y: hidden;
        white-space: nowrap;
    }

    .el-carousel__item{
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .carousel-image {
        max-width: 100%;
        max-height: 100%;
    }
    .infoBlock {
        margin-top: 15px;
        vertical-align: middle;
        display: block;
        border-bottom: 1px solid #e1e8ec;
        margin-bottom: 15px;
    }

    .infoBlock .infoLine {
        vertical-align: center;
        display: block;
        width: 200px;
        height: 60px;
    }

    .info {
        margin-left: 5%;
        margin-right: 10%;
        width: 30%;
        display: inline-block;
        vertical-align: top;
    }

    .infoBlock .title {
        color: #0aa6bc;
        font-size: 13px;
        vertical-align: middle;
        display: block;
        margin-left: 7px;
        margin-bottom: 15px;
        font-weight: 500;
    }

    .infoLine .content {
        font-size: 13px;
        vertical-align: center;
        display: inline-block;
        margin-left: 7px;
        margin-bottom: 15px;
        font-weight: 400;
    }


    .infoLine .label {
        width: 56px;
        white-space: nowrap;
        margin-right: 14px;
        margin-bottom: 0;
        vertical-align: center;
    }

    .infoBlock p {
        color: #667d94;
        display: block;
    }

    .infoBlock a {
        color: #0aa6bc;
        cursor: pointer;
    }

    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 150px;
        margin: 0;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }

    pre{
        border: 1px solid #e1e8ec;
    }

    .el-input{
        width: 100px;
        vertical-align: center;
    }

    .wrapper{
        width: 350px;
        display: inline-block;
    }

    .el-select{
        display: inline-block;
        width: 100px;
    }

    .dialog-footer{
        float: right;
    }

</style>