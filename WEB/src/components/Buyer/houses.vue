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
                            size="small"
                            type="primary"><i class="el-icon-setting"> 筛选</i>
                    </el-button>
                </template>
                <template slot-scope="scope">
                    <el-button
                            @click="detail(scope.row.id)"
                            size="small">详情
                    </el-button>
                    <el-button
                            type="success"
                            @click="request(scope.row.id)"
                            size="small">看房
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog :visible.sync="dialogVisible" title="详细信息" @closed="destroyMap">
            <detail></detail>
        </el-dialog>
        <el-dialog :visible.sync="requestVisible" title="预约看房" destroy-on-close>
            <request @close="closeRequest"></request>
        </el-dialog>
    </div>
</template>

<script>
    import Detail from "./detail";
    import Request from "./request";
    let map;
    export default {
        name: "buyerHouses",
        components: {Request, Detail},
        data() {
            return {
                requestVisible: false,
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
                }, 30);
            },
            request(id){
                this.requestVisible=true;
            },
            closeRequest(){
                this.requestVisible=false;
            },
            load() {

            },
            destroyMap(){
                map.destroy( );
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
