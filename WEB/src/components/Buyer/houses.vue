<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>浏览房源</el-breadcrumb-item>
        </el-breadcrumb>
        <el-table
                :data="houses">
            <el-table-column
                    align="center"
                    label="位置"
                    prop="location">
            </el-table-column>
            <el-table-column
                    align="center"
                    label="大小"
                    prop="size">
            </el-table-column>
            <el-table-column
                    align="center"
                    label="价格"
                    prop="price">
            </el-table-column>
            <el-table-column
                    align="center"
                    label="是否有电梯"
                    prop="lift">
            </el-table-column>
            <el-table-column
                    align="center"
                    label="评分"
                    prop="rank">
                <template slot-scope="scope">
                    <el-rate
                            disabled
                            show-score
                            text-color="#ff9900"
                            v-model="scope.row.rate">
                    </el-rate>
                </template>
            </el-table-column>
            <el-table-column
                    align="center">
                <template slot="header" slot-scope="scope">
                    <el-button
                            @click="select()"
                            size="mini"
                            type="primary"><i class="el-icon-setting"> 筛选</i>
                    </el-button>
                </template>
                <template slot-scope="scope">
                    <el-button
                            @click="detail(scope.row.id)"
                            size="mini">详情
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog :visible.sync="dialogVisible" title="详细信息">
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
                                <p class="content">{{houseDetail.decoration}}</p>
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
                    <div id="AMap"></div>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script>
    let map;
    export default {
        name: "buyerHouses",
        data() {
            return {
                dialogVisible: false,
                houses: [{
                    id: 0,
                    lift: '是',
                    location: '山东省威海市文化西路二号',
                    price: '1000万元',
                    size: '100平方米',
                    rate: 3.8
                }],
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
                    imgs: [
                        require("../../assets/timg.jpeg"),
                        require("../../assets/timg-2.jpeg")
                    ]
                },
                filter: {
                    position: 'all',
                    mimSize: 0,
                    mimPrice: 0,
                    maxPrice: 0,
                    rate: 0,
                    lift: -1,
                    time: 'all'
                },
            }
        },
        methods: {
            select() {

            },
            detail(id) {
                this.dialogVisible = true;
                localStorage['HouseId'] = id;
                setTimeout(function () {
                    map = new AMap.Map('AMap', {
                        resizeEnable: true,
                        center: [116.481181, 39.989792],
                        zoom: 13
                    });
                    let marker = new AMap.Marker({
                        position: [116.481181, 39.989792]
                    })
                    map.add(marker);
                }, 300);
            },
            load() {

            }
        },
        mounted() {
            // load();
        }
    }
</script>

<style scoped>
    .el-table-column {
        overflow: hidden;
    }

    #AMap {
        margin-top: 0;
        width: 100%;
        height: 200px;
    }

    .el-table {
        margin-top: 10px;
    }

    .route-content {
        width: 100%;
        text-align: left;
    }

    .right {
        margin-left: 80px;
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
