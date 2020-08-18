package com.xx.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@RestController
@Slf4j
public class Controller {
    // 每个服务启动起来对外提供的端口
    @Value("${server.port}")
    private String port;

    @GetMapping("/sayHi")
    public String sayHi(){
        return "This is " + port;
    }

    @PostMapping("/sayHi")
    public Friend sayHiPost(@RequestBody Friend friend){
//        log.info("You are " + friend.getName());
        log.info("You are " + friend.getName());
        friend.setName("xx");
        friend.setPort(port);
        return friend;

    }
}
