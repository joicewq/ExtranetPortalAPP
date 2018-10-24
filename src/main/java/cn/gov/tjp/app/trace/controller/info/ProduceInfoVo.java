package cn.gov.tjp.app.trace.controller.info;


/**
 * 对应页面的产品信息
 * @author Jfei
 *
 */
public class ProduceInfoVo {

	private String id;
	

	/**
	 * 产品条形码
	 */
	private String productBarCode;

	/**
	 * 生产批次
	 */
	private String batch;

	/**
	 * 生产日期
	 */
	private String produceDate;

	/**
	 * 生产数量
	 */
	private String total;

	/**
	 * 计量单位
	 */
	private String measurementUnit;

	/**
	 * 箱码
	 */
	private String boxCode;

	/**
	 * 追溯码
	 */
	private String traceCode;

	/**
	 * 产品名称
	 */
	private String produceName;

	/**
	 * 产品规格
	 */
	private String spec;
	
	/**
	 * 类别
	 */
	private String category;

	/**
	 * 品牌
	 */
	private String brand;

	/**
	 * 包装
	 */
	private String packingVal;

	/**
	 * 保质期
	 */
	private String overdueVal;

	/**
	 * 二维码数据代码
	 */
	private String qrCode;


	/**
	 * 生产企业名称
	 */
	private String produceEnterprise;

	/**
	 * 生产企业地址
	 */
	private String produceAddress;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductBarCode() {
		return productBarCode;
	}

	public void setProductBarCode(String productBarCode) {
		this.productBarCode = productBarCode;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getProduceDate() {
		return produceDate;
	}

	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public String getTraceCode() {
		return traceCode;
	}

	public void setTraceCode(String traceCode) {
		this.traceCode = traceCode;
	}

	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPackingVal() {
		return packingVal;
	}

	public void setPackingVal(String packingVal) {
		this.packingVal = packingVal;
	}

	public String getOverdueVal() {
		return overdueVal;
	}

	public void setOverdueVal(String overdueVal) {
		this.overdueVal = overdueVal;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getProduceEnterprise() {
		return produceEnterprise;
	}

	public void setProduceEnterprise(String produceEnterprise) {
		this.produceEnterprise = produceEnterprise;
	}

	public String getProduceAddress() {
		return produceAddress;
	}

	public void setProduceAddress(String produceAddress) {
		this.produceAddress = produceAddress;
	}
	
	
}
