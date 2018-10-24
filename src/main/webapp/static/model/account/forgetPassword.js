require.config(config);
require(["jquery","validate","animation","layer","encryption","step","phoneVarifyCode"],function($,validate,animation,layer,encryption,step,phoneVarifyCode){
	var isPhoneCodePass=false;
	var isVerifyCodePass=false;
	
	var oldRandom;
	var newRandom;
	var loginId;

	var curStep=1;

	/**
	 * 跳转到第二步
	 * */
	function inputNewPassword(){
		var verifyCode = $("#verifyCode").val();
		if(oldRandom){
			if(oldRandom==verifyCode){
				$("#verifySpan").text("");
				$("#varityCodeForm").addClass("hide").hide();
				$("#newPasswordForm").show().removeClass("hide");
				step.next();
				curStep++;
			}else{
				$("#verifySpan").text("请输入正确的验证码");
			}
		}else{
			layer.msg("请先获取验证码", {icon: 0});
		}
	}
	/**
	 * 注册成功后的提醒
     */
	function setPasswordSuccess(){
		if(curStep==3){
			animation.destory({
				callback:function(){
					var registerTip="<div class='register-tips tips-center'>" +
					"<div class='clearfix register-tips-text-wrap'>" +
					"<div class='register-tips-icon'>✔</div>" +
					"<div class='register-tips-text oneLine'>" +
					"<span class='font18'>完成修改，马上登录</span>" +
					"</div>"+
					"</div>"+
					"<div class='ds-btn-group ds-btn-group-center clearfix mt20'>" +
					"<a href='/login/index' class='ds-btn ds-btn-orange'>登陆</a>"+
					"</div>"+
					"</div>";
					$(".progress-title .js-newPassword").removeClass("active");
					$(".progress-title .js-finish").addClass("active");
					$(".ds-user-main").html(registerTip);
				}
			});			
		}
	}
	/**获取该用户的手机验证码*/
	function getPhoneVerifyCode(){
		var loginName = $("#loginNamee").val();
		if(loginName.length==0){
			layer.msg("请输入用户名", {icon: 0});
			return;
		}
		var url = "/account/pwdSendMsg";
		$.post(url,{loginName:loginName},function(result){
			if(result){
				if(result.status=="1"){
					layer.msg(result.message, {icon: 1});
					oldRandom = result.random;
					loginId = result.loginId;
				}else if(result.status=="0"){
					layer.msg(result.message, {icon: 2});
				}
			}else{
				layer.msg('操作失败', {icon: 2});
			}
		});
	}
	function onBlurNew(){
		var newPassword = $("#loginPassword").val();
		if(newPassword.length==0){
			$("#newVerifySpan").text("请完善必填信息");
		}
		else{
			if(newPassword.length<6){
				$("#newVerifySpan").text("密码长度不能少于6个字符");
			}
			else{
				$("#newVerifySpan").text("");
			}
		}
	}
	function onBlur(){
		if($(this).val()!== $("#loginPassword").val()){
			var $required = $("<span class='ds-from-tips ds-text-red ml10'>两次输入密码不一致</span>");
			$(this).addClass("input-danger");
			if($(this).parent().find(".ds-text-red").length==0){
				$(this).after($required);
			} else {
				$(this).parent().find(".ds-text-red").text("两次输入的密码不一致");
			}
		}else{
			$(this).removeClass("input-danger");
			$(this).parent().find(".ds-text-red").empty();
		}
	}
	function submit(){
		var newPassword = $("#loginPassword").val();
		if(newPassword.length==0){
			$("#newVerifySpan").text("请完善必填信息");
			return;
		}else{
			$("#newVerifySpan").text("");
		}
		if($("#password").val() != newPassword){
			var $required = $("<span class='ds-from-tips ds-text-red ml10'>两次输入密码不一致</span>");
			$("#password").addClass("input-danger");
			if($("#password").parent().find(".ds-text-red").length==0){
				$("#password").after($required);
			} else {
				$("#password").parent().find(".ds-text-red").text("两次输入的密码不一致");
			}
			return;
		}else{
			$("#password").removeClass("input-danger");
			$("#password").parent().find(".ds-text-red").empty();
		}
		animation.load({
			container:$(".ds-user-main"),
			text:"正在提交，请稍后"
		});
		var url = "/account/pwdUpdate";
		$.post(url,{loginId:loginId,newPassword:newPassword},function(result){
			if(result){
				if(result.status=="1"){
					layer.msg(result.message, {icon: 1});
					step.next({
						callback:function(){
							curStep++;
							setPasswordSuccess();
						}
					})
				}else if(result.status=="0"){
					layer.msg(result.message, {icon: 2});
				}
			}else{
				layer.msg('操作失败', {icon: 2});
			}
		});
	}
	$(function(){
		step.init({
			steps:["验证信息","输入新密码","完成修改"],
			container:$(".progress-title-container")
		})
		
		/**第一步：获取验证码*/
		$("#js-getPhoneVerifyCode").on("click",getPhoneVerifyCode);

		/**接着点击下一步进入 
		 * 输入新手机
		 */
		$("#varityCodeForm .js-submit").on("click",inputNewPassword);
		
		/**
		 * 第二步：输入新密码
		 * */
		$("#loginPassword").blur(onBlurNew);
		$("#password").blur(onBlur);

		$("#newPasswordForm .js-submit").on("click",submit);
		
	})
})