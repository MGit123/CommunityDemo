package com.lgx.community.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2019/8/22 12:15
 * dto: 数据传输对象
 */

@Setter
@Getter
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;

}
