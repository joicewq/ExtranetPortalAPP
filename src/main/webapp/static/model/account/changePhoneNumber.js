require.config(config);
require(["jquery","validate","animation","layer","encryption","step","phoneVarifyCode"],function($,validate,animation,layer,encryption,step,phoneVarifyCode){
	var isPhoneCodePass=false;
	var isVerifyCodePass=false;

	var oldRandom;
	var newRandom;
	
	var urlObj = {
		userNameVerify:null,	//用户名、验证码校验接口
		phoneVerifyCode:null,	//手机验证验证码接口
		newPassword:null,		//设置新密码接口
	}

	var curStep=1;
	var loginName="";//将在第一步提交用户名、验证码校验成功后返回，用于设置新密码发送参数

	/**
	 * 跳转到第二步
	 * */
	function inputNewPassword(){
		var verifyCode = $("#verifyCode").val();
		if(oldRandom){
			if(oldRandom==verifyCode){
				$("#verifySpan").text("");
				$("#oldPhoneNumberForm").addClass("hide").hide();
				$("#newPhoneNumberForm").show().removeClass("hide");
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
	 * 提交新手机，跳转到第三步：修改成功页面
	 */
	function editSuccess(){
		var newVerifyCode = $("#newVerifyCode").val();
		if(oldRandom){
			if(newRandom==newVerifyCode){
				animation.load({
					container:$(".ds-user-main"),
					text:"正在提交，请稍后"
				});
				updateUserNewPhoneCode();
				step.next({
					callback:function(){
						curStep++;
						setNewPhoneNumberSuccess();
					}
				})
			}else{
				$("#newVerifySpan").text("请输入正确的验证码");
			}
		}else{
			layer.msg("请先获取验证码", {icon: 0});
		}
	}

	/**
	 * 修改手机号码成功后的提醒
     */
	function setNewPhoneNumberSuccess(){
		if(curStep==3){
			animation.destory({
				callback:function(){
					var registerTip="<div class='register-tips tips-center'>" +
					"<div class='clearfix register-tips-text-wrap'>" +
					"<div class='register-tips-icon'>✔</div>" +
					"<div class='register-tips-text oneLine'>" +
					"<span class='font18'>完成修改</span>" +
					"</div>"+
					"</div>"+
					"</div>";
					$(".progress-title .js-newPassword").removeClass("active");
					$(".progress-title .js-finish").addClass("active");
					$(".ds-user-main").html(registerTip);
					
					
				}
			});			
		}
	}
	
	/**
	 * 获取 原 手机短信验证码
     */
	function getPhoneCode(){
		var phone = $("#oldPhoneNumber").val();
		var type = "old";
		var url = "/account/cagPhoneSendMsg";
		$.post(url,{phone:phone,type:type},function(result){
			if(result){
				if(result.status=="1"){
					layer.msg(result.message, {icon: 1});
					oldRandom = result.random;
				}else if(result.status=="0"){
					layer.msg(result.message, {icon: 2});
				}
			}else{
				layer.msg('操作失败', {icon: 2});
			}
		});
	}
	/**
	 * 获取 新 手机短信验证码
     */
	function getNewPhoneCode(){
		var phone = $("#newPhoneNumber").val();
		var type = "new";
		var url = "/account/cagPhoneSendMsg";
		$.post(url,{phone:phone,type:type},function(result){
			if(result){
				if(result.status=="1"){
					layer.msg(result.message, {icon: 1});
					newRandom = result.random;
				}else if(result.status=="0"){
					layer.msg(result.message, {icon: 2});
				}
			}else{
				layer.msg('操作失败', {icon: 2});
			}
		});
	}
	/**
	 * 修改用户手机
     */
	function updateUserNewPhoneCode(){
		var phone = $("#newPhoneNumber").val();
		var type = "new";
		var url = "/account/updateUserphone";
		$.post(url,{phone:phone,type:type},function(result){
			if(result){
				if(result.status=="1"){
					layer.msg(result.message, {icon: 1});
					step.next({
						callback:function(){
							curStep++;
							setNewPhoneNumberSuccess();
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
			steps:["验证原手机号","输入新手机号","完成修改"],
			container:$(".progress-title-container")
		})
		$(".required").on("blur",function(){
			checkForm($(this));
		});
		$(".checkPhoneExist").on("blur",function(){
			checkForm($(this));
		});
		
		/**第一步：获取验证码*/
		$(".js-getPhoneCode").on("click", getPhoneCode);
		
		/**接着点击下一步进入 
		 * 输入新手机
		 */
		$("#oldPhoneNumberForm .js-submit").on("click",inputNewPassword);
		
		/**第二步：输入新手机号码*/
		$(".js-getNewPhoneCode").on("click",getNewPhoneCode);

		/**
		 *接着点击提交新手机 
		 * */
		$("#newPhoneNumberForm .js-submit").on("click",editSuccess);
		
	})
})