package cn.gov.tjp.app.trace.controller.info;

/**
 * 对应界面的销售信息
 * @author Jfei
 *
 */
public class SaleDetailInfoVo {

	/**
	 * 销售单号
	 */
	private String saleNumber;

	/**
	 * 销售日期
	 */
	private String saleDate;

	/**
	 * 购货企业名称
	 */
	private String purchaseEnterpriseName;

	/**
	 * 购货企业许可证编号
	 */
	private String purchaseEnterprisePermitNumber;

	/**
	 * 销售人
	 */
	private String managerName;

	/**
	 * 联系方式
	 */
	private String contact;

	/**
	 * 产品条形码
	 */
	private String productBarCode;
	
	/**
	 * 数量
	 */
	private String total;


	/**
	 * 计量单位
	 */
	private String packUnitName;


	/**
	 * 购货企业所在地区
	 */
	private String purchaseEnterpriseProvince;

	/**
	 * 购货企业所在城市
	 */
	private String purchaseEnterpriseCity;

	/**
	 * 购货企业详细地址
	 */
	private String purchaseEnterpriseAdress;

	public String getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getPurchaseEnterpriseName() {
		return purchaseEnterpriseName;
	}

	public void setPurchaseEnterpriseName(String purchaseEnterpriseName) {
		this.purchaseEnterpriseName = purchaseEnterpriseName;
	}

	public String getPurchaseEnterprisePermitNumber() {
		return purchaseEnterprisePermitNumber;
	}

	public void setPurchaseEnterprisePermitNumber(
			String purchaseEnterprisePermitNumber) {
		this.purchaseEnterprisePermitNumber = purchaseEnterprisePermitNumber;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getProductBarCode() {
		return productBarCode;
	}

	public void setProductBarCode(String productBarCode) {
		this.productBarCode = productBarCode;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPackUnitName() {
		return packUnitName;
	}

	public void setPackUnitName(String packUnitName) {
		this.packUnitName = packUnitName;
	}

	public String getPurchaseEnterpriseProvince() {
		return purchaseEnterpriseProvince;
	}

	public void setPurchaseEnterpriseProvince(String purchaseEnterpriseProvince) {
		this.purchaseEnterpriseProvince = purchaseEnterpriseProvince;
	}

	public String getPurchaseEnterpriseCity() {
		return purchaseEnterpriseCity;
	}

	public void setPurchaseEnterpriseCity(String purchaseEnterpriseCity) {
		this.purchaseEnterpriseCity = purchaseEnterpriseCity;
	}

	public String getPurchaseEnterpriseAdress() {
		return purchaseEnterpriseAdress;
	}

	public void setPurchaseEnterpriseAdress(String purchaseEnterpriseAdress) {
		this.purchaseEnterpriseAdress = purchaseEnterpriseAdress;
	}

}
