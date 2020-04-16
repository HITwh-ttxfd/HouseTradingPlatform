package com.hit.rest;

import connector.HouseDBconnection;
import entity.House;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class HouseService {

    //新增房源
    @RequestMapping(value="/addNewHouse/{sellerID}/{size}/{price}/{location}")
    public void addNewHouse(@PathVariable("sellerID")String sellerID, @PathVariable("size")Double size,
                            @PathVariable("price")Double price, @PathVariable("location")double locationX,
                            @PathVariable("location")double locationY) {
        House house=new House(sellerID,size,price,locationX,locationY);
        HouseDBconnection p = new HouseDBconnection();
        p.addHouse(house);
    }

    //删除房源
    @RequestMapping(value="/deletePastHouse/{houseID}")
    public void deletePastHouse(@PathVariable("houseID")String id) {
        HouseDBconnection p = new HouseDBconnection();
        p.deleteHouse(id);
    }

    //返回所有房源
    @RequestMapping("/house/list")
    public ArrayList<House> getPastHouses(){
        HouseDBconnection p = new HouseDBconnection();
        return p.getHouses();
    }
    //返回某一房源
    @RequestMapping("/house/{houseID}")
    public House getPastHouses(@PathVariable("houseID")String id){
        HouseDBconnection p = new HouseDBconnection();
        return p.getHouse(id);
    }
    //修改房源价格（房子也就价格可以修改了）
    @RequestMapping("/house/price/{houseID}/{price}")
    public void changeHousePrice(@PathVariable("houseID")String id,@PathVariable("price")double price){
        HouseDBconnection p = new HouseDBconnection();
        p.changeHousePrice(id,price);
    }
}
