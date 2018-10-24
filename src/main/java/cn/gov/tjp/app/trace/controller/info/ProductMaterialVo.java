package cn.gov.tjp.app.trace.controller.info;


/**
 * 对应界面的产品原料进购信息
 * @author Jfei
 *
 */
public class ProductMaterialVo {

	private String id;
	
	/**
	 * 供应商名称
	 */
	private String supplier;

	/**
	 * 原料名称
	 */
	private String materialName;

	/**
	 * 原料批次
	 */
	private String batch;

	/**
	 * 原料生产厂家
	 */
	private String manufacturer;

	/**
	 * 规格
	 */
	private String specifName;

	/**
	 * 保质期
	 */
	private String qualityPeriod;

	/**
	 * 生产日期yyyy-MM-dd
	 */
	private String produceDate;

	/**
	 * 地址
	 */
	private String place;

	/**
	 * 联系电话
	 */
	private String phoneNo;

	/**
	 * 进货数量
	 */
	private String total;

	/**
	 * 包装单位
	 */
	private String unit;

	/**
	 * 食盐辅料
	 */
	private String saltMaterials;

	/**
	 * 原料检测报告文件名称
	 */
	private String checkReportFileName;

	/**
	 * 原料检测报告文件
	 */
	private String checkReportFile;

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getSpecifName() {
		return specifName;
	}

	public void setSpecifName(String specifName) {
		this.specifName = specifName;
	}

	public String getQualityPeriod() {
		return qualityPeriod;
	}

	public void setQualityPeriod(String qualityPeriod) {
		this.qualityPeriod = qualityPeriod;
	}

	public String getProduceDate() {
		return produceDate;
	}

	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSaltMaterials() {
		return saltMaterials;
	}

	public void setSaltMaterials(String saltMaterials) {
		this.saltMaterials = saltMaterials;
	}

	public String getCheckReportFileName() {
		return checkReportFileName;
	}

	public void setCheckReportFileName(String checkReportFileName) {
		this.checkReportFileName = checkReportFileName;
	}

	public String getCheckReportFile() {
		return checkReportFile;
	}

	public void setCheckReportFile(String checkReportFile) {
		this.checkReportFile = checkReportFile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
