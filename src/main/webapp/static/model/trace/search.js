require.config(config);
require(["jquery","animation","validate","layer"],function($,animation,validate,layer){
	
	function refreshVerifyCode($codeEle,$inputEle,verifyCodeUrl){
		var random = Math.random();
		$codeEle.attr("src",verifyCodeUrl+random);
		$inputEle.val("");
	}
	
	$(function(){
		$("#verifyCode").on("click",function(){
			refreshVerifyCode($(this),$("#verifyCodeIpt"),"/verify/code?tm=");
		})
		
		$("#js-search-btn").on("click",function(){
			var qrCode=$("#qrCode").val();
			var verifyCode=$("#verifyCodeIpt").val();
			if(qrCode!="" && verifyCode!=""){
				if(isNaN(qrCode)!=true){
					if(/^\d{0,20}$/.test(qrCode)){
						if(matchVerifyCode(verifyCode)){
							var qrCode = $("#qrCode").val();
							var url = "/trace/search/qrcode/" + $.trim(qrCode);
							animation.load({
								container:$("#info_detail_div"),
								url:url,
								type:"POST",
								callback:refreshVerifyCode($("#verifyCode"),$("#verifyCodeIpt"),"/verify/code?tm=")
							})						
						}
						else{
							layer.alert("请输入正确的验证码");
							refreshVerifyCode($("#verifyCode"),$("#verifyCodeIpt"),"/verify/code?tm=");
						}						
					}
					else{
						layer.alert("请输入20位以内的产品溯源码")
					}
				}
				else{
					layer.alert("产品溯源码必须为数字");
				}
			}
			else{
				layer.alert("产品溯源码或验证码不可为空");
				refreshVerifyCode($("#verifyCode"),$("#verifyCodeIpt"),"/verify/code?tm=");
			}
		})
	})
})
