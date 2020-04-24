package com.hit.rest;


import connector.HouseDBconnection;
import entity.House;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class HouseService {
    //测试用
    @RequestMapping(value="/test")
    public String test(){
        return "Hello world";
    }

//    //新增房源
//    @RequestMapping(value="/addNewHouse/{sellerID}/size/" +
//            "{location}/price/" +
//            "{time}/lift/{houseType}/" +
//            "{buildingArea}/{interiorArea}/" +
//            "{houseOrientation}/{decoration}/" +
//            "{heatingMode}/{floor}/" +
//            "{houseTypeStructure}/{buildingType}/" +
//            "{elevatorProportion}/" +
//            "{listingTime}/{lastTransaction}/" +
//            "{housingAge}/{mortgageInformation}/" +
//            "{transactionOwnership}/{housingPurpose}/" +
//            "{propertyOwnership}/{housingParts}")
//    public void addNewHouse(
//            @PathVariable("sellerID")String sellerID,@PathVariable("size") double size,
//            @PathVariable("location")String location,@PathVariable("price") double price,
//            @PathVariable("time")Date time, @PathVariable("lift")int lift,
//            @PathVariable("houseType")String houseType,
//            @PathVariable("buildingArea")double buildingArea, @PathVariable("interiorArea")double interiorArea,
//            @PathVariable("houseOrientation")String houseOrientation,@PathVariable("decoration") String decoration,
//            @PathVariable("heatingMode")String heatingMode, @PathVariable("floor")String floor,
//            @PathVariable("houseTypeStructure")String houseTypeStructure,@PathVariable("buildingType") String buildingType,
//            @PathVariable("elevatorProportion")String elevatorProportion,
//            @PathVariable("listingTime")Date listingTime, @PathVariable("lastTransaction")Date lastTransaction,
//            @PathVariable("housingAge")String housingAge,@PathVariable("mortgageInformation") String mortgageInformation,
//            @PathVariable("transactionOwnership")String transactionOwnership, @PathVariable("location")String housingPurpose,
//            @PathVariable("propertyOwnership")String propertyOwnership, @PathVariable("housingParts")String housingParts) {
//        //这里调用api解析地址产生地理编码
//        int locationX = 0,locationY = 0;
////        House house=new House(sellerID,size,price,locationX,locationY,location);
////        HouseDBconnection p = new HouseDBconnection();
////        p.addHouse(house);
//
//        House house=new House();
//        HouseDBconnection p = new HouseDBconnection();
//        p.addHouse(house);
//    }

    //删除房源
    @RequestMapping(value="/deletePastHouse/{houseID}")
    public void deletePastHouse(@PathVariable("houseID")String id) {
        HouseDBconnection p = new HouseDBconnection();
        p.deleteHouse(id);
    }

    //返回所有房源基本信息     第一次点击
    @RequestMapping(value="/houseList")
    public ArrayList<House> getPastHouses(){
        HouseDBconnection p = new HouseDBconnection();
        return p.getBasicHouses();
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