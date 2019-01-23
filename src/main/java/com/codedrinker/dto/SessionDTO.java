package com.codedrinker.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author Gwt
 * @date 2019/01/22
 */
@Data
public class SessionDTO {

    private String openid;

    @JSONField(name = "session_key")
    private String sessionKey;
}
