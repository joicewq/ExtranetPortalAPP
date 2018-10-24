package cn.gov.tjp.app.ga.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gov.tjp.app.ga.utils.HttpUtil;
import cn.gov.tjp.app.ga.utils.VariableUtil;

/**
 * 封装公用的Controller的类
 */
public class PublicController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicController.class);

	/**
	 * 通用的请求方法，主要是请求到API上
	 * 
	 * @author : Nansen
	 * @param url
	 *            :请求地址
	 * @param methodType
	 *            :请求方法
	 * @param params
	 *            :请求参数，如果是"get"请求，可以输入"null"，因其在"get"中是没有用到的
	 * @return
	 * @date : 2017年10月18日上午10:51:54
	 */
	public String commonInvoke(String url, String methodType, Map<String, Object> params) {
		String result = null;

		try {
			if (VariableUtil.TYPE_GET.equalsIgnoreCase(methodType)) {
				result = HttpUtil.get(url);
			} else if (VariableUtil.TYPE_POST.equalsIgnoreCase(methodType)) {
				result = HttpUtil.post(url, params);
			} else if (VariableUtil.TYPE_FORM.equalsIgnoreCase(methodType)) {
				result = HttpUtil.postFormData(url, params);
			}
		} catch (Exception e) {
			result = e.toString();
			LOGGER.error(e.toString());
		}
		return result;
	}
}
