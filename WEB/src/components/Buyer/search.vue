<template>
    <div>
        <el-form :model="form">
            <el-form-item id="location"  :label-width="'80px'" label="行政区划">
                <div class="wrapper">
                    <el-select @change="searchLocation(1)"  v-model="form.province" placeholder="省份">
                        <el-option v-for="(province) in provinces" :key="province.name" :label="province.name" :value="province.name"></el-option>
                    </el-select>
                    <el-select @change="searchLocation(2)"  v-model="form.city" placeholder="城市">
                        <el-option v-for="(city) in cities" :key="city.name" :label="city.name" :value="city.name"></el-option>
                    </el-select>
                    <el-select @change="searchLocation(3)"  v-model="form.district" placeholder="区县">
                        <el-option v-for="(district) in districts" :key="districts.name" :label="district.name" :value="district.name"></el-option>
                    </el-select>
                    <el-select  v-model="form.street" placeholder="街道">
                        <el-option v-for="(street) in streets" :key="street.name" :label="street.name" :value="street.name"></el-option>
                    </el-select>
                </div>
            </el-form-item>
            <el-form-item   :label-width="'80px'" label="小区名称">
                <div class="wrapper">
                    <el-input v-model="form.community"></el-input>
                </div>
            </el-form-item>
            <el-form-item   :label-width="'80px'" label="房屋面积">
                <div class="wrapper">
                    <el-input-number controls-position="right" v-model="form.mimSize" :min="10" :max="form.maxSize"></el-input-number>
                    ～
                    <el-input-number controls-position="right" v-model="form.maxSize" :min="form.mimSize"  :max="1000"></el-input-number>
                    <span>㎡</span>
                </div>
            </el-form-item>
            <el-form-item   :label-width="'80px'" label="房屋总价">
                <div class="wrapper">
                    <el-input-number controls-position="right" v-model="form.mimPrice" :min="50000" :max="form.maxPrice"></el-input-number>
                    ～
                    <el-input-number controls-position="right" v-model="form.maxPrice" :min="form.mimPrice"  :max="100000000"></el-input-number>
                    <span>元</span>
                </div>
            </el-form-item>
            <el-form-item   :label-width="'80px'" label="建成时间">
                <div class="wrapper">
                    <el-input-number controls-position="right" v-model="form.age" :min="1" :max="50"></el-input-number><span>年内</span>
                </div>
            </el-form-item>
            <el-form-item   :label-width="'80px'" label="最低评价">
                <div class="wrapper inline">
                    <el-input-number controls-position="right" v-model="form.rate" :step="0.1" :min="0" :max="5"></el-input-number>
                    <el-rate disabled v-model="form.rate"></el-rate>
                </div>
            </el-form-item>
        </el-form>
        <div class="dialog-footer" slot="footer">
            <el-button @click="$emit('close')">取 消</el-button>
            <el-button @click="$emit('selectionEnabled')" type="primary">确 定</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "search",
        props:{
            form:Object
        },
        data() {
            return {
                provinces:[],
                cities:'',
                districts:'',
                streets:''
            }
        },
        methods:{
            searchLocation(level){
                if (level<0||level>3)
                    return
                let location=['中国',this.form.province,this.form.city,this.form.district];
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
                            this.cities=[{name:'全部'}];
                            this.districts=[{name:'全部'}];
                            this.streets=[{name:'全部'}];
                            break;
                        case 1:
                            this.cities=eval(regions);
                            this.districts=[{name:'全部'}];
                            this.streets=[{name:'全部'}];
                            this.form.city='';
                            this.form.district='';
                            this.form.street='';
                            break;
                        case 2:
                            this.districts=eval(regions);
                            this.streets=[{name:'全部'}];
                            this.form.district='';
                            this.form.street='';
                            break;
                        case 3:
                            this.streets=eval(regions);
                            this.streets.concat({
                                name: '全部'
                            });
                            this.form.street='';
                    }
                }).catch(e=>{
                    console.log(e);
                })
            }
        },
        mounted() {
            this.searchLocation(0);
        }
    }
</script>

<style scoped>
    #location .el-select{
        width: 120px;
        margin-left: 0;
        margin-right: 20px;
    }
    .wrapper{
        text-align: left;
    }
    .wrapper .el-input{
       width: 400px;
    }
    .dialog-footer{
        margin-top: 20px;
    }
    .el-rate{
        margin-left: 10px;
        display: inline-block;
        width: 120px;
        float: none;
    }
    span{
        margin-left: 10px;
    }
</style>