package com.lianjia.service;

import com.lianjia.util.WxUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

/**
 * Created by chen on 2018/5/13.
 */
@Service
@Slf4j
public class WxService {

    @Autowired
    HttpAPIService httpAPIService;

    /**
     * 微信授权
     * @return
     */
    @SneakyThrows
    public String auth(String backUrl){
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                "?appid=" + WxUtils.APP_ID +
                "&redirect_uri=" + URLEncoder.encode(WxUtils.REDIRECT_URL, "utf-8") +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=" + URLEncoder.encode(backUrl, "utf-8") +
                "#wechat_redirect";
        return url;
    }

    /**
     * 获取accessToken
     * @return  { "access_token":"ACCESS_TOKEN",
                    "expires_in":7200,
                    "refresh_token":"REFRESH_TOKEN",
                    "openid":"OPENID",
                    "scope":"SCOPE" }
     */
    @SneakyThrows
    public String getAccseToken(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=" + WxUtils.APP_ID +
                "&secret=" + WxUtils.APP_SEERET +
                "&code=" + code +
                "&grant_type=authorization_code";
        String jsonString = httpAPIService.doGet(url);
        return jsonString;
    }

    /**
     * 拉取用户信息
     *  {    "openid":" OPENID",
             " nickname": NICKNAME,
             "sex":"1",
             "province":"PROVINCE"
             "city":"CITY",
             "country":"COUNTRY",
             "headimgurl":    "http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
             "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
             "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     }
     */
    @SneakyThrows
    public String getUserInfo(String access_token, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=" + access_token +
                "&openid=" + openid +
                "&lang=zh_CN";
        return httpAPIService.doGet(url);
    }

    /**
     * 刷新token
     * { "access_token":"ACCESS_TOKEN",
         "expires_in":7200,
         "refresh_token":"REFRESH_TOKEN",
         "openid":"OPENID",
         "scope":"SCOPE" }
     */
    @SneakyThrows
    public String refreshToken(String refresh_token, String openid) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token" +
                "?appid=" + WxUtils.APP_ID +
                "&grant_type=refresh_token" +
                "&refresh_token=" + refresh_token;
        return httpAPIService.doGet(url);
    }

    /**
     * 检验授权凭证（access_token）是否有效
     * @param openid
     * @return
     * { "errcode":0,"errmsg":"ok"}
     */
    @SneakyThrows
    public String validateAccessToken(String access_token, String openid) {
        String url = "https://api.weixin.qq.com/sns/auth" +
                "?access_token=" + access_token +
                "&openid=" + openid;
        return httpAPIService.doGet(url);
    }
}