require.config({
	baseUrl:"../js",
	paths:{
		"jquery":"../common/jquery-1.8.3.min",
		"Vue":"../common/vue",
		"path":"path",
		"animation":"animation",
		"validator":"../common/vue-validator.min",
		"tab":"tab"
	}
});
require(["jquery","path","Vue","animation","validator","tab"],function($,path,Vue,animation,validator,tab){
	var picPath=path.picPath;
	/**
	 * 供应信息单条详情页面
	 */
	/**
	 * 获取url参数
	 * @returns {Object}
	 */
	var urlOK = false;
	function GetRequest() {
		$("#sell").attr("class","ds-nav-li-active");
		var url = location.search; //获取url中"?"符后的字串 
		var theRequest = new Object(); 
		if (url.indexOf("?") != -1) { 
			var str = url.substr(1),
				strs = str.split("&"); 
			for(var i = 0; i < strs.length; i ++) { 
				theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
			} 
			forEachList(theRequest);
			urlOK = true;
		}
		return theRequest; 
	}
	//GetRequest();
	/*
	 * 查询单条供应信息
	 */
	function forEachList(data){
		if(typeof(data) != 'undefined'){
		data.start = 1;
		data.num = 1 ;
			$.ajax({
				//url:"/sell/getSellList.do",
				url:"../data/sellDetails.json",
				type:"POST",
				contentType: 'application/json',
				data:JSON.stringify(data),	
				dataType: 'json',
				success: function (data) {
					$.each(data.list, function(n, value) {
						sellData = value;
						/*$('#contactUser').html(value.contactUser);
						$('#telephone').html(value.telephone);
						$('#address').html(value.address);
						$('#saleTitle').append(value.saleTitle);
						$('#salePrice').html(value.salePrice+" 元");
						$('#saleQuantity').html(value.saleQuantity);
						$('#quantityUnit').html(value.quantityUnit);
						$('#modifyDate').html(value.modifyDate.substring(0,10));
						$('#endTime').html(value.endTime);
						$('#companyName').html(value.companyName);
						$('#city').html(value.companyArea);*/
/*						//关联企业 TODO :差一个根据ID查询企业信息的操作
						if(value.companyId != null){
							getCompanyById(value.companyId);
						}
						if(value.city){
							queryAreasListForDetail(value.city);
						}*/
						//图片
						if(value.fileUrl){
							$('#fileUrl').attr('src', picPath + value.fileUrl);
						}else{
							$('#fileUrl').attr('src','../images/pic-2.jpg');
						}
						//处理一下标签
						var saleLabels = "";
						/*if(value.saleLabel){
							for (var i = 0; i < value.saleLabel.split(',').length; i++) {
								for (var j = 0; j < tradeLeadsLabelsArrayBackup.length; j++) {
									if(tradeLeadsLabelsArrayBackup[j].grainCode == value.saleLabel.split(',')[i]){
										saleLabels += tradeLeadsLabelsArrayBackup[j].typeName + "，";
										break;
									}
								}
							}
						}*/
						$('#saleLabel').html(saleLabels.substring(0,saleLabels.length-1));
						$('#saleIntroduce').html(value.saleIntroduce);
					}); 
					var goodDetail = new Vue({
						el:"#detail",
						data:{
							items:data.list[0],
							num:1
						},
						mounted:function(){
							animation.destory();
						}
						/*validators:{
							num:function(val){
								return /[0-9]/.test(val);
							}
						},
						methods:{
							handleNum:function(e){
								this.handleValidate(e.target.$validity, 'num')
							},
							handleValidate:function($validity, field){
							  var self = this;
							  $validity.validate(function () {
					              var result = $validity.result
					              self.results[field] = result
					            })
							}
						}*/
					})
					
				},  
			    error : function() {
			    }
			});
		}
	}
	
	$(function(){
		/*animation.pageLoad({});//预留动画过渡，后期优化
		forEachList({});
		tab({
			tabContainer: $(".tab-contentmian"),
			tabTitleWrap: $(".tabs")
		});*/
	})
})