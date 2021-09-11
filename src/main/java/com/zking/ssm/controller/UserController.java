package com.zking.ssm.controller;

import com.zking.ssm.model.User;
import com.zking.ssm.service.IUserService;
import com.zking.ssm.util.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/handleUser")
public class UserController {

    @Autowired
    private IUserService userService;
    @RequestMapping("/godel")
    public String goDel(){
        return "delete";
    }
    @RequestMapping("/add")
    public String save(User u){
        String salt = PasswordHelper.createSalt();
        String credentials = PasswordHelper.createCredentials(u.getPassword(), salt);
        u.setPassword(credentials);
        u.setSalt(salt);
        int i = userService.insert(u);
        if(i>0){
            return "success";
        }else{
            return "failed";
        }
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> del(Integer uid){
        int i = userService.deleteByPrimaryKey(uid);
        String msg="删除成功";
        if(i<=0){
            msg="删除失败";
        }
        Map<String,Object> map=new HashMap<>();
        map.put("code",i);
        map.put("msg",msg);
        return map;
    }
}
