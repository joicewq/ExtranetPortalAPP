package cn.gov.tjp.app.ga.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.tjp.app.ga.utils.CommonUtil;
import cn.gov.tjp.app.ga.utils.SolrUtil;
import cn.gov.tjp.app.ga.utils.VariableUtil;

/**
 * 外宣门户上的全文检索控制层
 *
 */
@Controller
@RequestMapping(value = "/solr")
public class SolrController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SolrController.class);

	/**
	 * 分页查询全文检索
	 * 
	 * @author : Nansen
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 * @date : 2018年1月15日下午2:34:42
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPage/{pageNo}/{pageSize}")
	public Map<String, Object> queryPage(@PathVariable Integer pageNo, @PathVariable Integer pageSize, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			String field = "id,title,content,cfrom,updateTime,stationId";// 设置的返回字段
			List<String> fieldList = new ArrayList<String>(Arrays.asList(field.split(",")));
			Map<String, Object> params = CommonUtil.para2Map(request);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();// 查询返回集合
			SolrQuery sq = new SolrQuery();

			sq.setQuery("stationId:"+params.get("station"));// 默认查询全部
			sq.setStart((pageNo - 1) * pageSize);// 开始位置
			sq.setRows(pageSize);// 分页大小
			sq.addField(field);// 返回字段

			// 配置请求参数，并接收相关的返回
			Map<String, Object> setMap = this.setParams(sq, params);
			List<String> hlKeyList = new ArrayList<String>(Arrays.asList(((String) (setMap.get("hlKey"))).split(",")));// 高亮字段

			// 获取查询返回对象
			QueryResponse qr = SolrUtil.getDocument(sq);
			SolrDocumentList sdl = qr.getResults();

			Map<String, Map<String, List<String>>> highlightMap = qr.getHighlighting();
			Map<String, List<String>> highlightItem = null;

			// 遍历处理数据
			for (SolrDocument sd : sdl) {
				Map<String, Object> tempMap = new HashMap<String, Object>();
				highlightItem = (Map<String, List<String>>) (highlightMap.get(sd.getFieldValue("id")));

				// 没有高亮的
				for (String key : fieldList) {
					Object fieldValue = sd.getFieldValue(key);

					// 如果内容的长度超过200时，截取成200长度
					if ("content".equals(key) && !CommonUtil.isEmpty(sd.getFieldValue(key))) {
						StringBuffer sb = new StringBuffer((String) (sd.getFieldValue(key)));

						if (sb.length() > 200) {
							sb.delete(200, sb.length());
						}
						fieldValue = sb;
					}
					tempMap.put(key, fieldValue);
				}

				// 高亮的
				for (String key : hlKeyList) {
					if (!CommonUtil.isEmpty(highlightItem.get(key))) {
						tempMap.put(key, highlightItem.get(key));
					}
				}
				dataList.add(tempMap);
			}
			resultMap.put(VariableUtil.CODE, VariableUtil.SUCCESS);
			resultMap.put(VariableUtil.DATA, dataList);// 列表数据
			resultMap.put("sum", sdl.getNumFound());// 总条数
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			resultMap.put(VariableUtil.MESSAGE, e.toString());
		}
		return resultMap;
	}

	/**
	 * 配置solr的查询参数
	 * 
	 * @author : Nansen
	 * @param sq
	 * @param params
	 * @date : 2018年1月22日上午9:30:56
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> setParams(SolrQuery sq, Map<String, Object> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> query = CommonUtil.OM.readValue((String) (params.get("query")), Map.class);
		Integer flag = (Integer) (query.get("flag"));
		StringBuffer hlSB = new StringBuffer();
		StringBuffer querySB = new StringBuffer();
		StringBuffer excludeSB = new StringBuffer();

		if (flag == 1 && !CommonUtil.isEmpty(query.get("simple"))) {// 简单查询，且条件不为空
			List<String> simpleList = Arrays.asList(((String) (query.get("simple"))).trim().split(" "));
			StringBuffer paramSB = new StringBuffer("(");
			String logicalKey = " || ";

			// 当条件不为空时才配置
			if (simpleList.size() > 0) {
				for (String simple : simpleList) {
					paramSB.append(simple).append(logicalKey);
				}
				paramSB.delete(paramSB.lastIndexOf(logicalKey), paramSB.length()).append(")");
				querySB.append("(title:").append(paramSB).append(") || (").append("content:").append(paramSB).append(")");
				hlSB.append("title,content");
				sq.setQuery(querySB.toString());
			}
		} else if (flag == 2 && !CommonUtil.isEmpty(query.get("complex"))) {// 高级查询，且条件不为空
			Map<String, Object> complex = (Map<String, Object>) (query.get("complex"));
			// 包含全部关键词
			List<String> allKeyList = CommonUtil.isEmpty(complex.get("allKey")) ? new ArrayList<String>() : Arrays.asList(((String) (complex.get("allKey"))).trim().split(" "));
			// 完整关键词
			// String completeKey = (String) (complex.get("completeKey"));
			// 任意一个关键词
			List<String> eitherKeyList = CommonUtil.isEmpty(complex.get("eitherKey")) ? new ArrayList<String>() : Arrays.asList(((String) (complex.get("eitherKey"))).trim().split(" "));
			// 不包含关键词
			List<String> excludeKeyList = CommonUtil.isEmpty(complex.get("excludeKey")) ? new ArrayList<String>() : Arrays.asList(((String) (complex.get("excludeKey"))).trim().split(" "));
			// 时间标识
			String updateTimeFlag = (String) (complex.get("updateTimeFlag"));
			// 时间
			List<String> updateTime = CommonUtil.isEmpty(complex.get("updateTime")) ? new ArrayList<String>() : ((List<String>) (complex.get("updateTime")));
			// 文档类型
			List<String> filetypeList = CommonUtil.isEmpty(complex.get("filetype")) ? new ArrayList<String>() : (List<String>) (complex.get("filetype"));
			// 排序
			String sort = (String) (complex.get("sort"));
			// 关键词位置
			String keyLocation = (String) (complex.get("keyLocation"));

			// 条件字段集合
			List<String> keyList = new ArrayList<String>();

			// 关键词位置的不同作不同的条件处理
			switch (keyLocation) {
			case "1": {
				keyList.add("title");
				keyList.add("content");
				hlSB.append("title,content");
				break;
			}
			case "2": {
				keyList.add("title");
				hlSB.append("title");
				break;
			}
			case "3": {
				keyList.add("content");
				hlSB.append("content");
				break;
			}
			case "4": {
				keyList.add("cfrom");
				hlSB.append("cfrom");
				break;
			}
			default: {
				break;
			}
			}
			this.setKeyToSolrQuery(querySB, keyList, allKeyList, "", "&&");// 包含全部关键词
			this.setKeyToSolrQuery(querySB, keyList, eitherKeyList, "", "||");// 任意一个关键词
			this.setKeyToSolrQuery(excludeSB, keyList, excludeKeyList, "-", "||");// 不包含关键词

			// 加上查询条件
			if (!CommonUtil.isEmpty(querySB.toString())) {
				querySB.delete(querySB.lastIndexOf(" && "), querySB.length());
				sq.setQuery(querySB.toString());
			}

			// 加上过滤条件
			if (!CommonUtil.isEmpty(excludeSB.toString())) {
				excludeSB.delete(excludeSB.lastIndexOf(" && "), excludeSB.length());
				sq.addFilterQuery(excludeSB.toString());
			}

			// 处理文档类型
			if (filetypeList.size() > 0) {
				StringBuffer filterQuerySB = new StringBuffer("filetype:(");

				for (String filetype : filetypeList) {
					filterQuerySB.append(filetype).append(" || ");
				}

				// 去掉后面空格的逻辑符号
				filterQuerySB.delete(filterQuerySB.lastIndexOf(" || "), filterQuerySB.length());
				filterQuerySB.append(")");

				// 添加到SolrQuery中
				sq.addFilterQuery(filterQuerySB.toString());
			}

			// 设置排序
			if ("2".equals(sort)) {
				sq.addSort("updateTime", ORDER.desc);
			}

			// 配置时间
			this.setUpdateTimeToSolrQuery(sq, updateTimeFlag, updateTime);
		}

		// 设置高亮字段
		sq.setHighlight(true);
		sq.setHighlightSimplePre("<span class='highlight'>");
		sq.setHighlightSimplePost("</span>");
		sq.setParam("hl.fl", hlSB.toString());

		resultMap.put("hlKey", hlSB.toString());// 高亮字段
		return resultMap;
	}

	/**
	 * 统一设置条件方法
	 * 
	 * @author : Nansen
	 * @param querySB
	 * @param keyList
	 * @param notKey
	 * @param logicalKey
	 * @date : 2018年1月22日下午8:18:28
	 */
	private void setKeyToSolrQuery(StringBuffer querySB, List<String> keyList, List<String> paramList, String notKey, String logicalKey) {
		if (paramList.size() > 0) {
			StringBuffer keySB = new StringBuffer();
			StringBuffer paramSB = new StringBuffer("(");
			StringBuffer logicalSB = new StringBuffer(" ").append(logicalKey).append(" ");// 逻辑符号

			// 把条件参数组装起来
			for (String param : paramList) {
				paramSB.append(param).append(logicalSB);
			}
			paramSB.delete(paramSB.lastIndexOf(logicalSB.toString()), paramSB.length());// 删除后面的逻辑符号
			paramSB.append(")");

			// 遍历字段，并添加相应的条件
			for (String key : keyList) {
				keySB.append(notKey).append(key).append(":").append(paramSB);
				keySB.append(" || ");
			}
			keySB.delete(keySB.lastIndexOf(" || "), keySB.length());
			querySB.append("(").append(keySB).append(") && ");
		}
	}

	/**
	 * 设置时间段
	 * 
	 * @author : Nansen
	 * @param sq
	 * @param flag
	 * @param updateTime
	 * @date : 2018年1月23日上午10:30:06
	 */
	private void setUpdateTimeToSolrQuery(SolrQuery sq, String flag, List<String> updateTime) {
		Calendar c = Calendar.getInstance();
		String start = "*"; // 默认开始时间是任何时间

		// 默认开始时间是今天最后1秒
		this.setTime(c, "2");
		String end = CommonUtil.sdf_YMDTHmsZ.format(c.getTime());

		switch (flag) {
		case "1": {// 任何时间
			break;
		}
		case "2": {// 最近一天
			c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 1);

			this.setTime(c, "1");
			start = CommonUtil.sdf_YMDTHmsZ.format(c.getTime());
			break;
		}
		case "3": {// 最近一周
			c.set(Calendar.WEEK_OF_YEAR, c.get(Calendar.WEEK_OF_YEAR) - 1);

			this.setTime(c, "1");
			start = CommonUtil.sdf_YMDTHmsZ.format(c.getTime());
			break;
		}
		case "4": {// 最近一月
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);

			this.setTime(c, "1");
			start = CommonUtil.sdf_YMDTHmsZ.format(c.getTime());
			break;
		}
		case "5": {// 最近一年
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);

			this.setTime(c, "1");
			start = CommonUtil.sdf_YMDTHmsZ.format(c.getTime());
			break;
		}
		case "6": {
			try {
				if (updateTime.size() > 1) {// 如果只是选择了自定义而没有输入时间，默认全搜索
					c.setTime(CommonUtil.sdf_yMd.parse(updateTime.get(0)));

					this.setTime(c, "1");
					start = CommonUtil.sdf_YMDTHmsZ.format(c.getTime());

					this.setTime(c, "2");
					end = CommonUtil.sdf_YMDTHmsZ.format(c.getTime());
				} else {
					end = "*";
				}
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			break;
		}
		default: {
			break;
		}
		}
		sq.addFilterQuery("updateTime:[" + start + " TO " + end + "]");
	}

	/**
	 * 设置时间方法，用户配置设置"00:00:00"和"23:59:59"
	 * 
	 * @author : Nansen
	 * @param c
	 * @param flag
	 * @return
	 * @date : 2018年3月2日下午5:19:44
	 */
	private void setTime(Calendar c, String flag) {
		switch (flag) {
		case "1": {
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			break;
		}
		case "2": {
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
		}
		default: {
			break;
		}
		}
	}
}
