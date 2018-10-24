/**
 * 2016年11月26日 上午11:40:01
 * @auto Jack.Hou
 * @Copyright 1999-2020 http://www.yihecloud.com/ Croporation Limited.
 */
package cn.gov.tjp.app.home.util;

/**
 * @author Administrator
 *
 */
public class Account {

	private String loginId;

	private String loginName;

	private String loginPassword;

	private String loginType;

	private Integer bizStatus;

	private Integer isDelete;
	
	private String phone;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Integer getBizStatus() {
		return bizStatus;
	}

	public void setBizStatus(Integer bizStatus) {
		this.bizStatus = bizStatus;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
