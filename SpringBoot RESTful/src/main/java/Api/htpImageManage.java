package Api;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class htpImageManage {
    private JaxWsDynamicClientFactory jas;
    private String url;
    private org.apache.cxf.endpoint.Client client;
    // return API result
    public String imgClassify(String base){
        client = jas.createClient(url);
        Object[] objects;
        try {
            objects = client.invoke("imgClassify",base);
            closeClient();
            return objects[0].toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "error";
    }
    // return style
    public String departRes(String json){
        client = jas.createClient(url);
        Object[] objects;
        try {
            objects = client.invoke("departRes",json);
            closeClient();
            return objects[0].toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "error";
    }
    // store image
    public String storeImage(String base, String fileName, String houseID){
        client = jas.createClient(url);
        Object[] objects;
        try {
            objects = client.invoke("storeImg",base,fileName,houseID);
            closeClient();
            return objects[0].toString();
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    // return url
    public String selectUrl(String fileName, String houseID){
        client = jas.createClient(url);
        Object[] objects;
        try {
            objects = client.invoke("selectImg",fileName,houseID);
            closeClient();
            return objects[0].toString();
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    // delImg
    public String delImg(String fileName, String houseID){
        client = jas.createClient(url);
        Object[] objects;
        try {
            objects = client.invoke("delImg",fileName,houseID);
            closeClient();
            return objects[0].toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "error";
    }
    public htpImageManage(){
        jas = JaxWsDynamicClientFactory.newInstance();
        url = "http://39.98.48.34:2233/AliyunImageStore_war/service/imgRecognition?wsdl";
    }

    public void closeClient(){
        try {
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
