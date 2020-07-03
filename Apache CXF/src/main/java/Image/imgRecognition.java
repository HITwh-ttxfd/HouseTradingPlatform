package Image;

import aip.utils.HTPrecognition;
import codeBase64.ImageStore;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "Image.imgRec", serviceName = "imgRecognition")
public class imgRecognition implements imgRec{
    ImageStore img = new ImageStore();
    @Override
    // 接口调用
        public String imgClassify(@WebParam(name = "base")String base64){
            String result = HTPrecognition.easydlImageClassify(base64);
            return result;
    }

    @Override
    // 结果拆解
    public String departRes(@WebParam(name = "json")String jsonRes){
            String result = HTPrecognition.departBase64(jsonRes);
            return result;
    }

    @Override
    // 图片存储
    public String storeImg(@WebParam(name = "base")String base,@WebParam(name = "fileName")String fileName,@WebParam(name = "id")String houseID) {
            String check = img.base64ToImage(base,fileName,houseID);
            if(check.equals("success")){
               System.out.println(fileName+" store successfully.");
               return check;
            }else{
                return check;
         }
    }

    @Override
    public String selectImg(@WebParam(name = "fileName")String fileName,@WebParam(name = "id")String houseID){
            String result = img.selectUrl(fileName,houseID);
            return result;
    }
}
