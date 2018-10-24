require.config(config);
require(["jquery","validate","base","animation","underscore","layer"],
		function($,validate,base,animation,_,layer){

	function save(){
		var flag = true;
		$("#supply-form [name]").each(function() {
			if(!checkForm($(this))){
				$(this).focus();
				flag = false;
				return false;
			}
		});
		if(flag){
			base.processStatus(1,'save','process_btn');
			var id = $("#id").val();
			var companyName = $("#companyName").val();
			var companyId = $("#companyId").val();
			var saltRetailers = $("#saltRetailers").val();	//纸箱包装的样式描述
			var userName = $("#userSaleName").val();	//纸箱包装的样式描述
			
			var title = $("#title").val();		//标题
			var userAddress = $("#userAddress").val();		//规格
			var contactPerson = $("#contactPerson").val();		//品质等级
			var contactPhone = $("#contactPhone").val();		//产品标准号
			var monthSalt = $("#monthSalt").val();	//食盐定点生产证号
			var formDate = {
					id:id,
					companyName:companyName,
					companyId:companyId,
					saltRetailers:saltRetailers,				//名称
					userName:userName,			//品质等级
					title:title,				//规格
					userAddress:userAddress,	//食盐定点生产证号
					contactPerson:contactPerson,			//产品标准号
					contactPhone:contactPhone,//产地
					monthSalt:monthSalt
			}
			var url = "/sale/save";
			$.ajax({
                url:url,
                data: {sale:JSON.stringify(formDate)},
                type: 'POST',
                dataType: "json",
                success: function(data) {
                    if(data.result==1){
                    	layer.msg('操作成功', {
    						icon: 1
    					});
                    	//layer.close(index);
                    	jumbto("/sale/index");
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

	$(function(){

		$("#supply-form").on("blur", "input", function(){
			checkForm($(this));
		});
		
		$("#save").click(save);
	});

});