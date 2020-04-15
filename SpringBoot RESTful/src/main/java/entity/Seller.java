package entity;

public class Seller extends User {
    public Seller(){}

    public Seller(String username,String password,String realname,String id,String phone){
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.id = id;
        this.phone = phone;
        this.type = "seller";
    }

    /*public void receiveRequests(ArrayList<com.company.Request> requests){
        //获取id匹配的requests
        if(requests!=null){
            this.requests = requests;
        }
        System.out.println("Receive successfully.");
        Iterator<com.company.Request> iterator = this.requests.iterator();
        //迭代器
        while (iterator.hasNext()){
            com.company.Request req = iterator.next();
            System.out.println(req.getHouseID()+" "+req.getDate());
        }
    }*/
}