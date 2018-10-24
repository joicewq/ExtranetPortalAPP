package cn.gov.tjp.app.ga.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接连solr小工具
 */
public class SolrUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(SolrUtil.class);
	private final static String SOLR_URL = UrlUtil.SOLR_URL + "/solr/";// solr服务地址
	private final static String DATA_BASE_KEY = "portal";// solr中的分库名称

	/**
	 * 根据查询的条件查询出solr中的数据
	 * 
	 * @author : Nansen
	 * @param sq
	 * @return
	 * @date : 2018年1月18日上午9:43:54
	 */
	public final static QueryResponse getDocument(SolrQuery sq) {
		HttpSolrClient hsc = null;
		QueryResponse qr = null;

		try {
			hsc = getSolrClient();
			qr = hsc.query(sq);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		} finally {
			try {
				if (hsc != null) {
					hsc.close();
				}
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		}
		return qr;
	}

	/**
	 * 添加一个文档<br>
	 * 如果ID是一样的，可以直接用该方法覆盖之前的数据
	 * 
	 * @author : Nansen
	 * @param params
	 * @throws Exception
	 * @date : 2017年12月4日下午2:10:33
	 */
	public final static void addDocument(Map<String, Object> params) {
		HttpSolrClient hsc = null;

		try {
			SolrInputDocument sid = new SolrInputDocument();

			// 遍历参数
			for (Entry<String, Object> param : params.entrySet()) {
				sid.addField(param.getKey(), param.getValue());
			}
			hsc = getSolrClient();
			hsc.add(sid);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		} finally {
			if (hsc != null) {
				try {
					hsc.commit();
					hsc.close();
				} catch (Exception e) {
					LOGGER.error(e.toString());
				}
			}
		}
	}

	/**
	 * 根据ID删除文档
	 * 
	 * @author : Nansen
	 * @param id
	 *            :主键
	 * @date : 2018年1月15日上午11:25:26
	 */
	public final static void deleteDocumentById(String id) {
		HttpSolrClient hsc = null;

		try {
			if (id == null || "".equals(id)) {
				throw new RuntimeException("id为空");
			} else {
				hsc = getSolrClient();
				hsc.deleteById(id);
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		} finally {
			try {
				if (hsc != null) {
					hsc.commit();
					hsc.close();
				}
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		}
	}

	/**
	 * 根据ID集合删除文档
	 * 
	 * @author : Nansen
	 * @param id
	 *            :主键
	 * @date : 2018年1月15日上午11:25:26
	 */
	public final static void deleteDocumentById(List<String> ids) {
		HttpSolrClient hsc = null;

		try {
			if (ids.size() > 0) {
				hsc = getSolrClient();
				hsc.deleteById(ids);
			} else {
				throw new RuntimeException("ids为空");
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		} finally {
			try {
				if (hsc != null) {
					hsc.close();
				}
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		}
	}

	/**
	 * 新建一个SOLR链接
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2017年11月17日下午5:26:29
	 */
	private static synchronized HttpSolrClient getSolrClient() {
		return new HttpSolrClient(SOLR_URL + DATA_BASE_KEY);
	}
}