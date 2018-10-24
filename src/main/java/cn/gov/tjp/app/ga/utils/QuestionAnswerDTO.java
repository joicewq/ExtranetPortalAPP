package cn.gov.tjp.app.ga.utils;



import java.util.Date;
import java.util.List;

public class QuestionAnswerDTO {
	
    private String id;

    private String createDepartment;  //创建部门

    private String questionType;

    private String questtionName;

    private Integer questionChoose;

    private String questionForm;

    private Date createTime;

    private Integer isdelete;

    private List<QuestionContentVo> questionContentVoList;
    
    private Date finishTime;  //完成问卷的时间
    
    private String questionOption;  //所选中项的名称
    
    private String optionChoose;  //所选中项的id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateDepartment() {
		return createDepartment;
	}

	public void setCreateDepartment(String createDepartment) {
		this.createDepartment = createDepartment;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuesttionName() {
		return questtionName;
	}

	public void setQuesttionName(String questtionName) {
		this.questtionName = questtionName;
	}

	public Integer getQuestionChoose() {
		return questionChoose;
	}

	public void setQuestionChoose(Integer questionChoose) {
		this.questionChoose = questionChoose;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public List<QuestionContentVo> getQuestionContentVoList() {
		return questionContentVoList;
	}

	public void setQuestionContentVoList(List<QuestionContentVo> questionContentVoList) {
		this.questionContentVoList = questionContentVoList;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getQuestionOption() {
		return questionOption;
	}

	public void setQuestionOption(String questionOption) {
		this.questionOption = questionOption;
	}

	public String getOptionChoose() {
		return optionChoose;
	}

	public void setOptionChoose(String optionChoose) {
		this.optionChoose = optionChoose;
	}

	public String getQuestionForm() {
		return questionForm;
	}

	public void setQuestionForm(String questionForm) {
		this.questionForm = questionForm;
	}
    

}
