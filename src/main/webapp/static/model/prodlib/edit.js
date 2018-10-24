require.config(config);
require(["jquery","validate","local","animation","underscore","layer"],
		function($,validate,local,animation,_,layer){

	function save(){
		var flag = true;
		$(".form-list [name]").each(function() {
			if(!checkForm($(this))){
				$(this).focus();
				flag = false;
				return false;
			}
		});
		if(flag){
			debugger
			base.processStatus(1,'save','process_btn');
			var name = $("#name").val();		//名称
			var spec = $("#spec").val();		//规格
			var grade = $("#grade").val();		//品质等级
			var stdNum = $("#stdNum").val();		//产品标准号
			var licenseNum = $("#licenseNum").val();	//食盐定点生产证号
			var producePlace = $("#producePlace").val();//产地
			var valtDateFrom = $("#valtDateFrom").val();	//有效期（从）
			var valtDateTo = $("#valtDateTo").val();	//有效期（到）
			var mixIngt = $("#mixIngt").val();		//配料
			var keep = $("#keep").val();		//贮存
			var nutrComp = $("#nutrComp").val();	//营养成分
			var antiFake = $("#antiFake").val();	//防伪识别方法
			var introduce = $("#introduce").val();	//产品简介
			
			var number = $("#number").val();		//数量
			var price = $("#price").val();		//价格
			var deliveryPlace = $("#deliveryPlace").val();	//交割地
			
			var appearIds = getFileIds(1).join(',');	//外观图案Id集合
			var checkIds = getFileIds(2).join(',');	//检验报告Id集合
			var formDate = {
					companyId:companyId,	//所属企业ID
					name:name,				//名称
					spec:spec,				//规格
					grade:grade,			//品质等级
					stdNum:stdNum,			//产品标准号
					licenseNum:licenseNum,	//食盐定点生产证号
					producePlace:producePlace,//产地
					valtDateFrom:valtDateFrom,	//有效期（从）
					valtDateTo:valtDateTo,	//有效期（到）
					mixIngt:mixIngt,		//配料
					keep:keep,				//贮存
					nutrComp:nutrComp,		//营养成分
					antiFake:antiFake,		//防伪识别方法
					introduce:introduce,	//产品简介
					number:number,			//数量
					price:price,			//价格
					deliveryPlace:deliveryPlace,	//交割地
					appearIds : appearIds,	//外观图案Id集合
					checkIds : checkIds		//检验报告Id集合
			}
			var url = "/prodlib/save";
			debugger
			$.post(url,formDate,function(result){
				if(result=="success"){
					layer.msg('操作成功', {
						icon: 1
					});
					window.location.href="/prodlib/edit?companyId="+companyId; 
				}else if(result=="error"){
					layer.msg('操作失败', {
						icon: 2
					});
				}
				base.processStatus(0,'save','process_btn');
			});
		}
		
	}
	function renderDoc(upload_index,divId) {
		var option = {
			file_ids : "",
			multi_selection : true,
			max_file_size : "200mb",
			prevent_duplicates : true,
			is_shade : false,
			mime_types : "",
			upload_index:upload_index
		}
		$("#"+divId).load("/doc/index", option,function() {});
	}

	$(function(){
		renderDoc(1,"appearIds");
		renderDoc(2,"checkIds");

		$(".form-list").on("blur", "input", function(){
			checkForm($(this));
		});
		
		$("#save").click(save);
	});

});