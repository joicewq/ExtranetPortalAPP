package cn.gov.tjp.app.ga.utils;

/**
 * 存放请求路径类
 */
public class UrlUtil {

	/**
	 * 信息发布API地址
	 */
	public final static String ANNOUNCE_INFORMATION_URL = EnvUtil.getValue("PAASOS_DEPEND_APIGAANNOUNCEMENT");

	/**
	 * 栏目管理API地址
	 */
	public final static String COLUMN_MANAGER_URL = EnvUtil.getValue("PAASOS_DEPEND_APITJPGACOLUMN");

	/**
	 * 友情链接API地址
	 */
	public final static String BLOGROLL_URL = EnvUtil.getValue("PAASOS_DEPEND_APITJPGABLOGROLL");

	/**
	 * 文档服务APP地址
	 */
	public final static String DOC_APP_URL = EnvUtil.getValue("DOC_URL");

	/**
	 * 文档服务API地址
	 */
	public final static String DOC_API_URL = EnvUtil.getValue("PAASOS_DEPEND_APIGWAY0803");

	/**
	 * Solr地址
	 */
	public final static String SOLR_URL = EnvUtil.getValue("SOLR_URL");

	/**
	 * 检查登陆地址
	 */
	public final static String CHECK_LOGIN_IP = EnvUtil.getValue("checkLoginIp");

	/**
	 * 我的通知API地址
	 */
	public final static String MY_SUBSCRIBE_API_URL = EnvUtil.getValue("PAASOS_DEPEND_APIMYSUBSCRIBE");
	/**
	 * 内外宣模板管理
	 */
	public final static String TEMPLTE_API_URL = EnvUtil.getValue("PAASOS_DEPEND_APIPROPAGANDATEMPLETE");
	/**
	 * 调查问卷管理API
	 */
	public final static String QUESTIONNAIRE_API_URL = EnvUtil.getValue("PAASOS_DEPEND_APIQUESTION");
	/**
	 * 外宣Logo图片管理API
	 */
	public final static String LOGOIMGINFO_API_URL = EnvUtil.getValue("PAASOS_DEPEND_APIAPPLICATIONIMG");
}
