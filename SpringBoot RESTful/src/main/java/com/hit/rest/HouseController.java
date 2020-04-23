package com.hit.rest;



import Api.ReadUrlUtil;
import connector.HouseDBconnection;
import entity.House;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController//            @RestController的作用等同于@Controller + @ResponseBody
@CrossOrigin //              解决跨域问题
public class HouseController {

    //新增房源
    //注意：   这里传过来的house没有score，houseid，locationx，locationy
    @CrossOrigin
    @RequestMapping(value = "/addhouse",method= RequestMethod.POST)
    public void login(@RequestBody House house) throws IOException {//@RequestBody: 接收前端发送的json数据，其中JSONObject是封装好的实体
        house.setScore(0);
//这里调用api解析地址产生地理编码
        ReadUrlUtil.getLocationDetail(house);
        HouseDBconnection p = new HouseDBconnection();
        p.addHouse(house);
    }


    //测试用方法
    @CrossOrigin
    @RequestMapping(value = "/jsonTest",method= RequestMethod.POST)
    public void test(){
        System.out.println("Test Right");
    }


}
