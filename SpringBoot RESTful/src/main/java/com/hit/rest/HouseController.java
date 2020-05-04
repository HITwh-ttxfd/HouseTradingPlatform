package com.hit.rest;



import Api.ReadUrlUtil;
import connector.HouseDBconnection;
import entity.House;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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


        //设置房屋年份
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());
        String tempnow= formatter.format(date);
        int now=Integer.valueOf(tempnow.toString());
        String temppast= formatter.format(house.getTime());
        int past=Integer.valueOf(temppast.toString());
        int age=now-past;
        house.setHousingAge(String.valueOf(age));

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
