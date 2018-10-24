package cn.gov.tjp.app.ga.timer.job;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gov.tjp.app.ga.utils.CommonUtil;
import cn.gov.tjp.app.ga.utils.EnvUtil;
import cn.gov.tjp.app.ga.utils.HttpUtil;
import cn.gov.tjp.app.ga.utils.SolrUtil;

/**
 * 扫描外部新闻任务
 *
 */
public class ScanExternalNewsJob implements Job {
	private final static Logger LOGGER = LoggerFactory.getLogger(ScanExternalNewsJob.class);

	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Calendar c = Calendar.getInstance();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", EnvUtil.getValue("EXTRANET_COLUMN_IDS"));

		// 前一天
		c.add(Calendar.DATE, -1);
		params.put("date1", CommonUtil.sdf_YMd.format(c.getTime()) + " 00:00:00");// 开始时间
		params.put("date2", CommonUtil.sdf_YMd.format(c.getTime()) + " 23:59:59");// 结束时间

		try {
			String result = HttpUtil.postFormData(EnvUtil.getValue("PA_CHONG_APP_URL"), params);
			Map<String, Object> resultMap = CommonUtil.OM.readValue(result, Map.class);
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) (resultMap.get("list"));

			// 循环写入SOLR
			Map<String, Object> solrMap = new HashMap<String, Object>();
			for (Map<String, Object> dataItem : dataList) {
				solrMap.clear();
				solrMap.put("id", dataItem.get("id"));
				solrMap.put("title", dataItem.get("title"));
				solrMap.put("content", dataItem.get("text"));
				solrMap.put("updateTime", dataItem.get("date"));

				// 写入
				SolrUtil.addDocument(solrMap);
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
}
