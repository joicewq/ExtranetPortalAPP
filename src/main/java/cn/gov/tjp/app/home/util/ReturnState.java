/**
 * 2016年11月26日 下午3:36:44
 * @auto Jack.Hou
 * @Copyright 1999-2020 http://www.yihecloud.com/ Croporation Limited.
 */
package cn.gov.tjp.app.home.util;

/**
 * @author Administrator
 * @deprecated 登录返回状态
 */
public class ReturnState {

	public ReturnState() {
		
	}

	String S1 = "1"; // 登录名
	String S2 = "2"; // 密码

	public String getS1() {
		return S1;
	}

	public void setS1(String S1) {
		this.S1 = S1;
	}

	public String getS2() {
		return S2;
	}

	public void setS2(String s2) {
		S2 = s2;
	}

}