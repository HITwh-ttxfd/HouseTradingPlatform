package codeBase64;

public class TestClient {
    public static void main(String[] args){
        ImageStore img = new ImageStore();
        String head = "data:image/jpeg;base64,";
        //String result = HTPrecognition.easydlImageClassify(base);
        //img.base64ToImage(base,"13.jpg","1");
        String res = img.selectUrl("13.jpg","1");
        System.out.println(res);
        //String url = img.selectUrl();
        //System.out.println(url);
        //img.base64ToImage(base64);
        //String result = img.imageToBase64();
        //System.out.println(result);
    }

}
