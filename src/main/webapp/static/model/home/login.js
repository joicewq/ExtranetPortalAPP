require.config(config);
require([ "jquery","layer" ], function($,layer) {
	/**
	 * 用户登录
	 */
	function login() {
		var loginName = $("#loginName").val();
		var loginPassword = $("#loginPassword").val();
		if (loginName.indexOf(" ") > -1 || loginPassword.indexOf(" ") > -1) {
			$("#loginPassword").next().html("用户名或密码错误!");
		} else {
			var param = "loginName=" + loginName + "&loginPassword="
					+ loginPassword;
			$.ajax({
				url : "/login/login", // url action是方法的名称
				data : param,
				type : 'POST',
				dataType : "json", // 可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
				// contentType: "application/json; charset=utf-8",
				success : function(msg) {
//					if (msg.result == 0) {
//						layer.msg(msg.message, {
//							icon : 2
//						});
						$("#loginName").next().html("");
						$("#loginPassword").next().html("");
						if(msg.result==0){
							$("#loginName").next().html(msg.message);
						}
						if(msg.result==1){
							$("#loginPassword").next().html(msg.message);
						}
//						console.error(msg.message);
//					}
					if (msg.result == 2) {	
//						alert($('#isLogin').style.display)
						$('#isLogin').show();
						$('#notLogin').hide();
						location.href = "/salt/index?state=1";
						$("#userName").html(loginName);
					}
				},
				error : function(msg) {
					layer.msg("登录失败!", {
						icon : 6
					});
				}
			});
		}
	}
	/**
	 * 忘记密码
	 */
	function forgetPassword() {
		location.href = "/account/forgetPassword";
	}

	$(function() {
		$("#js-login-btn").on("click", function() {
			login();
		})

		$("#js-forget-password").on("click", function() {
			forgetPassword();
		})
		//键盘捕捉事件
		document.onkeydown=function(event){
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if(e && e.keyCode==13){ // enter 
				login();
				return false;
			} 

		}; 
	}) 

})