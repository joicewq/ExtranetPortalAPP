;define(["jquery"],function($){
	var himPath=getPath('ECOM_HIM');//首页管理APP
	var umPath=getPath('ECOM_UM');//用户管理APP
	var cimPath=getPath('ECOM_CIM');//企业管理APP
	var sdimPath=getPath('ECOM_SDIM');//供求管理APP
	//var picPath=getPath('ECOM_PIC');//文件服务
	var picPath="../images/"
	
	/**
	 * 获取APP的IP地址
	 * @param pathCode
	 * @returns {String}
	 */
	function getPath(pathCode){
		var result="";
		$.ajaxSetup({
			async:false
		});
		$.ajax({
			url: '/resourceController/getPath.do', //url  action是方法的名称
			type: 'GET',
			data:{'pathCode':pathCode},
			dataType: "json", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
			//contentType: "application/json; charset=utf-8",
			success: function(data) {
				result = data;
			},
			error: function(msg) {
				console.error("操作失败!");
			}
		});
		$.ajaxSetup({
			async:true
		});
		return result;
	}
	
	return {
		himPath:himPath,
		umPath:umPath,
		cimPath:cimPath,
		sdimPath:sdimPath,
		picPath:picPath
	}
})