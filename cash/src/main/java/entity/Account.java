package entity;

import lombok.Data;

/*
用户
 */
@Data
public class Account {
    private Integer id;
    private String username;
    private String password;
}
