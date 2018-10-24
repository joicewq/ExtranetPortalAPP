require.config(config);
require(["jquery","Vue","layer","validate","base","animation"],function($,Vue,layer,validate,base,animation){
	
	var submitOption={
			file_ids:"",
			multi_selection:true,
		  	max_file_size:"200mb",
		  	prevent_duplicates:true,
		  	is_shade:false,
		  	mime_types:"",
//		  	max_file_num:1,
		  	upload_index:1
		}
	function submit(){
		var $this = $(this);
		var flag = true;
		$("#report-form [name]").each(function() {
			if(($(this).hasClass("required") || $(this).val()!="" ) && !checkForm($(this))){
				//if(!checkForm($(this))){
					$(this).focus();
					flag = false;
					return false;
				//}				
			}
		});
		if(flag){
			var params=base.serializeObject($("#report-form"));
			params=$.extend({},{docId:getFileIds(1).join(","),kinds:0},params);
			$.ajax({
				url: '/claims/submitClaims',
				data: JSON.parse(JSON.stringify(params)),
				type: 'POST',
				beforeSend:function(){
					$this.addClass("disabled");
				},
				success:function(data){
					if(data!="error"){
						layer.alert("提交成功")
						$("#file_upload_div").load("/doc/index",submitOption);
						$("#report-form").find("input,select,textarea").val("");
						$("#report-form").find(".ds-from-tips").text("");
					}
					else{
						layer.alert("提交失败，请重新提交")
					}
				},
				error:function(){
					layer.alert("提交失败，请重新提交")
				},
				complete:function(){
					$this.removeClass("disabled");					
				}
			})
		}
	}
	
	function submitSuccessTip(num){
		var tip='<div class="text-center">'+
					'<p class="ds-text-blue"><i class="fa fa-check-circle"></i> 提交成功</p>'+
					'<p>您的投诉编号为<span class="ds-text-red">'+num+'</span></p>'+
					'<p>编号和密码用于查询投诉结果，请牢记</p>'+
				'</div>';
		return tip;
	}

	$(function(){
		
		$(".form-list").on("blur", "input,select,textarea", function(){
			checkForm($(this));
		});
		
		$("#file_upload_div").load("/doc/index",submitOption); 
		
		$("#js-submit-btn").on("click",function(){
			if(!$(this).hasClass("disabled")){
				submit.call(this);				
			}
		})
		var vm = new Vue({
			el:"#search-result",
			data:{
				result:{}
			},
			updated:function(){
				animation.destory({
					callback:function(){
						$("#search-failed").addClass("hide").hide();
						$("#search-success").removeClass("hide").show();
					}
				});				
			}
		})
		
		$("#js-search-btn").on("click",function(){			
			var num=$("#searchNum").val();
			var pwd=$("#searchPwd").val();
			var flag = true;
			
			$("#report-search-form [name]").each(function() {
				if(!checkForm($(this))){
					$(this).focus();
					flag = false;
					return false;
				}
			});
			if(flag){
				animation.load({
					container:$("#search-result"),
					callback:function(){
						$("#search-result").removeClass("hide").show();
					}
				})
				$.ajax({
					url:"/claims/getClaims",
					data:{
						rcCode:num,
						password:pwd
					},
					type:"POST",
					success:function(data){
						if(typeof data.rcCode != "undefined"){
							if(vm){
								vm.$data.result=data;
								var queryOption = {
										is_shade: false,
										file_ids: data.docId,
										multi_selection: true,
										max_file_size: "20mb",
										prevent_duplicates: true,
										mime_types: "",
										btn_class: "hide",
										delete_class: "hide"
									};
								$("#file_list").load("/doc/index",queryOption);
							}
						}
						else{
							animation.destory({
								callback:function(){
									$("#search-success").addClass("hide").hide();
									$("#search-failed").removeClass("hide").show();
								}
							});
						}
					}
				})
			}
		})
		
		$("#js-reset-btn").on("click",function(){
			$("#report-form input,#report-form textarea").val("");
		})
	})
	
})