package com.codedrinker.controller;

import com.codedrinker.adapter.WechatAdapter;
import com.codedrinker.dto.LoginDTO;
import com.codedrinker.dto.ResultDTO;
import com.codedrinker.dto.SessionDTO;
import com.codedrinker.dto.TokenDTO;
import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.error.ErrorCodeException;
import com.codedrinker.util.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Gwt
 * @date 2019/01/22
 */
@RestController
@Slf4j
public class LoginController {

    //Spring自动注入wechatAdapter，因WechatAdapter类上面有@Server注解
    @Autowired
    private WechatAdapter wechatAdapter;

    //定义domain/api/login访问接口，用于实现登录
    //使用LoginDTO自动解析传递过来的JSON数据
    @RequestMapping("/api/login")
    public ResultDTO login(@RequestBody LoginDTO loginDTO) {
        try {
            log.info("login request : {}", loginDTO);
            //使用code调用微信API获取session_key和openid
            SessionDTO sessionDTO = wechatAdapter.jscode2session(loginDTO.getCode());
            log.info("login get session : {}", sessionDTO);

            //检验传递过来的用户信息是否合法
            DigestUtil.checkDigest(loginDTO.getRawData(), sessionDTO.getSessionKey(), loginDTO.getSignature());
            // TODO: 2019-01-22 存储 token
            //生成token 用于自定义登录态
            TokenDTO data = new TokenDTO();
            data.setToken(UUID.randomUUID().toString());
            return ResultDTO.ok(data);

        }catch (ErrorCodeException e) {

            log.error("login error, info : {}", loginDTO, e.getMessage());
            return ResultDTO.fail(e);

        }catch (Exception e) {
            log.error("login error, info: {}", loginDTO, e);
            return ResultDTO.fail(CommonErrorCode.UNKOWN_ERROR);
        }
    }
}
