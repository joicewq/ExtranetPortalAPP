package cn.gov.tjp.app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

/**
 * 广告管理 ：创建、查询、删除、修改
 */
@Controller
@RequestMapping("/broadcast")
public class AdvertisementController{
	
	private String pdfPath = "";
	/**
	 * 定时删除10分钟前缓存的文件
	 */
	public AdvertisementController() {
		Runnable runnable = new Runnable() {
			public void run() {
				if(!("").equals(pdfPath)){
					File dir = new File(pdfPath);
					File[] lst = dir.listFiles();
					for (File f : lst) {
						if (new Date().getTime() - f.lastModified() > 1 * 60 * 1000 && !("notDel.txt").equals(f.getName())) {
							f.delete();
						}
					}
				}
			}
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(runnable, 30, 10, TimeUnit.SECONDS);
	}

	private static Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/detail")
	public String toDetail(Model model, HttpServletRequest request) {
		Map<String, Object> advert = findById(request.getParameter("id"));
		model.addAttribute("Advert", advert);
		String fileName = new Date().getTime() + ".pdf";
		downloadFile(request, advert.get("advUrl") + "", fileName);
		model.addAttribute("fileName", fileName);
		return "/advert/detail";
	}

	/**
	 * 列表查询
	 * 
	 * @param advert
	 * @return
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			// 执行查询
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("isDelete", 1);// 过滤已删除的数据;0:未删除,1:已删除
			paramMap.put("bizStatus", 0);// 0:监管，1：交易
			paramMap.put("advLocation", 1);// 1:页首,2:页尾
			paramMap.put("isOpen", 0);// 0:开启
			String url = EnvUtil.getVal("PAASOS_DEPEND_APIADVERT") + "/advert/query";
			String jsonStr = HttpUtil.post(url, JSONObject.parseObject(JSONObject.toJSONString(paramMap)));
			json = JSON.parseObject(jsonStr);
		} catch (Exception e) {
			json.put("msg", e.toString());
			json.put("code", 1);
		}
		return json;
	}

	/**
	 * 查询广告详情
	 * 
	 * @param advert
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> findById(String id) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("id", id);
			String url = EnvUtil.getVal("PAASOS_DEPEND_APIADVERT") + "/advert/findById";
			String jsonStr = HttpUtil.get(url, paramMap);

			JSONObject json = JSONObject.parseObject(jsonStr);
			if (json.get("data") != null) {
				dataMap = (Map<String, Object>) json.get("data");
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return dataMap;
	}

	/**
	 * 下载远程文件并保存到本地
	 * 
	 * @param remoteFilePath
	 *            远程文件路径
	 * @param localFilePath
	 *            本地文件路径
	 */
	public void downloadFile(HttpServletRequest request, String id, String fileName) {
		if(("").equals(pdfPath)){
			this.pdfPath = request.getSession().getServletContext().getRealPath("/") + "/static/js/pdf/file/";
		}
		if (id.equals("") || id == null || id.equals("null")) {
			return;
		}
		String remoteFilePath = EnvUtil.getVal("base_upload_url") + "/doc/download/" + id;
		String localFilePath = request.getSession().getServletContext().getRealPath("/") + "/static/js/pdf/file/" + fileName;
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(localFilePath);
		try {
			urlfile = new URL(remoteFilePath);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			bis.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}
	}
}
