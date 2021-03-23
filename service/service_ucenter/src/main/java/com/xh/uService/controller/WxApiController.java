package com.xh.uService.controller;

import com.google.gson.Gson;
import com.xh.commentUtils.JwtUtils;
import com.xh.uService.entity.UcenterMember;
import com.xh.uService.service.UcenterMemberService;
import com.xh.uService.utils.ConstantWxUtils;
import com.xh.uService.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * 微信接口
 * @author a3818
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;
    /**
     * 扫码登录后调用的一个函数 尚硅谷在他的服务器上做了处理扫码互会返回两个参数 code state
     * @param code 临时票据
     * @param state 原样返回
     * @return
     */
    @GetMapping("/callback")
    public String callback(String code,String state){
        //1 扫码登录后获取到临时票据 code 类似于验证码
        try {

            //2 携带code访问微信官方提供的另外一个固定地址
            String baseUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=" +code+
                    "&grant_type=authorization_code";
            //设置%s里面值
            String getAccessTokenAndOpenIdUrl = String.format(
                    baseUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET
            );

            // 使用httpclient请求getAccessTokenAndOpenIdUrl 获取到返回参数AccessToken 和 OpenId
            // 请求返回的是一个字符串 需要用gosn 转成map对象形式
            String accessTokenAndOpenId = HttpClientUtils.get(getAccessTokenAndOpenIdUrl);
            System.out.println("accessTokenAndOpenId"+accessTokenAndOpenId);
            Gson gson = new Gson();
            //  将返回的字符串转成map对象形式
            HashMap map = gson.fromJson(accessTokenAndOpenId, HashMap.class);
            String access_token = (String)map.get("access_token");
            String openid = (String)map.get("openid");
            //根据openid 到数据库中查询用户是否已经注册， 如果已经注册那就直接登录
            UcenterMember ucenterMember = ucenterMemberService.getUserInfoByOpenId(openid);
            // 如果在数据库中没有是用户那么需要更新到数据库中
            if (ucenterMember==null){
                //3 将是获到AccessToken 和 OpenId 再去请求微信官方固定地址,获取到用户详细信息

                String baseURL = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseURL,
                        access_token,
                        openid
                );
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap userInfoJson = gson.fromJson(userInfo, HashMap.class);
                ucenterMember = new UcenterMember();
                ucenterMember.setOpenid((String) userInfoJson.get("openid"));
                ucenterMember.setNickname((String) userInfoJson.get("nickname"));
                ucenterMember.setAvatar((String) userInfoJson.get("headimgurl"));
                ucenterMember.setSex(((Double)userInfoJson.get("sex")).intValue());
                ucenterMemberService.save(ucenterMember);

            }

            String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

            return "redirect:http://localhost:3000?token="+token;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:http://localhost:3000";

    }

    /**
     * 生成微信二维码
     * @return
     */
    @GetMapping("/login")
    public String wxLogin(){

        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "&wechat_redirect";

        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch(Exception e) {

        }
        //设置%s里面值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );

        //重定向到请求微信地址里面
        return "redirect:"+url;
    }

}
