package cn.gov.tjp.app.policies.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

@Controller
@RequestMapping("/policies")
public class PoliciesController {

	private String article_url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPUBLISH")
			+ "/publishContent";
	private String upload_url = EnvUtil.getVal("base_upload_url");
	private static Logger logger = LoggerFactory
			.getLogger(PoliciesController.class);
	
	@RequestMapping("/list")
	public String moreIndex(Model model){
		return "/policies/list";
	}
	
	@RequestMapping("/detail")
	public String showDetailPro(HttpServletRequest request, Model model){
		String id = request.getParameter("id");

		JSONObject json = new JSONObject();
		try {
			String resultStr = HttpUtil.get(article_url +"/query/" + id,"");
			JSONObject objResult = JSON.parseObject(resultStr);
			/*List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))),List.class);
			model.addAllAttributes(resultMap);*/
			Object obj = objResult.get("data");
			Map resultMap = JSON.parseObject(obj.toString(), Map.class);
			model.addAttribute("PoliciesInfo", resultMap);
			model.addAttribute("upload_url",upload_url);
			if (objResult.get("result").equals("1")) {
				json.put("status", 0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/policies/detail";
		
	}
	
}
