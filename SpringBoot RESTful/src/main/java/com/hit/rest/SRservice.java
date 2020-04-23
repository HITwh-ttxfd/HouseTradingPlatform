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
    private DBconnection db = new DBconnection();
    private HouseDBconnection hdb = new HouseDBconnection();
    private User user;
    // 返回用户 ||测试成功
    @RequestMapping(value="/registerBuyer/{id}", method = RequestMethod.GET)
    public Buyer registerBuyer(@PathVariable("id")String ID, @RequestParam(value = "password")String password) {
        User u = db.selectUser(ID,password);
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
        User u = db.selectUser(ID,password);
        Seller sell = new Seller();
        if(u.getType().equals("seller")){
            sell = new Seller(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
        }
        return sell;
    }

    // comment相关
    @RequestMapping(value = "/sendComment/{id}", method = RequestMethod.GET)
    public void sendComment(@RequestParam(value = "content", required = true)String content, @PathVariable("id")String authorID,
                            @RequestParam(value = "houseID", required = true)String houseID){
        // 等待传入House
        // 上传至comment到数据库
        House house = new House();
        User u = db.selectUser(authorID);
        Buyer buy = new Buyer(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
        Comment comment = buy.sendComment(content, house);
        db.addComments(comment);
    }
    @RequestMapping(value = "/buyerReceiveComments/{id}", method = RequestMethod.GET)
    public ArrayList<Comment> buyerReceiveComments(@PathVariable("id")String authorID){
        User u = db.selectUser(authorID);
        Buyer buy = new Buyer(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
        return db.selectComments(buy);
    }
    @RequestMapping(value = "/houseReceiveComments/{id}", method = RequestMethod.GET)
    public ArrayList<Comment> houseReceiveComments(@PathVariable("id")String houseID){
        // 等待传入房源
        House house = new House();
        return db.selectComments(house);
    }
    @RequestMapping(value = "/delComment/{authorID}/{houseID}")
    public void delComment(@PathVariable("authorID")String authorID,@PathVariable("houseID")String houseID,
                           @RequestParam(value = "content")String content){
        Comment comment = new Comment(authorID,"",houseID,content);
        db.delComments(comment);
    }

    // message相关--测试成功
    @RequestMapping(value = "/sendMessage/{senderID}/{receiverID}", method = RequestMethod.GET)
    public void sendMessage(@PathVariable("senderID")String senderID, @PathVariable("receiverID")String receiverID
            ,@RequestParam(value = "content")String content){
        User u1 = db.selectUser(senderID);
        User u2 = db.selectUser(receiverID);
        Buyer buy = new Buyer(u1.getUsername(),u1.getPassword(),u1.getRealname(),u1.getId(),u1.getPhone());
        Seller sell = new Seller(u2.getUsername(),u2.getPassword(),u2.getRealname(),u2.getId(),u2.getPhone());
        Message message = buy.sendMessage(content, sell);
        db.addMessages(message);
    }
    @RequestMapping(value = "/receiveMessages/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> receiveMessages(@PathVariable("id")String receiverID){
        User u = db.selectUser(receiverID);
        List<Message> messages;
        //messages = db.selectMessages(u);
        if(u.getType().equals("seller")){
            Seller sell = new Seller(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
            messages = db.selectMessages(sell);
        }else if(u.getType().equals("buyer")){
            Buyer buy = new Buyer(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
            messages = db.selectMessages(buy);
        }else{
            System.out.println("No this user.");
            return null;
        }
        return messages;
    }

    // request相关--测试成功
    @RequestMapping(value = "/sendRequest/{senderID}/{receiverID}/{houseID}/{date}/{time}", method = RequestMethod.GET)
    public void sendRequeset(@PathVariable("senderID")String senderID, @PathVariable("receiverID")String receiverID,
                             @PathVariable(value = "houseID")String houseID,@PathVariable("date")String date,@PathVariable("time")String time){
        User u1 = db.selectUser(senderID);
        User u2 = db.selectUser(receiverID);
        Buyer buy = new Buyer(u1.getUsername(),u1.getPassword(),u1.getRealname(),u1.getId(),u1.getPhone());
        Seller sell = new Seller(u2.getUsername(),u2.getPassword(),u2.getRealname(),u2.getId(),u2.getPhone());
        // 采取另一个DBconnection类
        House house = hdb.getHouse(houseID);
        Request request = buy.sendRequest(house,sell,date,time);
        db.addRequests(request);
    }
    @RequestMapping(value = "/receiveRequests/{id}", method = RequestMethod.GET)
    public ArrayList<Request> receiveRequests(@PathVariable("id")String receiverID){
        User u = db.selectUser(receiverID);
        ArrayList<Request> requests = new ArrayList<Request>();
        if(u.getType().equals("seller")){
            Seller sell = new Seller(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
            requests = db.selectRequests(sell);

        }else if(u.getType().equals("buyer")){
            Buyer buy = new Buyer(u.getUsername(),u.getPassword(),u.getRealname(),u.getId(),u.getPhone());
            requests = db.selectRequests(buy);
        }else{
            System.out.println("No this user.");
            return null;
        }
        return requests;
    }
    @RequestMapping(value = "/delRequest/{senderID}/{receiverID}/{houseID}/{date}/{time}")
    public void delRequest(@PathVariable("senderID")String senderID,@PathVariable("receiverID")String receiverID,
                           @PathVariable("houseID")String houseID,@PathVariable("date")String date, @PathVariable("time")String time) {
        Request request = new Request(houseID,senderID,receiverID,date,time);
        db.delRequests(request);
    }
}
