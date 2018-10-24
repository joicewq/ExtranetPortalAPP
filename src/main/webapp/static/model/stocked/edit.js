require.config(config);
require(["jquery","validate","base","animation","underscore","layer"],
		function($,validate,base,animation,_,layer){

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
			base.processStatus(1,'save','process_btn');
			var id = $("#id").val();
			var stocekCode = $("#stocekCode").val();
			var stocekOrder = $("#stocekOrder").val();
			var companyId = $("#companyName").val();	//纸箱包装的样式描述
			var companyName = $("select[name=companyName]").find("option:selected").text();	//纸箱包装的样式描述
			
			var title = $("#title").val();		//标题
			var saleNumber = $("#saleNumber").val();		//规格
			var contactPerson = $("#contactPerson").val();		//品质等级
			var saleTaskNumber = $("#saleTaskNumber").val();		//产品标准号
			var contactPhone = $("#contactPhone").val();	//食盐定点生产证号
			var stockedAddress = $("#stockedAddress").val();//产地
			
			var typeCode = $("#type").val();	//纸箱包装的样式描述
			var type = $("select[name=type]").find("option:selected").text();
			if(type=="请选择"){
				type=""
			}
			
			var stockedCapacity = $("#stockedCapacity").val();	//有效期（从）
			var postalCode = $("#postalCode").val();	//有效期（到）
			var stockedMaxCapacity = $("#stockedMaxCapacity").val();		//配料
			var stockedName = $("#stockedName").val();		//贮存
			var comeResource = $("input[type=redio]:checked").val()
			var formDate = {
					id:id,
					stocekCode:stocekCode,
					stocekOrder:stocekOrder,
					companyId:companyId,	//所属企业ID
					companyName:companyName,				//名称
					title:title,				//规格
					saleNumber:saleNumber,			//品质等级
					contactPerson:contactPerson,			//产品标准号
					saleTaskNumber:saleTaskNumber,	//食盐定点生产证号
					stockedAddress:stockedAddress,//产地
					typeCode:typeCode,	//有效期（从）
					type:type,	//有效期（到）
					stockedCapacity:stockedCapacity,		//配料
					postalCode:postalCode,				//贮存
					stockedMaxCapacity:stockedMaxCapacity,		//营养成分
					stockedName:stockedName,		//防伪识别方法
					comeResource:comeResource
			}
			var url = "/stocked/save";
			$.ajax({
                url:url,
                data: {stocked:JSON.stringify(formDate)},
                type: 'POST',
                dataType: "json",
                success: function(data) {
                    if(data.result==1){
                    	layer.msg('操作成功', {
    						icon: 1
    					});
                    	//layer.close(index);
                    	jumbto("/stocked/index");
                    }else{
                    	layer.msg('操作失败', {
						icon: 1
					});
                    }
                      
                },error:function(){
                	layer.msg('操作失败', {
						icon: 2
					});
                	base.processStatus(0,'save','process_btn');
                }
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