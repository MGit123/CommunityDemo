package com.lgx.community.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2019/9/5 17:43
 */

@Setter
@Getter
public class NotificationDTO {

    private Integer id;
    private Integer notifier;
    private String notifierName;
    private String outerTitle;
    private Integer outerId;
    private Integer type;
    private String typeName;
    private Long gmtCreate;
    private Integer status;
}
