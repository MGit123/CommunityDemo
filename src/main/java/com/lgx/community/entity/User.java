package com.lgx.community.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2019/8/23 17:53
 */

@Getter
@Setter
public class User {


    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
