package com.hit.rest;

import Api.htpImageManage;
import connector.DBconnection;
import entity.housePic;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping(value = "/imgManage")
@CrossOrigin
public class uploadImg {
    private htpImageManage htpImage = new htpImageManage();
    // private String houseID="";
    public uploadImg(){}

    //上传图片
    @RequestMapping(value = "/uploadImg64", method = RequestMethod.POST)
    @CrossOrigin
    public String uploadImage(@RequestBody Map<String,Object> map/*, HttpServletResponse response*/) throws InterruptedException {
        //response.setHeader("Access-Control-Allow-Origin","*");
        //response.setHeader("Cache-Control","no-cache");
        long randomNum = System.currentTimeMillis();
        int sleepTime = Math.abs((int)randomNum%(1000));
        Thread.sleep(sleepTime);
        String text = (String)map.get("file");
        String fileName = (String)map.get("fileName");
        String houseID = (String)map.get("houseID");
        if(text.isEmpty()){
            System.out.println("Image null.");
            return "false";
        }
        String base64 = text.split(",")[1];
        if(houseID.isEmpty()){
            System.out.println("ID null");
            return "false";
        }
        //System.out.println(base64);
        String index = htpImage.imgClassify(base64);
        String result = htpImage.departRes(index);
        if(result.equals("Chinese")){
            result="中式风格";
        }else if(result.equals("Europe")){
            result="欧式风格";
        }
        //String index = HTPrecognition.easydlImageClassify(base64);
        //String result = HTPrecognition.departBase64(index);

        if(result.equals("no house")){
            // 上传的不是房屋图片
            return "fail";
        }else if(result.equals("error")){
            return "error";
        }else {
            // 调用cxf进行存储工作
            htpImage.storeImage(base64,fileName,houseID);
            String path = htpImage.selectUrl(fileName,houseID);
            // 上传数据库
            return DBconnection.uploadImg(path,houseID,fileName,result);
            // return result;
        }
        //System.out.println(base64);
        //System.out.println(rs.toString());
    }

    //查验图片
    @PostMapping("/checkImg")
    @CrossOrigin
    public String checkImage(@RequestBody Map<String,Object> map) {
        String text = (String) map.get("file");
        String base64 = text.split(",")[1];
        String index = htpImage.imgClassify(base64);
        String result = htpImage.departRes(index);
        System.out.println(result);
        if (result.equals("no house")) {
            // 上传的不是房屋图片
            return "fail";
        }
        return "success";
    }

    // 返回图片
    @CrossOrigin
    @GetMapping(value = "/selectImg/{houseID}")
    public ArrayList<housePic> selectImage(@PathVariable("houseID")String houseID){
        System.out.println("Return img url.");
        ArrayList<housePic> bases = DBconnection.selectImg(houseID);
        return bases;
    }

    // 删除图片
    @CrossOrigin
    @GetMapping(value = "/delImg/{houseID}/{fileName}")
    public String delImage(@PathVariable("houseID")String houseID, @PathVariable("fileName")String fileName){
        String index = htpImage.delImg(fileName,houseID);
        if(index.equals("fail")){
            return "error";
        }
        return DBconnection.delImg(houseID,fileName);
    }
}


