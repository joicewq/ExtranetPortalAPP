require.config(config);
require(["jquery","validate","base","animation","underscore","layer","ztree"],
		function($,validate,base,animation,_,layer,ztree){
	

//	$(document).ready(function() {
//	});
	$(function(){ 

//		$(".form-list").on("blur", "input", function(){
//			checkForm($(this));
//		}); 
		var categorying = {
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "preId",
					rootPId : ""
				},
				key : {
					title : "name",
					name : "name"
				}
			},
			callback : {
				// 点击节点事件
				onClick : function(event, treeId, treeNode) {
					$("#pIdName").val(treeNode.name);
					$("#pid").val(treeNode.id);
					$("#tree").hide();
				}
			}
		};

		$("#pIdName").focus(function() {
				var divObj = $("#tree");
				var txtObj = $("#pIdName");
				showDiv(divObj, txtObj, categorying, "/demand/getcategory");
			});

		// 如果加载过，就不重复后台加载
		function showDiv(divobj, obj, zsetting, url) {
			if ($("#tree").children("li") == undefined 	|| $("#tree").children("li") == null || $("#tree").children("li").length == 0) {
				$.post(url, function(data2) {
					$.fn.zTree.init($("#tree"), zsetting, data2);
				}, "json");
			}
//			$(divobj).css("left", $(obj).offset().left);
//			$(divobj).css("top", $(obj).offset().top + $(obj).height() + 2);
			
			$(divobj).show();
			$("body").bind("click", $(divobj), onBodyDown2);
		} 
		function onBodyDown2(event) { 
			var obj = event.data; 
			if (!(event.target.id == "pIdName" || $(event.target).parents("#tree").length > 0)) {
				hideMenu(obj);
			}
		}
		function hideMenu(obj) {
			$("body").unbind("click", onBodyDown2);
			$(obj).fadeOut("fast");
		} 
		
		
		$("#save").click(save);
	});
 
	
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
			var formDate = base.serializeObject($("#supply-form"));
			$.post("/demand/save",formDate,function(result){
				if(result.code=="1"){
					layer.msg('操作成功', {
						icon: 1
					});
					$("#js-reset-btn").trigger("click");
					$("#rightContent").load('/demand/edit');
				}else if(result=="0"){
					layer.msg('操作失败', {
						icon: 2
					});
				}
				base.processStatus(0,'save','process_btn');
			});
		}
	}	
	

	
});