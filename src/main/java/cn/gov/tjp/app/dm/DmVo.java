package cn.gov.tjp.app.dm;

public class DmVo {

	private String id;
	//资料标题
	private String dTitle;
	//描述
	private String describe;
	//是否提供下载  0是  1否
	private String ifSupplyDown;
	//附件id
	private String docId;
	//下载次数
	private String degree;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getdTitle() {
		return dTitle;
	}
	public void setdTitle(String dTitle) {
		this.dTitle = dTitle;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getIfSupplyDown() {
		return ifSupplyDown;
	}
	public void setIfSupplyDown(String ifSupplyDown) {
		this.ifSupplyDown = ifSupplyDown;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
}
