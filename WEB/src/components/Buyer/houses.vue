<template>
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
                        v-model="scope.row.rate"
                        disabled
                        show-score
                        text-color="#ff9900">
                </el-rate>
            </template>
        </el-table-column>
        <el-table-column
                align="center">
            <template slot="header" slot-scope="scope">
                <el-button
                        @click="filter()"
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
</template>

<script>
    export default {
        name: "buyerHouses",
        data() {
            return {
                houses: [{
                    id:0,
                    lift:'是',
                    location:'山东省威海市文化西路二号',
                    price:'1000万元',
                    size:'100平方米',
                    rate:3.8
                }],
                filter:{
                    position:'all',
                    mimSize:0,
                    mimPrice:0,
                    maxPrice:0,
                    rate:0,
                    lift:-1,
                    time:'all'
                }
            }
        },
        methods:{
            filter(){

            },
            detail(id){

            },
            load(){

            }
        },
        mounted() {
            load();
        }
    }
</script>

<style scoped>
    .el-table-column{
        overflow: hidden;
    }
</style>
