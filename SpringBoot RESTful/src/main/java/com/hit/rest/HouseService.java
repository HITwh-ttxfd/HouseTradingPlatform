package com.hit.rest;

import connector.HouseDBconnection;
import entity.House;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class HouseService {
    @RequestMapping(value="/addNewHouse/{sellerID}/{size}/{price}/{location}")
    public void addNewHouse(@PathVariable("sellerID")String sellerID, @PathVariable("size")Double size,
                            @PathVariable("price")Double price, @PathVariable("location")double locationX,
                            @PathVariable("location")double locationY) {
        House house=new House(sellerID,size,price,locationX,locationY);
        HouseDBconnection p = new HouseDBconnection();
        p.addHouse(house);
    }
    //检验用方法
    @RequestMapping(value="/try/{ID}/{ie}",method=RequestMethod.GET)
    public int try1(@PathVariable("ID")int ID,@PathVariable("ie")int ie){return ID+ie;}


    @RequestMapping(value="/deletePastHouse/{ID}")
    public void deletePastHouse(@PathVariable("ID")String id) {
        HouseDBconnection p = new HouseDBconnection();
        p.deleteHouse(id);
    }
    @RequestMapping("/list")
    public List<House> getPastHouse(){
        HouseDBconnection p = new HouseDBconnection();
        return p.getHouse();
    }
}
