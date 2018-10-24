/**
 * 2016年11月25日 下午5:12:26
 * @auto Jack.Hou
 * @Copyright 1999-2020 http://www.yihecloud.com/ Croporation Limited.
 */
package cn.gov.tjp.app.home.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.pubframework.common.vo.RestVo;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.prodlib.service.ProdlibService;
import cn.gov.tjp.app.utils.ControllerUtil;
import cn.gov.tjp.app.utils.StringUtil;

/**
 * @author Administrator 首页
 */
@Controller
@RequestMapping("/salt")
public class HomeController {

	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProdlibService prodlibService;

	private String article_url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPUBLISH") + "/publishContent";
	private String upload_url = EnvUtil.getVal("base_upload_url");
	private String accessamount_url = EnvUtil.getVal("PAASOS_DEPEND_APIACCESSAMOUNT")+"/accessamount";

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,Model model) {
		try {
			JSONObject info=new JSONObject();
			String ipAddress=ControllerUtil.getIpAddr(request);
			info.put("ipAddress", ipAddress);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("info", info);
			String result=HttpUtil.postFormParams(accessamount_url, "/add", params);
			JSONObject json = JSON.parseObject(result);
			String accessNum=json.getString("exceptionMsg");
			request.setAttribute("trade_url", EnvUtil.getVal("trade_url"));
			request.getSession().setAttribute("accessNum", accessNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("upload_url", upload_url);
		return "/home/home";
	}

	/**
	 * 搜索
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/serach")
	public String search(HttpServletRequest request, String keyName) {
		request.setAttribute("keyName", keyName);
		request.setAttribute("title", keyName);
		return "/home/search/list";
	}

	/**
	 * 搜索列表
	 * 
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, Integer pageNo, Integer pageSize, String startDTime, String endDTime, String title) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String columnName = request.getParameter("columnName");// 栏目名称
			String comeSource = "监管平台";// 标题
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = 1;
			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = 10;
			}
			if (StringUtils.isEmpty(comeSource)) {
				comeSource = "";
			}
			if (StringUtils.isEmpty(columnName)) {
				columnName = "";
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageNo", pageNo);
			params.put("pageSize", pageSize);
			params.put("comeSource", comeSource);
			if (StringUtil.isNotEmpty(startDTime)) {
				params.put("startDate", startDTime);
			}
			if (StringUtil.isNotEmpty(endDTime)) {
				params.put("endDate", endDTime);
			}
			if (StringUtil.isNotEmpty(title)) {
				params.put("title", title);
			} 
			String result = HttpUtil.postFormParams(article_url, "/queryPublish", params);
			JSONObject json = JSON.parseObject(result);
			@SuppressWarnings("unchecked")
			List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))), List.class);
			resultMap.put("data", arrayResult);
			// 总记录数
			resultMap.put("totalRow", json.get("totalRow"));
			// 总页数
			resultMap.put("totalPage", json.get("totalPage"));
			// 当前页数
			resultMap.put("curPage", json.get("curPage"));
			// 每页记录数
			resultMap.put("pageLine", json.get("pageLine"));

		} catch (Exception e) {
			logger.error("查询出现异常...." + e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 搜索详情
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String queryDetail(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");

		JSONObject json = new JSONObject();
		try {
			String resultStr = HttpUtil.get(article_url + "/query/" + id, "");
			JSONObject objResult = JSON.parseObject(resultStr);
			Object obj = objResult.get("data");
			@SuppressWarnings("unchecked")
			Map<String, String> resultMap = JSON.parseObject(obj.toString(), Map.class);
			model.addAttribute("Notice", resultMap);
			if (objResult.get("result").equals("1")) {
				json.put("status", 0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/home/search/detail";

	}

}
