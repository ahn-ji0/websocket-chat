package com.chat.websocketchat.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String type;
    private String sender;
    private Object data;

    public void newConnect(){
        this.type = "new";
    }

    public void closeConnect(){
        this.type = "close";
    }

    @Override
    public String toString() {
        return "Message{" +
            "type='" + type + '\'' +
            ", sender='" + sender + '\'' +
            ", data=" + data +
            '}';
    }
}
