package cn.gov.tjp.app.common.utils;


/**
 * 消息处理
  * @author liub
  * @version 1.0
  * @date 2016-3-17
  * Copyright 青海粮食云项目组
 */
public class ResultMessage {
	/**
	 * 成功
	 */
	public static final int Success = 1;

	/**
	 * 失败
	 */
	public static final int Fail = 0;
   
	/**
	 * 登录超时
	 */
	public static final int Login = 2;

	private int result = Success;
	private Object message = null;
	private Object data =null;
	
	public ResultMessage() {
	}
	/**
	 * 赋值对象(用于成功赋值对象/集合等)
	 * @param message
	 */
	public ResultMessage(Object message) {
        this.message = message;
    }
	/**
	 * 赋值
	 * @param result  成功/失败
	 * @param message 集合对象/异常信息
	 */
	public ResultMessage(int result, Object message) {
		this.result = result;
		this.message = message;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
