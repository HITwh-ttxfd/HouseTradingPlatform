package com.hit.rest;

import connector.DBconnection;
import connector.HouseDBconnection;
import entity.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/SRservice")
public class SRservice {
    //@Autowired
    private HouseDBconnection hdb = new HouseDBconnection();
    private User user;
    // 返回用户 ||测试成功
    @RequestMapping(value="/registerBuyer/{id}", method = RequestMethod.GET)
    public Buyer registerBuyer(@PathVariable("id")String ID, @RequestParam(value = "password")String password) {
        User u = DBconnection.selectUser(ID,password);
        System.out.println(u.getUsername()+" read.");
        System.out.println(u.getType());
        Buyer buy = new Buyer();
        //Buyer buy = new Buyer(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
        if(u.getType().equals("buyer")){
            buy = new Buyer(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
        }
        return buy;
    }
    @RequestMapping(value = "/registerSeller/{id}", method = RequestMethod.GET)
    public Seller registerSellyer(@PathVariable("id")String ID, @RequestParam(value = "password")String password) {
        User u = DBconnection.selectUser(ID,password);
        Seller sell = new Seller();
        if(u.getType().equals("seller")){
            sell = new Seller(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
        }
        return sell;
    }

    // comment相关--测试成功 未做查重处理
    @RequestMapping(value = "/sendComment/{id}", method = RequestMethod.GET)
    public String sendComment(@RequestParam(value = "content", required = true)String content,
                              @PathVariable("id")String authorID,
                              @RequestParam(value = "houseID", required = true)String houseID,
                              @RequestParam(value = "score")String score){
        // 上传至comment到数据库
        House house = hdb.getHouse(houseID);
        User u = DBconnection.selectUser(authorID);
        Buyer buy = new Buyer(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
        Comment comment = buy.sendComment(content, house,score);
        String result = DBconnection.addComments(comment);
        //等待调用更新评分
        return result;
    }
    @RequestMapping(value = "/buyerReceiveComments/{id}", method = RequestMethod.GET)
    public ArrayList<Comment> buyerReceiveComments(@PathVariable("id")String authorID){
        User u = DBconnection.selectUser(authorID);
        Buyer buy = new Buyer(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
        return DBconnection.selectComments(buy);
    }
    @RequestMapping(value = "/houseReceiveComments/{id}", method = RequestMethod.GET)
    public ArrayList<Comment> houseReceiveComments(@PathVariable("id")String houseID){
        House house = hdb.getHouse(houseID);
        System.out.println(house.getHouseID());
        return DBconnection.selectComments(house);
    }
    @RequestMapping(value = "/delComment/{authorID}/{houseID}")
    public String delComment(@PathVariable("authorID")String authorID,@PathVariable("houseID")String houseID,
                             @RequestParam(value = "content")String content,@RequestParam(value = "date")String date,
                             @RequestParam(value = "score")String score){
        Comment comment = new Comment(authorID,date,houseID,content,score);
        return DBconnection.delComments(comment);
    }


    // message相关--测试成功
    @RequestMapping(value = "/sendMessage/{senderID}/{receiverID}", method = RequestMethod.GET)
    public String sendMessage(@PathVariable("senderID")String senderID, @PathVariable("receiverID")String receiverID
            ,@RequestParam(value = "content")String content){
        User u1 = DBconnection.selectUser(senderID);
        User u2 = DBconnection.selectUser(receiverID);
        Buyer buy = new Buyer(u1.getUsername(),u1.getPassword(),u1.getRealname(),u1.getId(),u1.getPhone());
        Seller sell = new Seller(u2.getUsername(),u2.getPassword(),u2.getRealname(),u2.getId(),u2.getPhone());
        Message message = buy.sendMessage(content, sell);
        return DBconnection.addMessages(message);
    }
    @RequestMapping(value = "/receiveMessages/{id1}/{id2}", method = RequestMethod.GET)
    @ResponseBody // 返回符合id的所有消息
    public List<Message> receiveMessages(@PathVariable("id1")String senderID,@PathVariable("id2")String receiverID){
        User u1 = DBconnection.selectUser(senderID);
        User u2 = DBconnection.selectUser(receiverID);
        List<Message> messages;
        messages = DBconnection.selectMessages(u1,u2);
/*        if(u.getType().equals("seller")){
            Seller sell = new Seller(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
            messages = DBconnection.selectMessages(sell);
        }else if(u.getType().equals("buyer")){
            Buyer buy = new Buyer(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
            messages = DBconnection.selectMessages(buy);
        }else{
            System.out.println("No this user.");
            return null;
        }*/
        return messages;
    }
    @GetMapping(value = "/changeMessageStatus/{senderID}/{receiverID}/{content}/{date}")
    public String changeMessageStatus(@PathVariable("senderID")String send,@PathVariable("receiverID")String receive,
                                      @PathVariable("content")String content,@PathVariable("date")String date,
                                      @RequestParam(name = "status")String status){
        String name = DBconnection.selectUser(receive).getUsername();
        Message message = new Message(name,send,receive,content,date,status);
        return DBconnection.changeMessageStatus(message,status);
        //return "success";
    }
    @GetMapping(value = "/delMessage/{senderID}/{receiverID}/{content}/{date}")
    public String delMessage(@PathVariable("senderID")String send,@PathVariable("receiverID")String receive,
                             @PathVariable("content")String content,@PathVariable("date")String date){
        String name = DBconnection.selectUser(receive).getUsername();
        Message message = new Message(name,send,receive,content,date);
        return DBconnection.delMessage(message);
    }
    @GetMapping(value = "/selectConversation/{id}")
    public ArrayList<Conversation> selectConversation(@PathVariable("id")String id){
        User u = DBconnection.selectUser(id);
        ArrayList<Conversation> list = new ArrayList<>();
        list = DBconnection.selectConversation(u);
        return list;
    }


    // request相关--测试成功
    @RequestMapping(value = "/sendRequest/{senderID}/{receiverID}/{houseID}/{phone}", method = RequestMethod.GET)
    public String sendRequeset(@PathVariable("senderID")String senderID, @PathVariable("receiverID")String receiverID,
                               @PathVariable("houseID")String houseID,@PathVariable("phone")String phone,
                               @RequestParam(value = "date")String date,@RequestParam(value = "time")String time){
        User u1 = DBconnection.selectUser(senderID);
        User u2 = DBconnection.selectUser(receiverID);
        Buyer buy = new Buyer(u1.getUsername(),u1.getPassword(),u1.getRealname(),u1.getId(),u1.getPhone());
        Seller sell = new Seller(u2.getUsername(),u2.getPassword(),u2.getRealname(),u2.getId(),u2.getPhone());
        House house = hdb.getHouse(houseID);
        String location = house.getLocation(); // getLocation
        // 采取另一个DBconnection类
        Request request = buy.sendRequest(house,sell,date,time,phone,location);
        String check = DBconnection.selectRequest(request);
        if(check.equals("exist")){
            return check;
        }else if(check.equals("new")){
            DBconnection.addRequests(request);
            return "success";
        }else{
            return "fali";
        }
    }
    @RequestMapping(value = "/receiveRequests/{id}", method = RequestMethod.GET)
    public ArrayList<Request> receiveRequests(@PathVariable("id")String receiverID){
        User u = DBconnection.selectUser(receiverID);
        ArrayList<Request> requests = new ArrayList<Request>();
        if(u.getType().equals("seller")){
            Seller sell = new Seller(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
            requests = DBconnection.selectRequests(sell);

        }else if(u.getType().equals("buyer")){
            Buyer buy = new Buyer(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
            requests = DBconnection.selectRequests(buy);
        }else{
            System.out.println("No this user.");
            return null;
        }
        return requests;
    }
    @RequestMapping(value = "/delRequest/{senderID}/{receiverID}/{houseID}/{phone}")
    public String delRequest(@PathVariable("senderID")String senderID,@PathVariable("receiverID")String receiverID,
                             @PathVariable("houseID")String houseID,@PathVariable("phone")String phone,
                             @RequestParam(value =  "date")String date, @RequestParam(value = "time")String time) {
        House house = hdb.getHouse(houseID);
        String location = house.getLocation(); // getlocation
        Request request = new Request(houseID,senderID,receiverID,date,time,phone,location);
        String result = DBconnection.delRequests(request);
        if(result.equals("success")){
            return result;
        }else if(result.equals("fail")){
            return result;
        }else{
            return "error";
        }
    }
    @RequestMapping(value = "/changeRequestStatus/{senderID}/{receiverID}/{houseID}")
    public String changeRequest(@PathVariable("senderID")String senderID,@PathVariable("receiverID")String receiverID,
                                @PathVariable("houseID")String houseID,@RequestParam(value = "status")String choice){
        String result = DBconnection.changeRequestStatus(senderID,receiverID,houseID,choice);
        if(result.equals("success")){
            return result;
        }else if(result.equals("fail")){
            return result;
        }else{
            return "error";
        }
    }

}