package cn.gov.tjp.app.article.controller;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	private String article_url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPUBLISH")
			+ "/publishContent";
	private static Logger logger = LoggerFactory
			.getLogger(ArticleController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request,
			Integer pageNo, Integer pageSize) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// String id = request.getParameter("id");//审核历史
			String columnName = request.getParameter("columnName");// 栏目名称
			String comeSource = "监管平台";// 来源
			String title = request.getParameter("title");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
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
			if (StringUtils.isEmpty(title)) {
				title = "";
			}
			if (StringUtils.isEmpty(startDate)) {
				startDate = "";
			}
			if (StringUtils.isEmpty(endDate)) {
				endDate = "";
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageNo", pageNo);
			params.put("pageSize", pageSize);
			params.put("comeSource", comeSource);
			params.put("columnName", columnName);
			params.put("title", title);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			if (columnName.equals("政策法规")){
				//remark: 0,国家;1:地方
				String remark =  request.getParameter("type");
				if(StringUtils.isEmpty(remark)){
					remark="";
				}
				params.put("remark", remark);
			}
			if (columnName.equals("新闻动态")){
				//remark2: 1,盐业动态;0,盐政执法
				String remark2 =  request.getParameter("type");
				if(StringUtils.isEmpty(remark2)){
					remark2="";
				}
				params.put("remark2", remark2);
			}
		
			String result = "";
			result = HttpUtil.postFormParams(article_url, "/queryPublish", params);
			JSONObject json = JSON.parseObject(result);
			List<JSONObject> arrayResult = JSON.toJavaObject(
					JSON.parseArray(String.valueOf(json.get("data"))),
					List.class);
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
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request,
			Integer pageNo, Integer pageSize) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// String id = request.getParameter("id");//审核历史
			String columnName = request.getParameter("columnName");// 栏目名称
			String title = request.getParameter("title");// 标题
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = 1;
			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = 10;
			}
			if (StringUtils.isEmpty(title)) {
				title = "";
			}
			if (StringUtils.isEmpty(columnName)) {
				columnName = "";
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageNo", pageNo);
			params.put("pageSize", pageSize);
			params.put("title", title);
			params.put("cloumn", columnName);
			String result = "";
			result = HttpUtil.postFormParams(article_url, "/query", params);
			JSONObject json = JSON.parseObject(result);
			List<JSONObject> arrayResult = JSON.toJavaObject(
					JSON.parseArray(String.valueOf(json.get("data"))),
					List.class);
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

}
