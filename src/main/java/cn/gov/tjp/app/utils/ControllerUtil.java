package cn.gov.tjp.app.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.pubframework.common.util.StringUtilExt;

public class ControllerUtil {

	public static void getSetParameters(HttpServletRequest request){
		Map<String,String[]> map = request.getParameterMap();
		for(Map.Entry<String, String[]> m : map.entrySet()){
			if(m.getValue() != null){
				request.setAttribute(m.getKey(), m.getValue()[0]);
			}
			
		}
	}
	public static void getParameterForMap(HttpServletRequest request , Map<String,Object> map){
		Map<String,String[]> paramMap = request.getParameterMap();
		for(Map.Entry<String, String[]> m : paramMap.entrySet()){
			if(m.getValue() != null && StringUtilExt.isNotBlank(m.getValue()[0])){
				map.put(m.getKey(), m.getValue()[0].trim());
			}
			
		}
	}
	
	/**
     * 获取访问者IP
     * 
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
