<template>
    <div>
        <!--面包屑标签&按钮-->
        <el-row>
            <el-col :span="3">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>首页</el-breadcrumb-item>
                    <el-breadcrumb-item>浏览房源</el-breadcrumb-item>
                </el-breadcrumb>
            </el-col>
            <el-col :span="6">
                <div class="buttonBox">
                    <el-button
                            @click="select()"
                            size="small"
                            style="margin-top: 5px"
                            type="primary"><i class="el-icon-setting"> 筛选</i>
                    </el-button>
                    <el-button
                            @click="reset()"
                            size="small"
                            type="info"><i class="el-icon-refresh-left"> 重置</i>
                    </el-button>
                </div>
            </el-col>
        </el-row>
        <!--卡片列表区-->
        <div class="houseCards" style="padding-top: 20px">
            <el-row>
                <el-col v-for="(o, index) in houses.length" :key="o">
                    <el-card>
                        <el-row>
                            <el-col :span="7">
                                <!--走马灯-->
                                <!--houses[index].imgs!== undefined && houses[index].imgs.length>0-->
                                <div v-if="!houses[index].imgFlag">
                                    <el-carousel height="200px" direction="vertical" :autoplay="false">
                                        <el-carousel-item :key="img.fileName" v-for="img in houses[index].imgs">
                                            <h3 class="medium">
                                                <el-image :src="img.path" fit="contain"/>
                                            </h3>
                                        </el-carousel-item>
                                    </el-carousel>
                                </div>
                                <!--houses[index].imgs.length === 0-->
                                <div v-if="houses[index].imgFlag">
                                    <el-carousel height="200px" direction="vertical" :autoplay="false">
                                        <el-carousel-item v-for="item in 3" :key="item">
                                            <h3 class="medium">
<!--                                                {{item}}-->
                                                <el-image src='http://39.98.48.34:2233/houseImg/noImg2.png' fit="contain"/>
                                            </h3>
                                        </el-carousel-item>
                                    </el-carousel>
                                </div>
                            </el-col>
                            <!--基本信息-->
                            <el-col :span="10" style="padding-left: 15px; padding-top: 15px">
                                <div class="houseTextArea">
                                    <el-row>
                                        <el-col>
                                            <span style="font-size: 24px; display: flex; justify-content: left">
                                            小区名称：{{houses[index].village}}
                                            </span>
                                        </el-col>
                                        <el-col style="padding-top: 30px">
                                             <span style="font-size: 18px; display: flex; justify-content: left;">
                                                 {{houses[index].size}}m² | {{houses[index].decoration}} | {{houses[index].houseType}}
                                             </span>
                                        </el-col>
                                        <el-col style="padding-top: 15px">
                                             <span style="font-size: 18px; display: flex; justify-content: left;">
                                                 位置：{{houses[index].location}}
                                             </span>
                                        </el-col>
                                        <el-col style="padding-top: 30px">
                                            <el-row>
                                                <el-col :span="10">
                                                     <span style="font-size: 16px; display: flex; justify-content: left;">
                                                        商家：{{houses[index].username}}
                                                     </span>
                                                </el-col>
                                                <el-col :span="2">
                                                    <span>评分</span>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-rate
                                                            v-if="houses[index].count>0"
                                                            disabled
                                                            text-color="#ff9900"
                                                            v-model="houses[index].score">
                                                    </el-rate>
                                                    <p v-else>暂无评分</p>
                                                </el-col>
                                            </el-row>
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-col>
                            <!--售价-->
                            <el-col :span="5" style="padding-top: 50px">
                                <el-row>
                                    <el-col>
                                        <span style="color: #e54b00; font-size: 32px;">
                                            {{houses[index].price}}<b style="font-size: 25px; padding-left: 5px;">元</b>
                                        </span>
                                    </el-col>
                                    <el-col style="padding-top: 10px;">
                                        <span style="font-size: 16px">
                                             {{(parseFloat(houses[index].price)/parseFloat(houses[index].size)).toFixed(1)}}
                                        元/m²
                                        </span>
                                    </el-col>
                                    <!--button-->
                                    <el-col style="padding-top: 27px">
                                        <el-button
                                                type="primary"
                                                @click="detail(houses[index].houseID)"
                                                size="small"><i class="el-icon-more"></i>
                                        </el-button>
                                        <el-button
                                                v-if="houses[index].count>0"
                                                @click="comment(houses[index].houseID)"
                                                size="small"><i class="el-icon-chat-dot-square"></i>
                                        </el-button>
                                        <el-button
                                                type="success"
                                                @click="request(houses[index].sellerID,houses[index].houseID)"
                                                size="small"><i class="el-icon-s-order"></i>
                                        </el-button>
                                        <el-button
                                                type="primary"
                                                @click="chat(houses[index])"
                                                size="small"><i class="el-icon-message"></i>
                                        </el-button>
                                    </el-col>
                                </el-row>
                            </el-col>
                        </el-row>
                    </el-card>
                </el-col>
            </el-row>

        </div>
<!--        <el-table
                height="650px"
                v-loading="loading"
                :data="houses">
            <el-table-column
                    align="center"
                    label="位置"
                    prop="location">
            </el-table-column>
            <el-table-column
                    align="center"
                    label="小区名称"
                    prop="village">
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
                    label="评分"
                    prop="rank">
                <template slot-scope="scope">
                    <el-rate
                            v-if="scope.row.count>0"
                            disabled
                            text-color="#ff9900"
                            v-model="scope.row.score">
                    </el-rate>
                    <p v-else>暂无评分</p>
                </template>
            </el-table-column>
            <el-table-column
                    align="center"
                    width="260px">
                <template slot="header" slot-scope="scope">
                    <el-button
                            @click="select()"
                            size="small"
                            type="primary"><i class="el-icon-setting"> 筛选</i>
                    </el-button>
                    <el-button
                            @click="reset()"
                            size="small"
                            type="info"><i class="el-icon-refresh-left"> 重置</i>
                    </el-button>
                </template>
                <template slot-scope="scope">
                    <el-button
                            type="primary"
                            @click="detail(scope.row.houseID)"
                            size="small"><i class="el-icon-more"></i>
                    </el-button>
                    <el-button
                            v-if="scope.row.count>0"
                            @click="comment(scope.row.houseID)"
                            size="small"><i class="el-icon-chat-dot-square"></i>
                    </el-button>
                    <el-button
                            type="success"
                            @click="request(scope.row.sellerID,scope.row.houseID)"
                            size="small"><i class="el-icon-s-order"></i>
                    </el-button>
                    <el-button
                            type="primary"
                            @click="chat(scope.row)"
                            size="small"><i class="el-icon-message"></i>
                    </el-button>
                </template>
            </el-table-column>
        </el-table>-->
        <el-dialog :visible.sync="detailVisible" title="详细信息" destroy-on-close @closed="destroyMap">
            <detail :house-detail="houseDetail"></detail>
        </el-dialog>
        <el-dialog :visible.sync="requestVisible" title="预约看房" destroy-on-close>
            <request :form="requestForm" @confirm="requestConfirmed" @close="closeRequest"></request>
        </el-dialog>
        <el-dialog :visible.sync="filterVisible" title="筛选" destroy-on-close>
            <search :form="filter" @selectionEnabled="selectionConfirmed" @close="closeFilter"></search>
        </el-dialog>
        <el-dialog width="500px" :visible.sync="commentVisible" title="评价" destroy-on-close>
            <comment :comments="comments" @clear="comments=[]"></comment>
        </el-dialog>
        <el-dialog width="500px" :visible.sync="chattingVisible" :title="conversation.username" @close="clear">
            <chatting v-if="loaded" :username="conversation.username" :id="conversation.id"></chatting>
        </el-dialog>
    </div>
</template>

<script>
    import Detail from "./detail";
    import Request from "./request";
    import search from "./search";
    import Comment from "./comment";
    import Chatting from "../shared/chatting";

    let map;
    export default {
        name: "buyerHouses",
        components: {Chatting, Comment, search, Request, Detail},
        data() {
            return {
                loaded: false,
                commentVisible: false,
                filterVisible: false,
                requestVisible: false,
                detailVisible: false,
                chattingVisible: false,
                loading: true,
                houses: [],
                comments: [],
                conversation: {
                    username: '',
                    id: ''
                },
                filter: {
                    province: '',
                    city: '',
                    district: '',
                    street: '',
                    community: '',
                    mimSize: 10,
                    maxSize: 1000,
                    mimPrice: 50000,
                    maxPrice: 100000000,
                    age: 50,
                    rate: 0.0
                },
                filterBackup: {
                    province: '',
                    city: '',
                    district: '',
                    street: '',
                    community: '',
                    mimSize: 10,
                    maxSize: 1000,
                    mimPrice: 50000,
                    maxPrice: 100000000,
                    age: 50,
                    rate: 0.0
                },
                requestForm: {
                    senderID: '',
                    sellerID: '',
                    houseId: '',
                    phoneNumber: '',
                    date: '',
                    time: ''
                },
                houseDetail: {},
                houseIndex: [],
                displayHouseImg: [],
            }
        },
        methods: {
            comment(id) {
                this.$axios({
                    method: 'GET',
                    url: 'http://localhost:8080/SRservice/houseReceiveComments/' + id,
                }).then(res => {
                    this.comments = res.data;
                    this.commentVisible = true;
                    console.log(res.data)
                }).catch(e => {
                    console.log(e);
                })
            },
            reset() {
                this.load()
                Object.assign(this.$data.request, this.$options.data().request);
            },
            select() {
                this.filterVisible = true;
                this.filterBackup = JSON.parse(JSON.stringify(this.filter));
            },
            detail(id) {
                //房源详情
                this.$axios({
                    url: 'http://localhost:8080/house/' + id,
                    method: 'GET'
                }).then(res => {
                    this.detailVisible = true;
                    this.houseDetail = res.data;
                    this.$axios({
                        url: 'http://localhost:8080/imgManage/selectImg/' + id,
                        method: 'GET',
                    }).then(res => {
                        console.log(res)
                        this.$set(this.houseDetail, 'imgs', res.data);
                    }).catch(e => {
                        console.log(e);
                    })
                    setTimeout(function () {
                        map = new AMap.Map('AMap', {
                            resizeEnable: true,
                            center: [res.data.locationX, res.data.locationY],
                            zoom: 13
                        });
                        let marker = new AMap.Marker({
                            position: [res.data.locationX, res.data.locationY]
                        })
                        map.add(marker);
                    }, 30);
                }).catch(e => {
                    console.log(e);
                })
            },
            request(sellerID, houseID) {
                this.requestVisible = true;
                this.requestForm.senderID = window.localStorage['username'];
                this.requestForm.sellerID = sellerID;
                this.requestForm.houseId = houseID;
            },
            closeRequest() {
                this.requestVisible = false;

            },
            load() {
                //加载房源列表
                this.$axios({
                    url: 'http://localhost:8080/houseList',
                    method: 'GET'
                }).then(res => {
                    this.loading = false;
                    this.houses = res.data;
                    for (var i in this.houses) {
                        this.houseIndex[i] = this.houses[i].houseID
                    }
                    // console.log(this.houseIndex)
                    var i = 0         /*this.houseIndex.length*/
                    /*递归调用*/
                    this.loadImg(i)
                    // for循环异步请求问题
                    // this.loadImg(0, this.houses.length)
                }).catch(e => {
                    console.log(e);
                })
            },
            loadImg(i) {
                this.$axios({
                    url: 'http://localhost:8080/imgManage/selectImg/' + this.houseIndex[i],
                    method: 'GET',
                }).then(res => {
                    if (i == this.houseIndex.length) {
                        console.log(this.houses)
                        return;
                    }
                    /*递归调用*/
                    // console.log(res.data)
                    this.$set(this.houses[i], 'imgFlag', true)
                    if(res.data.length === 0){
                        let index = {fileName: 'noImg.jpg', path: 'http://39.98.48.34:2233/houseImg/noImg.jpg'}
                        this.$set(this.houses[i], 'imgs', index)
                    }
                    else{
                        this.$set(this.houses[i], 'imgFlag', false)
                        this.$set(this.houses[i], 'imgs', res.data);
                    }
                    this.loadImg(++i)
                    /*                    if (i == 10) {
                                            console.log(this.houses)
                                        }*/
                }).catch(e => {
                    console.log(e);
                })
                /*setTimeout(function () {

                    console.log(this.houses===undefined)
                    while (this.houses===undefined);
                    if (this.houses!==undefined){
                        this.$axios({
                            url: 'http://localhost:8080/imgManage/selectImg/' + this.houses[i].houseID,
                            method: 'GET',
                        }).then(res => {
                            this.$set(this.houses[i], 'imgs', res.data);
                            if (i == 10) {
                                console.log(this.houses)
                            }
                        }).catch(e => {
                            console.log(e);
                        })
                        if (++i < size) {
                            loadImg(i, size)
                        }
                    }

                })*/
            },
            requestConfirmed(id) {
                this.requestVisible = false;
                let request = this.requestForm;
                this.$axios({
                    url: 'http://localhost:8080/SRservice/sendRequest/' +
                        request.senderID + '/' + request.sellerID + '/' + request.houseId +
                        '/' + request.phoneNumber,
                    method: 'GET',
                    params: {
                        date: request.date,
                        time: request.time
                    }
                }).then(res => {
                    this.$notify({
                        title: '申请成功',
                        message: '等待卖家做出回应',
                        type: 'success'
                    });
                }).catch(e => {
                    this.$notify({
                        title: '申请失败',
                        message: '请稍后再试',
                        type: 'error'
                    });
                });
                Object.assign(this.$data.requestForm, this.$options.data().requestForm);
            },
            selectionConfirmed() {
                let filter = this.filter;
                this.filterVisible = false;
                this.$axios({
                    url: 'http://localhost:8080/select',
                    method: 'GET',
                    params: {
                        village: filter.community,
                        position: filter.province + filter.city + filter.district + filter.street,
                        mimSize: filter.mimSize,
                        maxSize: filter.maxSize,
                        mimPrice: filter.mimPrice,
                        maxPrice: filter.maxPrice,
                        score: filter.rate,
                        time: filter.age
                    }
                }).then(res => {
                    this.houses = res.data;
                    for (var i in this.houses) {
                        this.houseIndex[i] = this.houses[i].houseID
                    }
                    var i = 0;
                    this.loadImg(i)
                }).catch(e => {
                    this.$message({
                        message: '筛选失败',
                        type: 'error'
                    });
                })
            },
            closeFilter() {
                this.filterVisible = false;
                Object.assign(this.$data.filter, this.$options.data().filter);
            },
            destroyMap() {
                map.destroy();
            },
            chat(row) {
                new Promise((resolve, reject) => {
                    while (this.conversation.username !== row.sellerID || this.conversation.id !== row.sellerID) {
                        this.conversation.username = row.sellerID;
                        this.conversation.id = row.sellerID;
                    }
                    resolve('success');
                }).then(res => {
                    this.chattingVisible = true;
                    this.loaded = true;
                })
            },
            clear() {
                this.conversation.username = '';
                this.conversation.id = '';
                this.loaded = false;
            }
        },
        mounted() {
            this.load();
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

    p {
        color: rgba(175, 175, 175, 0.83);
    }

    .buttonBox {
        position: absolute;
        top: -110%;
    }

    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 200px;
        margin: 0;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }
</style>
