package Api;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class htpImageManage {
    private JaxWsDynamicClientFactory jas = JaxWsDynamicClientFactory.newInstance();
    private String url = "http://localhost:8091/Apache_CXF_war/service/imgRecognition?wsdl";
    private org.apache.cxf.endpoint.Client client = jas.createClient(url);
    // return API result
    public String imgClassify(String base){
        Object[] objects;
        try {
            objects = client.invoke("imgClassify",base);
            return objects[0].toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "error";
    }
    // return style
    public String departRes(String json){
        Object[] objects;
        try {
            objects = client.invoke("departRes",json);
            return objects[0].toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "error";
    }
    // store image
    public String storeImage(String base, String fileName, String houseID){
        Object[] objects;
        try {
            objects = client.invoke("storeImg",base,fileName,houseID);
            return objects[0].toString();
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    // return url
    public String selectUrl(String fileName, String houseID){
        Object[] objects;
        try {
            objects = client.invoke("selectImg",fileName,houseID);
            return objects[0].toString();
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    public htpImageManage(){}
}
