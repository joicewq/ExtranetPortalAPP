package cn.gov.tjp.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.qhgrain.ecom.common.util.ResultMessage;

import cn.gov.tjp.app.common.utils.DES;

@Controller
@RequestMapping("/cookie")
public class getCookie {
    
    /**
     * 获取cookie中存的用户相关信息,也可判断用户是否登录
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月15日
     */
    @RequestMapping("/getCookie")
    @ResponseBody
    public JSONPObject getCookie(HttpServletRequest request,HttpServletResponse response){
        String result=request.getParameter("callback");
        String loginId="";
        Cookie[] cookie = request.getCookies();
        System.out.println("getCookie.do-cookie:"+JSON.toJSONString(cookie));
        if(cookie!=null && cookie.length>0){
            System.out.println("for xunhuan");
            for (int i = 0; i < cookie.length; i++) {
                Cookie cook = cookie[i];
                if(cook.getName().equalsIgnoreCase("loginId")){ //获取键 
                    DES des=new DES(DES.SECRET_KEY);
                    loginId=des.decrypt(cook.getValue().toString());    //获取值 
                }
            }
        }
        JSONPObject jpo=new JSONPObject(result);
        System.out.println("getCookie.do-loginId:"+loginId);
        jpo.addParameter(loginId);
        System.out.println(JSON.toJSONString(jpo));
        return jpo;
    }
    
    /**
     * 删除cookie里的信息,用于用户退出系统
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月15日
     */
    @RequestMapping("/delCookie")
    @ResponseBody
    public JSONPObject delCookie(HttpServletRequest request,HttpServletResponse response){
        String callback=request.getParameter("callback");
        ResultMessage result=new ResultMessage(ResultMessage.Fail);
        Cookie cookie = new Cookie("loginId",null);   
        cookie.setMaxAge(0);
        cookie.setPath("/"); 
        response.addCookie(cookie);
        result.setResult(ResultMessage.Success);
        JSONPObject jpo=new JSONPObject(callback);
        jpo.addParameter(result);
        System.out.println(JSON.toJSONString(jpo));
        return jpo;
    }
}
