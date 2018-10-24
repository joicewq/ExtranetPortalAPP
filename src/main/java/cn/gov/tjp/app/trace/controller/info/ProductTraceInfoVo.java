package cn.gov.tjp.app.trace.controller.info;
/**
 * 对应页面的生产追溯信息
 * @author Jfei
 *
 */
public class ProductTraceInfoVo {
 
	private String productBarCode;//条形码
	
	private String batch;//产品批次
	
	private String total ;// 产品数量
	
	private String traceCode;// 追溯码
	
	private String packingVal ; //包装
	
	private String checkId;//ProductFinishedCheck的id
	
	private String fileName ;// 检验报告扫描件

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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTraceCode() {
		return traceCode;
	}

	public void setTraceCode(String traceCode) {
		this.traceCode = traceCode;
	}

	public String getPackingVal() {
		return packingVal;
	}

	public void setPackingVal(String packingVal) {
		this.packingVal = packingVal;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	
	
}
