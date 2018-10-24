/**
 * watson pan
 */
require.config(config);
require(["jquery","validate","base","strength"],function($,validate,base,strength){
	function back(){
		$("#rightContent").load('/account/safe/index');
	}
	function showCode(){
		$("#verifyImg").attr("src","/verify/code?tm="+Math.random());
		$("#verifyCode").val("");
		$("#verifySpan").text("");
	}
	
	function submit(){
		var flag = true;
		$(".form-list [name]").each(function() {
			if(!checkForm($(this))){
				$(this).focus();
				flag = false;
				return false;
			}
			switch ($(this).attr("id")) {
			case "newPassword":
				if($("#newPassword").val().indexOf(" ")>-1){
					$("#newPassword").focus();
					flag = false;
					$("#newPassword").parent().find(".ds-text-red").html("密码不能包含空格")
					return false;
				}
				break;
			case "checkNewPassword":
				if($("#checkNewPassword").val().indexOf(" ")>-1){
					$("#checkNewPassword").focus();
					flag = false;
					$("#checkNewPassword").parent().find(".ds-text-red").html("密码不能包含空格")
					return false;
				}
				break;

			default:
				break;
			}
		});
		if(flag){
			base.processStatus(1,'submit','process_btn');
			var password = $("#password").val();
			var newPassword = $("#newPassword").val();
			var checkNewPassword = $("#checkNewPassword").val();
			var verifyCode = $("#verifyCode").val();
			if(newPassword!=checkNewPassword){
				$("#theTips").text("确认密码必须与新密码相同");
				base.processStatus(0,'submit','process_btn');
				return;
			}
			
			var formData = {
					password:password,
					newPassword:newPassword
			}
			var url = "/account/safe/update";
			$.post(url,formData,function(result){
				base.processStatus(0,'submit','process_btn');
				if(result=="success"){
					layer.msg('操作成功', {icon: 1});
					delsession();
				}else if(result=="errorByPassword"){
					layer.msg('原密码不正确', {icon: 0});
				}else if(result=="error"){
					layer.msg('操作失败', {icon: 2});
				}
			});
		}
	}
	
	function delsession(){
		var url = "/login/delsession";
		$.post(url,{},function(result){
			if(result){
				if(result.result=="1"){
					layer.msg(result.message, {icon: 1});
					location.href="/login/index";
				}
			}else{
				layer.msg('操作失败', {icon: 2});
			}
		});
	}
	$(function(){
		showCode();
		
		$("#submit").click(submit);
		
		$("#showCode").click(showCode);
		$(".required").on("blur",function(){
			checkForm($(this));
		});
		$("#back").click(back);
		
		//调用密码强度插件
		$("#newPassword").strength({
            strengthClass: 'strength',
            strengthMeterClass: 'strength_meter',
            strengthButtonClass: 'button_strength',
            strengthButtonText: 'Show Password',
            strengthButtonTextToggle: 'Hide Password',
            text:{
            	veryWeak:"非常弱",
            	weak:"弱",
            	medium:"中",
            	strong:"强"
            },
            isShowPassword:false,
            strength:"强度:"
        });
		
		/**
		 * 失去焦点验证
		 */
		$(".form-list [name]").blur(function(){
			if(!checkForm($(this))){
				return false;
			}
			
			switch ($(this).attr("id")) {
			case "newPassword":
				if($("#newPassword").val().indexOf(" ")>-1){
					$("#newPassword").focus();
					$("#newPassword").parent().find(".ds-text-red").html("密码不能包含空格")
					return false;
				}
				break;
			case "checkNewPassword":
				if($("#checkNewPassword").val().indexOf(" ")>-1){
					$("#checkNewPassword").focus();
					$("#checkNewPassword").parent().find(".ds-text-red").html("密码不能包含空格")
					return false;
				}
				break;

			default:
				break;
			}
		});
		
	});
});