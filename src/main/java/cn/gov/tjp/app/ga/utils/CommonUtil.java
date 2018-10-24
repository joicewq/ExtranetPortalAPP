package cn.gov.tjp.app.ga.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 常用工具方法
 * 
 * @author Nansen
 *
 */
public class CommonUtil {
	/**
	 * 格式时间工具"YYYY-MM-dd"
	 */
	public final static SimpleDateFormat sdf_YMd = new SimpleDateFormat(VariableUtil.YMd);

	/**
	 * 格式时间工具"yyyy-MM-dd"
	 */
	public final static SimpleDateFormat sdf_yMd = new SimpleDateFormat(VariableUtil.yMd);

	/**
	 * 格式时间工具"YYYY-MM-dd HH:mm:ss"
	 */
	public final static SimpleDateFormat sdf_YMdHms = new SimpleDateFormat(VariableUtil.YMd_Hms);

	/**
	 * 格式时间工具YYYY-MM-dd'T'HH:mm:ss'Z'
	 */
	public final static SimpleDateFormat sdf_YMDTHmsZ = new SimpleDateFormat(VariableUtil.YMd_T_Hms_Z);

	/**
	 * 处理JSON工具
	 */
	public final static ObjectMapper OM = new ObjectMapper();

	/**
	 * 判断一个"String"对象是否为"null"或""
	 */
	public final static boolean isEmpty(Object value) {
		boolean flag = false;

		if (value == null || "".equals(value)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 生成系统当前时间
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2017年9月25日上午11:03:21
	 */
	public final static String getNow(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * 生成系统当前时间
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2017年9月25日上午11:03:21
	 */
	public final static String getNow() {
		return getNow(VariableUtil.YMd_Hms);
	}

	/**
	 * 把request中的参数提取到map中的方法
	 * 
	 * @date : 2017年12月15日上午11:09:11
	 */
	public static Map<String, Object> para2Map(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String[]> reqMap = request.getParameterMap();

		for (Map.Entry<String, String[]> m : reqMap.entrySet()) {
			paramMap.put(m.getKey(), m.getValue()[0]);
		}
		return paramMap;
	}

	/**
	 * 把存放在Map中分散的List提取出来，并删除Map中的相应数据
	 * 
	 * @author : Nansen
	 * @param key
	 * @return
	 * @date : 2018年1月9日下午4:38:40
	 */
	public static List<String> getListByMap(Map<String, Object> map, String key) {
		List<String> list = new ArrayList<String>();
		int index = 0;
		String listKey = null;

		while (true) {
			listKey = key + "[" + index + "]";

			// 如果没有该值，则跳出
			if (isEmpty(map.get(listKey))) {
				break;
			}
			list.add((String) (map.get(listKey)));// 把参数存放到返回数组
			map.remove(listKey);// 删除相应的值
			index++;
		}
		return list;
	}
}
