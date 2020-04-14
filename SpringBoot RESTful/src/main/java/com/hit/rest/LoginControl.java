package com.hit.rest;

import com.alibaba.fastjson.JSON;
import connector.JdbcUserDao;
import org.springframework.web.bind.annotation.*;

import entity.User;
import entity.res;
import connector.UserDao;

@RestController
public class LoginControl<Allow> {
    @RequestMapping(value = "/login/{id}",method = RequestMethod.GET)
    public res verify(@RequestParam(value = "password",required = true)String password, @PathVariable("id")String id){
        UserDao dao=new JdbcUserDao();
        User user=dao.find("phoneNumber",id);
        if (user==null)
            user=dao.find("id",id);
        if (user!=null&& user.getPassword().equals(password)){
            String radio;
            if (user.getBuyer())
                radio="buyer";
            else
                radio="seller";
            return new res(user.getUsername(),radio,"登陆成功");
        } else {
          return new res(null,null,"用户名或密码错误");
        }
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public res register(@RequestParam(value = "form",required = true)JSON form){
        return null;
    }


}
