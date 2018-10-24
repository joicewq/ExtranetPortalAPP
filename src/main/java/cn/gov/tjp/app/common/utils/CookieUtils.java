package cn.gov.tjp.app.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lovercy on 30/11/2016.
 */
public class CookieUtils {
	
	//获取cookie的值
    public static String getValue(HttpServletRequest request,String key){
        Cookie[] cookie = request.getCookies();
        String value=null;
        if(cookie!=null && cookie.length>0) {
            for (int i = 0; i < cookie.length; i++) {
                Cookie cook = cookie[i];
                if (cook.getName().equalsIgnoreCase(key)) { //获取键
                    DES des = new DES(DES.SECRET_KEY);
                    String desValue=cook.getValue();
                    //value=desValue;
                    value = des.decrypt(desValue);    //获取值
                }
            }
        }
        return value;
    }
    //设置cookie的值
    public static void setValue(HttpServletResponse response,String key,String value){
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(1800);// 设置cookie的声明周期为半小时,1800=0.5*60*60(秒)
		cookie.setPath("/");
		response.addCookie(cookie);
    }
    //设置cookie的值
    public static String getValueNotDecrypt(HttpServletRequest request,String key){
        Cookie[] cookie = request.getCookies();
        String value=null;
        if(cookie!=null && cookie.length>0) {
            for (int i = 0; i < cookie.length; i++) {
                Cookie cook = cookie[i];
                if (cook.getName().equalsIgnoreCase(key)) { //获取键
                    String desValue=cook.getValue();
                    value=desValue;
                }
            }
        }
        return value;
    }
}
