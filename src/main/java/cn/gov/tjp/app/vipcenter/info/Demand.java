/**
 * 2016年11月28日 下午2:45:08
 * @auto Jack.Hou
 * @Copyright 1999-2020 http://www.yihecloud.com/ Croporation Limited.
 */
package cn.gov.tjp.app.vipcenter.info;

/**
 * @author Administrator
 * @category 发布需求表
 */
public class Demand {

	String id;// '需求ID',
	String productName;// '产品名称',
	String productSpec;// '产品规格',
	String productNum;// '产品数量',
	String productPrice;// '产品单价',
	String productPlace;// 产地,
	String logisticsMode;// '物流方式（0-京东1-顺丰。。。）',
	String tradeAdress;// '交割地/交易地址'
	String contactPhone;// '联系方式'
	String deadtime;// '截止时间'
	String reserve1;// 备用1
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductSpec() {
		return productSpec;
	}
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductPlace() {
		return productPlace;
	}
	public void setProductPlace(String productPlace) {
		this.productPlace = productPlace;
	}
	public String getLogisticsMode() {
		return logisticsMode;
	}
	public void setLogisticsMode(String logisticsMode) {
		this.logisticsMode = logisticsMode;
	}
	public String getTradeAdress() {
		return tradeAdress;
	}
	public void setTradeAdress(String tradeAdress) {
		this.tradeAdress = tradeAdress;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getDeadtime() {
		return deadtime;
	}
	public void setDeadtime(String deadtime) {
		this.deadtime = deadtime;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	
	
}
