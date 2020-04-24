package entity;

public class housePic {
    private String houseID;
    private String fileName;
    private String style;
    private String path;

    public housePic(String houseID, String fileName, String style, String pic){
        this.houseID=houseID;
        this.fileName=fileName;
        this.style=style;
        this.path=pic;
    }

    public String getHouseID() {
        return houseID;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getStyle() {
        return style;
    }
}
