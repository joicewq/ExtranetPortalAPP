package cn.gov.tjp.app.ga.utils;


public class QuestionContentVo {
    private String id;

    private String pid;  //所属题目

    private String chooseName;  //选项名称

    private Integer isDelete;  //是否删除

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getChooseName() {
		return chooseName;
	}

	public void setChooseName(String chooseName) {
		this.chooseName = chooseName;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
    
    

}
