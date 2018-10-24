package cn.gov.tjp.app.helpcenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.common.utils.CookieUtils;
import cn.gov.tjp.app.common.utils.ResultMessage;
import cn.gov.tjp.app.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lovercy on 22/11/2016.
 */
@Controller
@RequestMapping("/help")
public class HelpController {
	
    @RequestMapping("/index")
    public String register(){
        return "/helpcenter/index";
    }
    @RequestMapping("/product")
    public String helpproduct(){
        return "/helpcenter/product";
    }
    @RequestMapping("/company")
    public String helpcompany(){
        return "/helpcenter/company";
    }
    @RequestMapping("/checkproduct")
    public String helpcheckproduct(){
        return "/helpcenter/checkproduct";
    }
    @RequestMapping("/complain")
    public String helpcomplain(){
        return "/helpcenter/complain";
    }
    @RequestMapping("/register")
    public String helpregister(){
        return "/helpcenter/register";
    }
    @RequestMapping("/protocol")
    public String helpprotocol(){
        return "/helpcenter/protocol";
    }
    /*
     * 网站统计
    */
    @RequestMapping("/statistics")
    public String statistics(){
        return "/helpcenter/statistics";
    }
}
