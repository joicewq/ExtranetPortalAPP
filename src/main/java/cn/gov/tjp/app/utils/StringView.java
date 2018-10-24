/**
*File： StringView.java
*Copyright 2015 Hangzhou Podinns
*All right reserved.
*Date        Author      Changes
*2015-07-06   xun        Created  
*/
package cn.gov.tjp.app.utils;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * 返回页面请求的字符串对象
 * @author: xun
 * @version: 1.0 2015年9月6日
 */
public class StringView implements View {
	
	private String content;
	
	public StringView() {
	}

	public StringView(String content) {
		this.content = content;
	}

	public String getContentType() {
		return "text/plain;charset=UTF-8";
	}

	@SuppressWarnings("unchecked")
	public void render(Map params, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setContentType(getContentType());
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		String str = getContent();
		try {
			out.write(str);
		} finally {
			out.close();
			out = null;
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
