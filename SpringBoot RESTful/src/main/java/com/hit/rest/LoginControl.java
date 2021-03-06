package com.hit.rest;


import connector.JdbcUserDao;
import org.springframework.web.bind.annotation.*;

import entity.User;
import entity.res;
import connector.UserDao;

@RestController
public class LoginControl<Allow> {
    //登录
    @RequestMapping(value = "/login/{id}",method = RequestMethod.GET)
    public res verify(@RequestParam(value = "password",required = true)String password, @PathVariable("id")String id){
        UserDao dao=new JdbcUserDao();
        User user=dao.find("phone",id);
        if (user==null)
            user=dao.find("id",id);
        if (user!=null&& user.getPassword().equals(password)){
            String radio;
            return new res(user.getPhone(),user.getType(),"success");
        } else {
          return new res(null,null,"error");
        }
    }
    //判断用户是否可用
    @RequestMapping(value = "/verify",method = RequestMethod.GET)
    public res verify(@RequestParam(value = "username",required = true)String username,
                        @RequestParam(value = "password",required = true)String password,
                        @RequestParam(value = "realName",required = true)String realName,
                        @RequestParam(value = "phone",required = true)String phone,
                        @RequestParam(value = "id",required = true)String id,
                      @RequestParam(value = "type",required = true)String type) {
        UserDao dao = new JdbcUserDao();
        User searchByPhone=dao.find("phone", phone);
        User searchById=dao.find("id", id);
        if (searchByPhone == null && searchById == null) {
            return new res(null, null, "available");
        } else if (! (searchByPhone == null) ) {
            return new res(null, null, "phoneNumber engaged");
        } else {
            return new res(null, null, "id engaged");
        }
    }
    //注册用户
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public int register(@RequestParam(value = "username",required = true)String username,
                        @RequestParam(value = "password",required = true)String password,
                        @RequestParam(value = "realName",required = true)String realName,
                        @RequestParam(value = "phone",required = true)String phone,
                        @RequestParam(value = "id",required = true)String id,
                         @RequestParam(value = "type",required = true)String type) {
        new JdbcUserDao().add(new User(username, password, realName, id, phone,type));
        return 0;
    }

}
