package com.hit.rest;



import Api.ReadUrlUtil;
import connector.HouseDBconnection;
import entity.House;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController//            @RestController的作用等同于@Controller + @ResponseBody
@CrossOrigin //              解决跨域问题
public class HouseController {
    private HouseDBconnection p = new HouseDBconnection();
    //新增房源
    //注意：   这里传过来的house没有score，houseid，locationx，locationy
    @CrossOrigin
    @RequestMapping(value = "/addhouse",method= RequestMethod.POST)
    public void login(@RequestBody JSONObject json) throws IOException, ParseException {//@RequestBody: 接收前端发送的json数据，其中JSONObject是封装好的实体
        //System.out.println(json.toString());
        JSONObject jsonobject=  json.getJSONObject("house");
        House house= (House)JSONObject.toBean(jsonobject,House.class);



        house.setScore(0);
        //这里调用api解析地址产生地理编码
        ReadUrlUtil.getLocationDetail(house);

        //设置时间转化     time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTemp = sdf.parse(jsonobject.getString("time"));
        house.setTime(dateTemp);
        //         lastTransaction
        dateTemp = sdf.parse(jsonobject.getString("lastTransaction"));
        house.setLastTransaction(dateTemp);
        //             listingTime
        dateTemp = sdf.parse(jsonobject.getString("listingTime"));
        house.setListingTime(dateTemp);


        //设置房屋年份
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());
        String tempnow= formatter.format(date);
        int now=Integer.valueOf(tempnow.toString());
        String temppast= formatter.format(house.getTime());
        int past=Integer.valueOf(temppast.toString());
        int age=now-past;
        house.setHousingAge(String.valueOf(age));

        p.addHouse(house);
    }

    @CrossOrigin
    @GetMapping("/getHouseID")
    public String getNewID(){
        // 在添加房源前用于添加图片
        String id = p.selectMaxHouseID();
        //System.out.println(id);
        return id;
    }

    //测试用方法
    @CrossOrigin
    @RequestMapping(value = "/jsonTest",method= RequestMethod.POST)
    public void test(){
        System.out.println("Test Right");
    }


}
