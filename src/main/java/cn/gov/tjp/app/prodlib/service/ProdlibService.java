/**
 * 2016年11月28日 下午5:32:38
 * @auto Jack.Hou
 * @Copyright 1999-2020 http://www.yihecloud.com/ Croporation Limited.
 */
package cn.gov.tjp.app.prodlib.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

/**
 * @author Administrator
 *
 */
@Service
public class ProdlibService { 
	
	private static Logger LOG = LoggerFactory.getLogger(ProdlibService.class);
	
	/**
	 * 查询供货信息
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryProdLib(String id,String companyId,String name,String spec, Integer pageNo, Integer pageSize){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
		 
			String start = null;
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = 1;
			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = 10;
			}
			start = String.valueOf((pageNo - 1) * pageSize);
			Map<String, Object> map = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(id)) {
				map.put("id", id);
			}
			if (!StringUtils.isEmpty(companyId)) {
				map.put("companyId", companyId);
			}
			if (!StringUtils.isEmpty(name)) {
				map.put("name", name);
			}
			if (!StringUtils.isEmpty(spec)) {
				map.put("spec", spec);
			}
			String queryInfo = JSON.toJSONString(map);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("queryInfo", queryInfo);
			params.put("start", start);
			params.put("size", pageSize);
			params.put("sta", 4);		//产品状态：0：未提交；1：审核中；2：已通过；3：不通过；4：挂牌；5：停牌；6：已完成
			String result = "";
			result = HttpUtil.postFormParams(EnvUtil.getVal("PRODLIB_URL")+"/prodlibService", "/query", params);
			JSONObject json = JSON.parseObject(result);
			Map<String, Object> dataMap = (Map<String, Object>) json;
			// 总记录数
			Integer count = (Integer) dataMap.get("total");
			resultMap.put("totalRow", count);
			// 总页数
			resultMap.put("totalPage", (count % pageSize == 0) ? count / pageSize : count / pageSize + 1);
			// 当前页数
			resultMap.put("curPage", pageNo);
			// 每页记录数
			resultMap.put("pageLine", pageSize);
			resultMap.put("data", dataMap.get("message"));
			
		} catch (Exception e) {
			LOG.error("查询出现异常...." + e.getMessage());
		}
		return resultMap;
	}
}
