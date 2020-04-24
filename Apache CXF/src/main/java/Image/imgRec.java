package Image;

import javax.jws.WebService;

@WebService
public interface imgRec {
    public String imgClassify(String base);
    public String departRes(String json);
    public String storeImg(String base, String fileName, String houseID);
    public String selectImg(String fileName, String houseID);
}
