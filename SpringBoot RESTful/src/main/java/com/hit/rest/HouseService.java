package com.hit.rest;


import connector.DBconnection;
import connector.HouseDBconnection;
import entity.House;
import entity.housePic;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@RestController
public class HouseService {
    //测试用
    @RequestMapping(value="/test")
    public String test(){
        return "Hello world";
    }

    //删除房源
    @RequestMapping(value="/deletePastHouse/{houseID}")
    public void deletePastHouse(@PathVariable("houseID")String id) {
        HouseDBconnection p = new HouseDBconnection();
        ArrayList<housePic> ary = DBconnection.selectImg(id);
        for (int i = 0; i < ary.size(); i++) {
            //System.out.println(ary.get(i).getHouseID());
            DBconnection.delImg(ary.get(i).getHouseID(),ary.get(i).getFileName());
        }
        p.deleteHouse(id);
    }

    //返回所有房源基本信息     第一次点击
    @RequestMapping(value="/houseList")
    public ArrayList<House> getPastHouses(){
        HouseDBconnection p = new HouseDBconnection();
        return p.getBasicHouses();
    }

    //返回卖家房源
    @RequestMapping(value="/sellerHouse/{sellerID}")
    public ArrayList<House> getSellerHouse(@PathVariable("sellerID")String id){
        HouseDBconnection p = new HouseDBconnection();
        return p.getSellerHouse(id);
    }

    //返回某一房源详细信息     第二次点击
    @RequestMapping(value="/house/{houseID}")
    public House getPastHouses(@PathVariable("houseID")String id){
        HouseDBconnection p = new HouseDBconnection();
        return p.getHouse(id);
    }

    //修改房源价格（房子也就价格可以修改了）
    @RequestMapping(value="/changeHousePrice/{houseID}/{price}")
    public void changeHousePrice(@PathVariable("houseID")String id,@PathVariable("price")double price){
        HouseDBconnection p = new HouseDBconnection();
        p.changeHousePrice(id,price);
    }

    //筛选
    @RequestMapping(value = "/select")
    public ArrayList<House> selectHouse(
            @RequestParam(value = "village")String village,
            @RequestParam(value ="position")String position, @RequestParam(value ="mimSize")double mimSize,
            @RequestParam(value ="maxSize")double maxSize, @RequestParam(value ="mimPrice")double mimPrice,
            @RequestParam(value ="maxPrice")double maxPrice,
            @RequestParam(value ="score")float score,@RequestParam(value ="time")int time) throws ParseException{
        HouseDBconnection p = new HouseDBconnection();
        ArrayList<House> houses= p.selectBasicHouse(position,village,mimSize,maxSize,mimPrice,maxPrice,score,time);
        return houses;
    }
}