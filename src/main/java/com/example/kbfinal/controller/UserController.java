package com.example.kbfinal.controller;

import com.example.kbfinal.entity.User;
import com.example.kbfinal.exception.BusinessException;
import com.example.kbfinal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // user 정보를 입력, 삭제, 수정하는 API 생성
    @PostMapping("/user/register")
    @ResponseBody
    public int register(String username, String password){
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);

        userService.registerUser(user);

        return 100;

    }

    @PutMapping("/user/update")
    @ResponseBody
    public int update(Long id, String username, String password){
        if(userService.findById(id)) {
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);

            userService.registerUser(user);

            return 100;

        }else {
            throw new BusinessException("ID가 존재하지 않습니다.");
        }



    }

    @PutMapping("/user/delete")
    @ResponseBody
    public int delete(Long id){
        if(userService.findById(id)) {
            userService.delete(id);
            return 100;
        }else{
            throw new BusinessException("ID가 존재하지 않습니다.");
        }
    }

    // 전체 user List를 조회하는 api 생성
    @GetMapping("/user/list")
    @ResponseBody
    public List<User> Users(){

        return userService.findUsers();
    }


    // 전체 user 의 숫자를 조회하는 api 생성
    @GetMapping("/user/count")
    @ResponseBody
    public Long Usercount(){
        return userService.usercount();
    }

    @GetMapping("/login")
    @ResponseBody
    public boolean login(String username, String password){
        return userService.authenticate(username, password);
    }

}
