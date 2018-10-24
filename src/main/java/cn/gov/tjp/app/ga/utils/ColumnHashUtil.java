package cn.gov.tjp.app.ga.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用CODE来对应栏目ID的工具
 */
public class ColumnHashUtil {
	private static Map<String, Object> COLUMN_CODE_ID = new HashMap<String, Object>();// 页面上code对应的ID

	// 初始化
	static {
		COLUMN_CODE_ID.put("1", EnvUtil.getValue("PROCURATOR_NEWS_COLUMN_ID"));// 检察要闻
		COLUMN_CODE_ID.put("2", EnvUtil.getValue("INFORMATIONS_COLUMN_ID"));// 通知公告
		COLUMN_CODE_ID.put("3", EnvUtil.getValue("LEADER_SPEAKING_COLUMN_ID"));// 领导讲话
		COLUMN_CODE_ID.put("4", EnvUtil.getValue("LEADER_INDICATE_COLUMN_ID"));// 领导批示
		COLUMN_CODE_ID.put("5", EnvUtil.getValue("PROCURATORATE_PROPAGATE_COLUMN_ID"));// 检察宣传
		COLUMN_CODE_ID.put("6", EnvUtil.getValue("PROCURATORATE_FORUM_COLUMN_ID"));// 检察论坛
		COLUMN_CODE_ID.put("7", EnvUtil.getValue("CULTURAL_ACTIVITY_COLUMN_ID"));// 文体活动
		COLUMN_CODE_ID.put("8", EnvUtil.getValue("PAINTION_COLUMN_ID"));// 书画长廊
		COLUMN_CODE_ID.put("9", EnvUtil.getValue("LITERATURE_COLUMN_ID"));// 文学随笔
		COLUMN_CODE_ID.put("10", EnvUtil.getValue("PHOTOGRAPHY_COLUMN_ID"));// 摄影欣赏
		COLUMN_CODE_ID.put("11", EnvUtil.getValue("MEDIA_COVERAGE_COLUMN_ID"));// 媒体报道
		COLUMN_CODE_ID.put("12", EnvUtil.getValue("DEPARTMENT_PARTY_COLUMN_ID"));// 机关党建
		COLUMN_CODE_ID.put("13", EnvUtil.getValue("INCORRUPT_BUILD_COLUMN_ID"));// 廉政建设
		COLUMN_CODE_ID.put("14", EnvUtil.getValue("APP_LINK_COLUMN_ID"));// 应用链接
		COLUMN_CODE_ID.put("15", EnvUtil.getValue("PICTURE_NEWS_COLUMN_ID"));// 图片新闻
		COLUMN_CODE_ID.put("16", EnvUtil.getValue("LEADERSHIP_COLUMN_ID"));// 院级领导
		COLUMN_CODE_ID.put("17", EnvUtil.getValue("ORGaNiZATION_COLUMN_ID"));// 机构设置
		COLUMN_CODE_ID.put("18", EnvUtil.getValue("POLICY_DOCUMENT_COLUMN_ID"));// 政策文件
		COLUMN_CODE_ID.put("19", EnvUtil.getValue("LEGAL_DOCUMENT_COLUMN_ID"));// 法律文件
		COLUMN_CODE_ID.put("20", EnvUtil.getValue("WORK_DOCUMNET_COLUMN_ID"));// 工作文件
		COLUMN_CODE_ID.put("21", EnvUtil.getValue("WORK_BRIEFING_COLUMN_ID"));// 工作简报
		COLUMN_CODE_ID.put("22", EnvUtil.getValue("PICTURE_NEWS_COLUMN_NAME"));// 图片新闻
	}

	/**
	 * 返回CODE对应栏目ID的hash表
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2018年1月8日下午8:14:43
	 */
	public final static Map<String, Object> getMap() {
		return COLUMN_CODE_ID;
	}

	/**
	 * 根据Code返回对应的ID
	 * 
	 * @author : Nansen
	 * @param code
	 * @return
	 * @date : 2018年1月8日下午8:16:33
	 */
	public final static String getIdByCode(String code) {
		return (String) (COLUMN_CODE_ID.get(code));
	}
}
