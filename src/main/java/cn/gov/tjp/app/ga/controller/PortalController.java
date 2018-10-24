package cn.gov.tjp.app.ga.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.gov.tjp.app.ga.utils.ColumnHashUtil;
import cn.gov.tjp.app.ga.utils.CommonUtil;
import cn.gov.tjp.app.ga.utils.EnvUtil;
import cn.gov.tjp.app.ga.utils.HttpUtil;
import cn.gov.tjp.app.ga.utils.QuestionAnswerDTO;
import cn.gov.tjp.app.ga.utils.UrlUtil;
import cn.gov.tjp.app.ga.utils.VariableUtil;

/**
 * 门户控制类
 */
@RequestMapping(value = "/portal")
@Controller
public class PortalController extends PublicController {
	private final static Logger LOGGER = LoggerFactory.getLogger(PortalController.class);

	/**
	 * 初始化图片模式的检察新闻
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2017年12月20日下午7:12:54
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/initNewsPics")
	public Map<String, Object> initNewsPics() {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			List<String> columnIds = new ArrayList<String>();

			// 配置“检察要闻”ID
			columnIds.add(ColumnHashUtil.getIdByCode("1"));
			params.put("columnIds", columnIds);
			params.put("state", "1");// 已发布

			String result = super.commonInvoke(UrlUtil.ANNOUNCE_INFORMATION_URL + "/contentService/queryPage/1/4", VariableUtil.TYPE_POST, params);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			resultMap.put(VariableUtil.MESSAGE, e.toString());
		}
		return resultMap;
	}

	/**
	 * 轮播
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/roundSowing")
	public Map<String, Object> roundSowing(HttpServletRequest request) {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = CommonUtil.para2Map(request);
			if(!StringUtils.isEmpty(params)){
				List<String> column = queryRoundSowingColumn(params.get("stationId").toString());
				if(column.size()>0){
					params.put("columnIds", column);
				}
				//params.put("stationId", stationId);
				//columnIds.add(ColumnHashUtil.getIdByCode("22"));
			}
			Iterator<String> iter = params.keySet().iterator();
			    while(iter.hasNext()){
			        String key = iter.next();
			        if("stationId".equals(key)){
			            iter.remove();
			        }
			    }
			params.put("state", "1");// 已发布

			String result = super.commonInvoke(UrlUtil.ANNOUNCE_INFORMATION_URL + "/contentService/queryPage/1/3", VariableUtil.TYPE_POST, params);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			resultMap.put(VariableUtil.MESSAGE, e.toString());
		}
		return resultMap;
	}
	
	/**
	 * 查询轮播图片所对应栏目
	 * 
	 * @author wq
	 * 
	 * @date  2018年07月17日上午11:17:08
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryRoundSowingColumn(String stationId){
		Map<String, Object> resultMap = null;

		List<String> columnId = new ArrayList<>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("columnNode", stationId);
			params.put("isPicture", 1);
			params.put("isDisplay", 1);
			//在win下需要下面代码转换
			String osName = System.getProperty("os.name");
			if(osName.toLowerCase().indexOf("windows")>-1){
				params.put("nameLike",new String(ColumnHashUtil.getIdByCode("22").getBytes("ISO-8859-1"),"utf-8"));
			}else{
				params.put("nameLike",ColumnHashUtil.getIdByCode("22"));
			}
			   
			
			String url = UrlUtil.COLUMN_MANAGER_URL + "/columnManger/query";
			String result = super.commonInvoke(url, VariableUtil.TYPE_POST, params);
			if(!StringUtils.isEmpty(result)){				
				JSONObject json = JSON.parseObject(result);
				List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))), List.class);
				for (int i = 0; i < arrayResult.size(); i++) {
					columnId.add(arrayResult.get(i).getString("id"));
					
				}
				
			}else{
				return columnId;
			}
			
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
		}
		return columnId;
	}

	/**
	 * 初始化图片模式媒体报道
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2017年12月21日上午11:17:08
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/initReportsPics")
	public Map<String, Object> initReportsPics() {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			List<String> columnIds = new ArrayList<String>();

			// 媒体报道栏目ID
			columnIds.add(ColumnHashUtil.getIdByCode("11"));
			params.put("columnIds", columnIds);
			params.put("state", "1");// 已发布

			String result = super.commonInvoke(UrlUtil.ANNOUNCE_INFORMATION_URL + "/contentService/queryPage/1/3", VariableUtil.TYPE_POST, params);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			resultMap.put(VariableUtil.MESSAGE, e.toString());
		}
		return resultMap;
	}

	/**
	 * 初始化图片模式领导批示
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2017年12月21日上午11:43:18
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/initInstructionPics")
	public Map<String, Object> initInstructionPics() {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			List<String> columnIds = new ArrayList<String>();

			// 领导批示栏目ID
			columnIds.add(ColumnHashUtil.getIdByCode("4"));
			params.put("columnIds", columnIds);
			params.put("state", "1");// 已发布

			String result = super.commonInvoke(UrlUtil.ANNOUNCE_INFORMATION_URL + "/contentService/queryPage/1/1", VariableUtil.TYPE_POST, params);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			resultMap.put(VariableUtil.MESSAGE, e.toString());
		}
		return resultMap;
	}

	/**
	 * 初始化图片模式摄影欣赏
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2017年12月21日下午2:27:14
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/initPhotographyPics")
	public Map<String, Object> initPhotographyPics() {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			List<String> columnIds = new ArrayList<String>();

			// 摄影欣赏栏目ID
			columnIds.add(ColumnHashUtil.getIdByCode("10"));
			params.put("columnIds", columnIds);
			params.put("state", "1");// 已发布

			String result = super.commonInvoke(UrlUtil.ANNOUNCE_INFORMATION_URL + "/contentService/queryPage/1/2", VariableUtil.TYPE_POST, params);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			resultMap.put(VariableUtil.MESSAGE, e.toString());
		}
		return resultMap;
	}

	/**
	 * 初始化友情链接方法
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2018年1月20日上午11:30:52
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/initBolgroll")
	public Map<String, Object> initBolgroll(HttpServletRequest request) {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = CommonUtil.para2Map(request);
			String result = super.commonInvoke(UrlUtil.BLOGROLL_URL + "/blogrollService/query", VariableUtil.TYPE_POST, params);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			resultMap.put(VariableUtil.MESSAGE, e.toString());
		}
		return resultMap;
	}

	/**
	 * 根据条件查询栏目新闻集合
	 * 
	 * @author : Nansen
	 * @param params
	 * @return
	 * @date : 2017年12月21日下午3:53:50
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/columnNews")
	public Map<String, Object> columnNews(HttpServletRequest request) {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = CommonUtil.para2Map(request);
			params.put("state", "1");// 已发布

			String result = super.commonInvoke(UrlUtil.ANNOUNCE_INFORMATION_URL + "/contentService/query", VariableUtil.TYPE_POST, params);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			resultMap.put(VariableUtil.MESSAGE, e.toString());
		}
		return resultMap;
	}

	/**
	 * 分页栏目新闻更多
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/columnNewsPage")
	public Map<String, Object> columnNewsPage(HttpServletRequest request) {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = CommonUtil.para2Map(request);
			List<String> columnIds = new ArrayList<String>();
			Object columnCode = params.get("columnCode");
			Object columnId = params.get("columnId");

			if (!CommonUtil.isEmpty(columnCode)) {
				columnIds.add(ColumnHashUtil.getIdByCode(columnCode.toString()));
			}
			if (!CommonUtil.isEmpty(columnId)) {
				columnIds.add(columnId.toString());
			}
			params.put("columnIds", columnIds);
			params.put("state", "1");// 已发布

			String url = UrlUtil.ANNOUNCE_INFORMATION_URL + "/contentService/queryPage/" + params.get("pageNo") + "/" + params.get("pageSize");
			String result = super.commonInvoke(url, VariableUtil.TYPE_POST, params);

			resultMap = CommonUtil.OM.readValue(result, Map.class);			
			
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			resultMap.put(VariableUtil.MESSAGE, e.toString());
		}
		return resultMap;
	}

	/**
	 * 外部要闻查询方法
	 * 
	 * @author : Nansen
	 * @param request
	 * @return
	 * @date : 2018年2月2日下午5:07:16
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/extranetColumnNewsPage")
	public Map<String, Object> extranetColumnNewsPage(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(VariableUtil.CODE, VariableUtil.SUCCESS);

		try {
			Map<String, Object> params = CommonUtil.para2Map(request);
			Map<String, Object> queryParams = new HashMap<String, Object>();
			String columnId = (String) (params.get("columnId"));
			StringBuffer columns = new StringBuffer();

			// 根据栏目ID查询对应的栏目路径参数
			if (EnvUtil.getValue("MAJOR_COLUMN_ID").equals(columnId)) {
				columns.append(EnvUtil.getValue("MAJOR_COLUMN_COLUMNS"));
			} else if (EnvUtil.getValue("NEWS_NOTICE_COLUMN_ID").equals(columnId)) {
				columns.append(EnvUtil.getValue("NEWS_NOTICE_COLUMN_COLUMNS"));
			}
			queryParams.put("pageNo", params.get("pageNo"));
			queryParams.put("pageSize", params.get("pageSize"));

			if (!CommonUtil.isEmpty(columns.toString())) {
				queryParams.put("columns", columns.toString().replace("_", "#"));
			}
			String result = super.commonInvoke(UrlUtil.MY_SUBSCRIBE_API_URL + "/outway/1.0/queryPage", VariableUtil.TYPE_FORM, queryParams);
			Map<String, Object> queryResultMap = CommonUtil.OM.readValue(result, Map.class);

			// 整理成自己所用的结构
			resultMap.put(VariableUtil.DATA, queryResultMap.get("list"));
			resultMap.put("totalPage", queryResultMap.get("totalPages"));
			resultMap.put("totalCount", queryResultMap.get("totalCount"));
			resultMap.put("currentPage", params.get("pageNo"));
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			resultMap.put(VariableUtil.MESSAGE, e.toString());
		}
		return resultMap;
	}

	/**
	 * 查询栏目接口方法
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2017年12月1日下午2:14:12
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryColumn")
	public Map<String, Object> queryColumn(HttpServletRequest request) {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> param=new HashMap<String, Object>(); 
			Map<String, Object> params = CommonUtil.para2Map(request);
			List<String> portalTypes = CommonUtil.getListByMap(params, "portalTypes");
			List<String> columnTypes = CommonUtil.getListByMap(params, "columnTypes");

			if (portalTypes.size() > 0) {
				param.put("portalTypes", portalTypes);
			}
			if (columnTypes.size() > 0) {
				param.put("columnTypes", columnTypes);
			}
			//wq：新增查询条件
			if(!CommonUtil.isEmpty(params.get("portalTypes"))){
				param.put("portalType", params.get("portalTypes").toString());
			}
			if(!CommonUtil.isEmpty(params.get("stationId"))){
				param.put("columnNode",params.get("stationId")+"_");
			}
			
			String url = UrlUtil.COLUMN_MANAGER_URL + "/columnManger/query";
			String result = super.commonInvoke(url, VariableUtil.TYPE_POST, param);

			resultMap = CommonUtil.OM.readValue(result, Map.class);
			List<Map<String,Object>> resultColumns = (List<Map<String, Object>>)resultMap.get("data");
			System.out.println(resultColumns.size());
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
		}
		return resultMap;
	}

	/**
	 * 查询栏目树方法
	 * 
	 * @author : Nansen
	 * @param request
	 * @return
	 * @date : 2018年1月30日下午5:10:51
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryColumnTree")
	public Map<String, Object> queryColumnTree(HttpServletRequest request) {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = CommonUtil.para2Map(request);
			String url = UrlUtil.COLUMN_MANAGER_URL + "/columnManger/queryTree";
			String result = super.commonInvoke(url, VariableUtil.TYPE_POST, params);

			resultMap = CommonUtil.OM.readValue(result, Map.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
		}
		return resultMap;
	}

	/**
	 * 根据ID查询详情信息
	 * 
	 * @author : Nansen
	 * @param id
	 * @return
	 * @date : 2017年12月4日下午4:49:31
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryDetail")
	public Map<String, Object> queryDetail(HttpServletRequest request) {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = CommonUtil.para2Map(request);
			String url = UrlUtil.ANNOUNCE_INFORMATION_URL;
			String columnType = (String) (params.get("columnType"));

			// 当栏目类型为2的时候，按照“置顶”、“修改时间”的排序来查询第一个
			if ("2".equals(columnType)) {
				String columnId = (String) (params.get("columnId"));
				params.put("columnIds", Arrays.asList(columnId.split(",")));
				url += "/contentService/queryPage/1/1";
			} else {
				url += "/contentService/query";
			}

			String result = super.commonInvoke(url, VariableUtil.TYPE_POST, params);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) (resultMap.get("data"));

			if (dataList.size() > 0) {
				Map<String, Object> newInfo = dataList.get(0);
				String attachmentIds = (String) (newInfo.get("attachmentIds"));
				List<Map<String, Object>> attachmentInfo = null;

				// 如果有附件ID，去文档服务中查询附件信息，否则只传入一个空的数组
				if (!CommonUtil.isEmpty(attachmentIds)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ids", attachmentIds);

					result = super.commonInvoke(UrlUtil.DOC_API_URL + "/doc/getByIds", VariableUtil.TYPE_FORM, map);
					attachmentInfo = (List<Map<String, Object>>) (CommonUtil.OM.readValue(result, Map.class).get("data"));
				} else {
					attachmentInfo = new ArrayList<Map<String, Object>>();
				}
				newInfo.put("attachmentInfo", attachmentInfo);
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
		}
		return resultMap;
	}

	/**
	 * 返回栏目的hash对照表
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2018年1月8日下午8:18:41
	 */
	@ResponseBody
	@RequestMapping(value = "/getColumnHash")
	public Map<String, Object> getColumnHash() {
		return ColumnHashUtil.getMap();
	}

	/**
	 * 返回一个DocUrl
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2018年1月11日下午4:41:00
	 */
	@ResponseBody
	@RequestMapping(value = "/getDocUrl")
	public String getDocUrl() {
		return UrlUtil.DOC_APP_URL;
	}

	/**
	 * 根据ID查询PDF文档
	 * 
	 * @author : Nansen
	 * @param id
	 * @return
	 * @date : 2017年12月26日下午4:38:05
	 */
	@RequestMapping(value = "/getPDFByID/{id}")
	public void getPDFByID(HttpServletResponse response, @PathVariable(value = "id") String id) {
		InputStream is = null;
		OutputStream os = null;

		try {
			String url = UrlUtil.DOC_APP_URL + "/doc/download/" + id;
			HttpResponse hr = HttpUtil.getEntry(response, url, null);

			is = hr.getEntity().getContent();
			os = response.getOutputStream();
			IOUtils.write(IOUtils.toByteArray(is), os);// 整合流
		} catch (Exception e) {
			LOGGER.error(e.toString());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		}
	}

	/**
	 * 请求环境变量方法
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2018年1月11日下午4:41:00
	 */
	@ResponseBody
	@RequestMapping(value = "/getEnv")
	public Map<String, Object> getEnv() {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put(VariableUtil.CODE, VariableUtil.SUCCESS);
		resultMap.put("docAppUrl", UrlUtil.DOC_APP_URL);
		resultMap.put("checkLoginIp", UrlUtil.CHECK_LOGIN_IP);
		resultMap.put("extranetColumnId", EnvUtil.getValue("EXTRANET_COLUMN_ID"));
		resultMap.put("myBenchAppUrl", EnvUtil.getValue("MY_BENCH_APP_URL"));
		resultMap.put("interiorPortalAppUrl", EnvUtil.getValue("INTERIOR_PORTAL_APP_URL"));
		resultMap.put("onlineService", EnvUtil.getValue("ONLINE_SERVICE"));
		return resultMap;
	}
	
	/**
	 * 请求访问地址对应模板信息
	 * 
	 * @author : Nansen
	 * @return 
	 * @param link(访问地址：如http://www.baidu.com)
	 * @date : 2018年1月11日下午4:41:00
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryTemplete")
	public Map<String, Object> queryTemplete(HttpServletRequest request) {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = CommonUtil.para2Map(request);
			JSONObject jsonString = new JSONObject();
			jsonString.put("testLink", params.get("testLink").toString());
			params.put("JsonString", jsonString);
			String url = UrlUtil.TEMPLTE_API_URL + "/propagandatemplete/query";
			String result = super.commonInvoke(url, VariableUtil.TYPE_FORM, params);

			resultMap = CommonUtil.OM.readValue(result, Map.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
		}
		return resultMap;
	}
	/**
	 * 查询新版栏目接口方法
	 * 
	 * @author : wq
	 * @return
	 * @date : 2018年06月08日下午2:14:12
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryNewColumn")
	public Map<String, Object> queryNewColumn(HttpServletRequest request) {
		Map<String, Object> resultMap = null;

		try {
			Map<String, Object> params = CommonUtil.para2Map(request);
			List<String> portalTypes = CommonUtil.getListByMap(params, "portalTypes");
			List<String> columnTypes = CommonUtil.getListByMap(params, "columnTypes");
			List<String> visualizationColumn = CommonUtil.getListByMap(params, "visualizationColumn");

			if (portalTypes.size() > 0) {
				params.put("portalTypes", portalTypes);
			}
			if (columnTypes.size() > 0) {
				params.put("columnTypes", columnTypes);
			}
			if (visualizationColumn.size() > 0) {
				params.put("visualizationColumn", visualizationColumn);
			}
			if (columnTypes.size() > 0) {
				params.put("columnTypes", columnTypes);
			}
			params.put("pageNo", 1);
			params.put("pageSize", 100);
			params.put("JsonString", JSON.toJSONString(params));
			
			String url = UrlUtil.COLUMN_MANAGER_URL + "/columnManger/queryByStationType";
			String result = super.commonInvoke(url, VariableUtil.TYPE_FORM, params);

			resultMap = CommonUtil.OM.readValue(result, Map.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
		}
		return resultMap;
	}
	/**
	 * 根据站群ID:查调查问卷查询列表
	 * @param stationId(站群ID)
	 * @author : wq
	 * @return
	 * @date : 2018年06月25日下午10:14:12
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/Questionnaire" , method = RequestMethod.POST)
	public  Map<String, Object> queryQuestionnaire(HttpServletRequest request,String stationId) {
		Map<String, Object> resultMap = null;
		try {
		String url = UrlUtil.QUESTIONNAIRE_API_URL + "/survey/pullColumn?columnId="+stationId;
		String result = super.commonInvoke(url,VariableUtil.TYPE_GET,null);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
		}  catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
		}
		return resultMap;
	}
	
	/**
	 * 根据调查问卷ID:查询详情接口
	 * @param id(调查问卷ID)
	 * @author : wq
	 * @return
	 * @date : 2018年06月25日下午14:14:12
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/questionById" , method = RequestMethod.POST)
	public  Map<String, Object> QuestionnaireById(HttpServletRequest request,String id) {
		Map<String, Object> resultMap = null;
		try {
		String url = UrlUtil.QUESTIONNAIRE_API_URL + "/survey/queryAllById?id=" + id;
		String result = super.commonInvoke(url,VariableUtil.TYPE_GET,null);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
		}  catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
		}
		return resultMap;
	}
	/**
	 * 问卷调查答题
	 * @param id(调查问卷ID)
	 * @author : wq
	 * @return
	 * @date : 2018年06月25日下午14:14:12
	 */
	@SuppressWarnings({ "null" })
	@ResponseBody
	@RequestMapping(value = "/saveQuestion" , method = RequestMethod.POST)
	public  Map<String, Object> saveQuestionnaire(HttpServletRequest request,String surveyId) {
		Map<String, Object> resultMap = null;
		Map<String, Object> params = CommonUtil.para2Map(request);
		String question  = request.getParameter("params").toString();
		if(!StringUtils.isEmpty(question)){
			List<QuestionAnswerDTO> questionAnswerDTOList = JSONObject.parseArray(question, QuestionAnswerDTO.class);
			params.put("param", questionAnswerDTOList);			
		}
		try {
			String url = UrlUtil.QUESTIONNAIRE_API_URL + "/recode/lookSave?surveyId=" + surveyId+ "&uid="+null;
			String result = super.commonInvoke(url,VariableUtil.TYPE_POST,params);
			if(result.equals("true")){
				resultMap = new HashMap<String, Object>();
				resultMap.put(VariableUtil.MESSAGE, "保存成功");
				resultMap.put(VariableUtil.CODE, VariableUtil.SUCCESS);
			}else{
				resultMap.put(VariableUtil.MESSAGE, "保存失败");
				resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
			}
		}  catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
		}
		return resultMap;
	}
	/**
	 * 根据站群ID:查询站群LOGO等Footer.vue的信息
	 * @param stationId(站群ID),applicationType(门户类型：1,内宣,2.内宣.3其他)
	 * @author : wq
	 * @return
	 * @date : 2018年07月24日下午10:14:12
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryLogoInfo",method=RequestMethod.POST)
	public  Map<String, Object> queryLogoInfo(HttpServletRequest request) {
		Map<String, Object> resultMap = null;
		try {
			Map<String, Object> params = CommonUtil.para2Map(request);	
			String url = UrlUtil.LOGOIMGINFO_API_URL + "/imgService/isNotExit";
			String result = super.commonInvoke(url,VariableUtil.TYPE_POST,params);
			resultMap = CommonUtil.OM.readValue(result, Map.class);
		}  catch (Exception e) {
			LOGGER.error(e.toString());
			resultMap = new HashMap<String, Object>();
			resultMap.put(VariableUtil.CODE, VariableUtil.ERROR);
		}
		return resultMap;
	}
}
