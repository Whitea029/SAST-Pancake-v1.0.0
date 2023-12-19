package com.whitea.sastpancake.entity.po;

import jakarta.websocket.OnOpen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Integer id;

    private String userName;

    private String password;

    private Integer role;


}
