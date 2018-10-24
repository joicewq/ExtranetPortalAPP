package cn.gov.tjp.app.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gov.tjp.app.trace.controller.TraceController;

public class IpUtil {

	 private static Logger LOG = LoggerFactory.getLogger(IpUtil.class);
		
	public static String getRealIp(HttpServletRequest request){
		
		LOG.info("获取ip地址X-Forwarded-For:" + request.getHeader("X-Forwarded-For") );
		LOG.info("获取ip地址X-Real-IP:" + request.getHeader("X-Real-IP") );
		LOG.info("获取ip地址Proxy-Client-IP:" + request.getHeader("Proxy-Client-IP") );
		LOG.info("获取ip地址WL-Proxy-Client-IP:" + request.getHeader("X-Forwarded-For") );
		LOG.info("获取ip地址HTTP_CLIENT_IP:" + request.getHeader("HTTP_CLIENT_IP") );
		LOG.info("获取ip地址HTTP_X_FORWARDED_FOR:" + request.getHeader("HTTP_X_FORWARDED_FOR") );
		
		LOG.info("获取ip地址getRemoteAddr:" + request.getRemoteAddr() );
		
		String ip = request.getHeader("X-Forwarded-For");
		if(ip == null || ip.length() == 0 || "unknown".equals(ip) ){
			ip = request.getHeader("X-Real-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equals(ip) ){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equals(ip) ){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equals(ip) ){
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equals(ip) ){
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip == null || ip.length() == 0 || "unknown".equals(ip) ){
			ip = request.getRemoteAddr();
			if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){
				//根据网卡取本机配置的IP
				
				try {
					InetAddress inet=null;
					inet = InetAddress.getLocalHost();
					ip= inet.getHostAddress();
				} catch (UnknownHostException e) {
					ip = request.getRemoteAddr();
				}
				
			}
		}
		
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15
			String[] _ips = ip.split(",");
			for(String _ip : _ips){
				if(_ip != null && !"unknown".equals(_ip)){
					return _ip;
				}
			}
		}
		return ip;
	}
}
