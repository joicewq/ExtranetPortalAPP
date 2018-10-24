/**
 * 2016年11月26日 上午11:01:59
 * @auto Jack.Hou
 * @Copyright 1999-2020 http://www.yihecloud.com/ Croporation Limited.
 */
package cn.gov.tjp.app.account.service;

import java.io.IOException;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.common.utils.ResultMessage;
import cn.gov.tjp.app.home.util.Account;
import cn.gov.tjp.app.utils.StringUtil;

import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;


/**
 * @author Administrator
 *
 */
@Service
public class AccountService {
	@Autowired
	private RestTemplate restTemplate;

	private static Logger LOG = LoggerFactory.getLogger(AccountService.class);

	/**
	 * 根据loginName获取用户信息
	 * 
	 * @param loginName
	 * @return
	 * @throws IOException
	 */
	public Account findByLoginInfo(String loginName, String loginId) throws IOException {

		String param = null;
		if (StringUtil.isNotEmpty(loginName)) {
			param = "/byName/" + loginName; // byName 为根据名称查询参数类型
		}
		if (StringUtil.isNotEmpty(loginId)) {
			param = "/byId/" + loginId; // byId 为根据ID查询参数类型
		}
		String vo;
		try {
			vo = HttpUtil.get(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTACCOUNT01") + "/accountService/query" + param, "");
			
			JSONObject voJson = JSONObject.parseObject(vo);
			if ("1".equals(voJson.getString("result"))) {
				Account userVo = JSONObject.parseObject(voJson.getString("data"), Account.class);
				return userVo;
			} else {
				LOG.error("根据loginName从api获取用户数据失败..." + vo);
				throw new RuntimeException("根据loginName从api获取用户数据失败..." + vo);
			}
		} catch (Exception e) {
			LOG.error("根据"+param+"从api获取用户数据,httpClient异常...", e.getMessage());
			throw new IOException(e);
		}
	}


	public Account getByLoginId(String loginId){
		Account account=null;
		String url=EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTACCOUNT01");
		url+="/accountService/query/byId/"+loginId;
		String message=restTemplate.getForObject(url,String.class);
		if(!StringUtils.isEmpty(message) && !message.trim().equals("{}")){
			ResultMessage result= JSON.parseObject(message,ResultMessage.class);
			if (result.getData()!=null){
				account=JSON.parseObject(JSON.toJSONString(result.getData()),Account.class);
			}
		}
		return account;
	}

	public JSONObject getCompany(String loginId, String loginType){
		JSONObject member=null;
		String message="";
		if(!StringUtils.isEmpty(loginType) && loginType.equals("S")){//供应商
			String url= EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01");
			url+="/supplierService/query/byAccount/"+loginId;
			message=restTemplate.getForObject(url,String.class);
		}else if(!StringUtils.isEmpty(loginType) && loginType.equals("P")){//采购商
			String url= EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPROVIDER01");
			url+="/providerService/query/byAccount/"+loginId;
			message=restTemplate.getForObject(url,String.class);
		}
		if(!StringUtils.isEmpty(message) && !message.trim().equals("{}")){
			ResultMessage result=JSON.parseObject(message,ResultMessage.class);
			if(result.getResult()==ResultMessage.Success){
				Object o=result.getData();
				if(o!=null){
					member=JSON.parseObject(JSON.toJSONString(o),JSONObject.class);
				}
			}
		}
		return member;
	}
}
