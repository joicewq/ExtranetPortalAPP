package cn.gov.tjp.app.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadUtil {

	public static void download(HttpServletRequest request,HttpServletResponse response,String fileName,byte[] file) throws IOException{
		OutputStream os = null;
		try {
			String ua = request.getHeader("user-agent");
			if (ua != null && ua.indexOf("Firefox") > 0) {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
				response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			} else {
				response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			}
			
			os = response.getOutputStream();
			os.write(file);
			os.flush();
			os.close();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(os != null){
					os.flush();
					os.close();
				}
				
			} catch (IOException e) {
				throw e;
			}
		}
		
	}
}
