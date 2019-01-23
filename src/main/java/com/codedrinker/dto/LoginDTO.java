package com.codedrinker.dto;

import lombok.Data;

/**
 * @author Gwt
 * @date 2019/01/22
 */
@Data
public class LoginDTO {

    //用户信息原始数据
    private String rawData;

    //用于验证用户信息是否被篡改过
    private String signature;

    //用户获取seesion_key的code
    private String code;
}
