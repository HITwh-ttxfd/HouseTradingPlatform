package codeBase64;

import aip.auth.AuthService;
import aip.utils.HTPrecognition;

public class TestClient {
    public static void main(String[] args){
        ImageStore img = new ImageStore();
        String head = "data:image/jpeg;base64,";
        String result = AuthService.getAuth();
        //String result = HTPrecognition.easydlImageClassify(base);
        System.out.println(result);

    }

}
