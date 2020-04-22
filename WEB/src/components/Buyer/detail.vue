<template>
    <div class="route-content">
        <div class="head">
            <div class="block">
                <el-carousel height="200px">
                    <el-carousel-item :key="img" v-for="img in houseDetail.imgs">
                        <h3 class="small">
                            <el-image :src="img" fit="contain">
                                <div class="image-slot" slot="error">
                                    <i class="el-icon-picture-outline"></i>
                                </div>
                            </el-image>
                        </h3>
                    </el-carousel-item>
                </el-carousel>
            </div>
        </div>
        <div class="infoBlock">
            <p class="title">基本属性</p>
            <div class="info">
                <div class="infoLine">
                    <p class="content label">房屋户型:</p>
                    <p class="content">{{houseDetail.roomType}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">建筑面积:</p>
                    <p class="content">{{houseDetail.outerSize}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">套内面积:</p>
                    <p class="content">{{houseDetail.innerSize}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">房屋朝向:</p>
                    <p class="content">{{houseDetail.direction}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">装修情况:</p>
                    <p class="content">{{houseDetail.decoration}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">供暖方式:</p>
                    <p class="content">{{houseDetail.warmth}}</p>
                </div>
            </div>
            <div class="info">
                <div class="infoLine">
                    <p class="content label">所在楼层:</p>
                    <p class="content">{{houseDetail.floor}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">户型结构:</p>
                    <p class="content">{{houseDetail.roomStructure}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">建筑类型:</p>
                    <p class="content">{{houseDetail.architectureType}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">建筑结构:</p>
                    <p class="content">{{houseDetail.architectureStructure}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">梯户比例:</p>
                    <p class="content">{{houseDetail.neighborType}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">配备电梯:</p>
                    <p class="content">{{houseDetail.lift}}</p>
                </div>
            </div>
        </div>
        <div class="infoBlock">
            <p class="title">交易属性</p>
            <div class="info">
                <div class="infoLine">
                    <p class="content label">挂牌时间:</p>
                    <p class="content">{{houseDetail.postTime}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">上次交易:</p>
                    <p class="content">{{houseDetail.laseTradeTime}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">房屋年限:</p>
                    <p class="content">{{houseDetail.houseAge}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">抵押信息:</p>
                    <p class="content">{{houseDetail.mortgage}}</p>
                </div>
            </div>
            <div class="info">
                <div class="infoLine">
                    <p class="content label">交易权属:</p>
                    <p class="content">{{houseDetail.tradeProperty}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">房屋用途:</p>
                    <p class="content">{{houseDetail.houseUsage}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">产权所属:</p>
                    <p class="content">{{houseDetail.ownership}}</p>
                </div>
                <div class="infoLine">
                    <p class="content label">房本备件:</p>
                    <p class="content">{{houseDetail.credentials}}</p>
                </div>
            </div>
        </div>
        <div id="AMap"></div>
    </div>
</template>

<script>
    export default {
        name: "detail",
        data(){
            return{
                houseDetail: {
                    roomType: '3室1厅1厨1卫',
                    outerSize: '72.83㎡',
                    innerSize: '62.48㎡',
                    direction: '南 北',
                    decoration: '精装',
                    warmth: '集中供暖',
                    floor: '中楼层 (共4层)',
                    roomStructure: '平层',
                    architectureType: '板楼',
                    architectureStructure: '混合结构',
                    neighborType: '一梯两户',
                    lift: '无',
                    postTime:'2018-11-05',
                    laseTradeTime:'2013-12-03',
                    tradeProperty:'商品房',
                    houseUsage:'普通住宅',
                    houseAge:'房屋年限',
                    ownership:'非共有',
                    mortgage:'无抵押',
                    credentials:'已上传房本照片',
                    imgs: [
                        require("../../assets/timg.jpeg"),
                        require("../../assets/timg-2.jpeg")
                    ]
                }
            }
        }
    }
</script>

<style scoped>

    #AMap {
        margin-top: 0;
        width: 100%;
        height: 200px;
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
        vertical-align: middle;
        display: block;
        width: 100%;
    }

    .info {
        margin-left: 10%;
        width: 30%;
        display: inline-block;
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

    .infoLine .label {
        width: 56px;
        white-space: nowrap;
        margin-right: 14px;
    }

    .infoLine .content {
        font-size: 13px;
        vertical-align: middle;
        display: inline-block;
        margin-left: 7px;
        margin-bottom: 15px;
        font-weight: 400;
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
</style>