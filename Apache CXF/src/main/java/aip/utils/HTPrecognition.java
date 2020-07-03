package aip.utils;

import aip.auth.AuthService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * easydl图像分类
 */
public class HTPrecognition {
    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    /**
     *
     * @param imgbase-图片base64编码
     * @return 识别结果, json数组
     */
    public static String easydlImageClassify(String imgbase) {
        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/classification/htprecognition";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", imgbase);
            map.put("top_num", "3");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            //String accessToken = "24.ca33cbbde7545bb4d14a90d8e776f7e7.2592000.1592460286.282335-19462141";
            String accessToken = AuthService.getAuth();
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 
     *
     * @param result-识别结果
     * @return 拆分出优先识别结果-no house / Chinese / Europe
     */
    public static String departBase64(String result){
        String json = result.substring(result.indexOf("{"));
        JSONObject obj = JSONObject.fromObject(json);
        String index = obj.getString("results");
        JSONArray js = JSONArray.fromObject(index);
        ArrayList<String> style = new ArrayList<String>();
        ArrayList<String> score = new ArrayList<String>();
        for(int i=0;i<js.size();i++){
            JSONObject tmp = JSONObject.fromObject(js.getJSONObject(i));
            style.add(tmp.getString("name"));
            score.add(tmp.getString("score"));
        }
        String baseStyle = style.get(0);
        double baseScore = Double.parseDouble(score.get(0));
        if(baseStyle.equals("[default]") && baseScore>0.40){
            return "no house";
        }else if(baseScore>0.40){
            return baseStyle;
        }
        return "fail";
    }
    /*public static void main(String[] args) {
        EasydlImageClassify.easydlImageClassify();
    }*/
}