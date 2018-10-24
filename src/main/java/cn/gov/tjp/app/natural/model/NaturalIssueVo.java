package cn.gov.tjp.app.natural.model;

public class NaturalIssueVo {

	private String id;
	//食盐资质id集合
	private String nIds;
	//发布标题
	private String issueTitle;
	//发布内容
	private String issueContent;
	//发布时间
	private String createDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getnIds() {
		return nIds;
	}
	public void setnIds(String nIds) {
		this.nIds = nIds;
	}
	public String getIssueTitle() {
		return issueTitle;
	}
	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}
	public String getIssueContent() {
		return issueContent;
	}
	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
