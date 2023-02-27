package com.chat.websocketchat.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1")
public class ChatController {

    @GetMapping("/chat")
    public String chat(){
        return "v1/chat";
    }
}
