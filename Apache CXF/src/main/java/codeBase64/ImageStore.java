package codeBase64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageStore {
    static BASE64Decoder decoder = new BASE64Decoder();  // 解码
    static BASE64Encoder encoder = new BASE64Encoder();  // 编码
    //private String path = "./houseImg/";
    private String path = "C://Program Files/MySQL/houseImg/";

    public String base64ToImage(String base64String, String fileName, String houseID) {
        // 接收的图片base64不需要带表头
        try {
            String newpath = houseID+"/";
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File direct = new File(this.path+newpath);
            if (!direct.exists()){
                direct.mkdir();
            }
            File f1 = new File(this.path+newpath+fileName);
            ImageIO.write(bi1, "jpg", f1);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String imageToBase64(String fileName) {
        // 转换出的base64也没有表头
        String packPath = fileName.substring(0,6);
        File f = new File(this.path+packPath+fileName);
        try {
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String selectUrl(String fileName, String houseID){
        String port = "http://localhost:8091/";
        String path = "houseImg/"+houseID+"/"+fileName;
        return port+path;
    }

    public ImageStore(){}
}