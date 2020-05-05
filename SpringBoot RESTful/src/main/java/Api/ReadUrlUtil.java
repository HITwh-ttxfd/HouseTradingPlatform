package Api;


import entity.House;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;


public class ReadUrlUtil {
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            JSONObject json = JSONObject.fromObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    //测试用
//    public static void main(String[] args) throws IOException, JSONException {
//  这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
//        String ip = "113.57.244.100";
//        String str="河北省邢台市桥西区中兴路街道邢钢南区";
//        String url = "https://restapi.amap.com/v3/geocode/geo?address="+str+"&output=json&key=954da475f3d5a6d4aca79d8819f10289";
//        JSONObject json = ReadUrlUtil.readJsonFromUrl(url);
//        System.out.println(json.toString());
//        JSONArray js1=  json.getJSONArray("geocodes");
//        JSONObject js2=  js1.getJSONObject(0);
//        String temp= js2.getString("location");
//        String str1=temp.substring(0, temp.indexOf(","));
//        String str2=temp.substring(str1.length()+1, temp.length());
//        double locationx=Double.valueOf(str1.toString());
//        double locationy=Double.valueOf(str2.toString());
//        System.out.println("str1"+locationx+"str2"+locationy);
//        House house=new House();
//        house.setLocation("河北省邢台市桥西区钢铁路邢钢南区");
//        getLocationDetail(house);
//        System.out.println("locationx"+house.getLocationX());
//    }

    //将string型的location转化为double类型的x,y并赋值给house。
    public static void getLocationDetail(House house) throws IOException {
//  这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
        String ip = "113.57.244.100";
        String str=house.getLocation();
        String url = "https://restapi.amap.com/v3/geocode/geo?address="+str+"&output=json&key=954da475f3d5a6d4aca79d8819f10289";
        JSONObject json = ReadUrlUtil.readJsonFromUrl(url);
        JSONArray js1=  json.getJSONArray("geocodes");
        JSONObject js2=  js1.getJSONObject(0);
        String temp= js2.getString("location");
        //将字符串转化为doule
        String str1=temp.substring(0, temp.indexOf(","));
        String str2=temp.substring(str1.length()+1, temp.length());
        double locationx=Double.valueOf(str1.toString());
        double locationy=Double.valueOf(str2.toString());
        //给house赋值
        house.setLocationX(locationx);
        house.setLocationY(locationy);
    }
}
