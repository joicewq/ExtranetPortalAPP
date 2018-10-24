;define(["jquery"],function($){
		/**
		 * created by hehuajun
		 */
		var base={};
		base.ns = function(){
			var o  = {},d;
			for ( var i = 0; i < arguments.length; i++) {
				d = arguments[i].split(".");
				o = window[d[0]] = window[d[0]] || {};
				for(var k = 0 ; k<d.slice(1).length;k++){
					o = o[d[k+1]] = o[d[k+1]] || {};
				}
			}
			return o;
		};
		/* 将form表单内的元素序列化为对象，扩展Jquery的一个方法 */
		base.serializeObject = function(form) { 
			var o = {};
			$.each(form.serializeArray(), function(index) {
				if (o[this['name']]) {
					o[this['name']] = o[this['name']] + "," + this['value'];
				} else {
					o[this['name']] = this['value'];
				}
			});
			return o;
		};
		var interval;
		/*表单重复提交按钮设置*/
		base.processStatus =function(i,x,y){
			if(i == 1){	//show
				$("#" + x).hide();
				$("#" + y).show();
				var str = $("#" + y).val();
				var j = 0;
				interval=setInterval(function(){
					if(j % 4 == 0){
						$("#" + y).val(str);
					}else{
						$("#" + y).val($("#" + y).val()+".");
					}
					j++;
				},700);
			}else{	//hidden
				if(interval){
					clearInterval(interval);
				}
				$("#" + x).show();
				$("#" + y).hide();
			}
		};
		
//全选
		base.selectAll=function(obj,clazz) {
			if ($(obj).attr("checked")) {
				$("."+clazz).attr("checked", "checked");
			} else {
				$("."+clazz).removeAttr("checked");
			}
		};
		
		Date.prototype.Format = function(fmt)   
		{ 
			var o = {   
					"M+" : this.getMonth()+1,                 //月份   
					"d+" : this.getDate(),                    //日   
					"h+" : this.getHours(),                   //小时   
					"m+" : this.getMinutes(),                 //分   
					"s+" : this.getSeconds(),                 //秒   
					"q+" : Math.floor((this.getMonth()+3)/3), //季度   
					"S"  : this.getMilliseconds()             //毫秒   
			};   
			if(/(y+)/.test(fmt))   
				fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
			for(var k in o)   
				if(new RegExp("("+ k +")").test(fmt))   
					fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
			return fmt;   
		}  
		window.base=base;
		return base;
		
	//限制textarea字符长度，替代之前的"onkeyup="this.value = this.value.substring(0, 500)"造成的IE9不兼容情况
	$(function() { 
		$("textarea").bind('keydown',function(){
			if($(this).get(0).value.length > 500){
				event.returnValue = false; 
			}
		});
	})
})


