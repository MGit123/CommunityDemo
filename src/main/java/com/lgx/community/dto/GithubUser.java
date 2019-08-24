package com.lgx.community.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2019/8/22 21:35
 */

@Getter
@Setter
public class GithubUser {

    private String login;
    private Long id;
    private String avatar_url;

}
