//岗位
var postsetting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "unitId",
			rootPId : ""
		},
		key : {
			title : "postName",
			name : "postName"
		}
	},
	callback : {
		// 点击节点事件
		onClick : function(event, treeId, treeNode) {
			$("#s_postName").val(treeNode.postName);
			$("#s_postId").val(treeNode.id);
			$("#treediv").hide();
		}
	}
};
// 机构
var unitsetting = {
		
	data : {
		
		simpleData : {
			enable : true,
			idKey : "unitId",
			//pIdKey : "parentId",
			pIdKey: "parentUnitid",
			rootPId : ""
		},
		key : {
			title : "unitName",
			name : "unitName"
		}
	},
	callback : {
		// 点击节点事件
		onClick : function(event, treeId, treeNode) {
			$("#s_unitName").val(treeNode.unitName);
			$("#s_unitId").val(treeNode.unitId);
			$("#treediv").hide();
		}
	}
};
// 机构2
var unitsetting2 = {
		data : {
			simpleData : {
				enable : true,
				idKey : "unitId",
				pIdKey : "parentUnitid",
				rootPId : ""
			},
			key : {
				title : "unitName",
				name : "unitName"
			}
		},
		callback : {
			// 点击节点事件
			onClick : function(event, treeId, treeNode) {
				$("#s_unitName").val(treeNode.unitName);
				$("#s_unitId").val(treeNode.unitId);
				$("#treediv2").hide();
				//unitBlurValid();//点击节点的时候做校验
			}
		}
};
//通用服务编辑机构
var editunitsetting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "unitId",
				pIdKey : "parentUnitid",
				rootPId : ""
			},
			key : {
				title : "unitName",
				name : "unitName"
			}
		},
		callback : {
			// 点击节点事件
			onClick : function(event, treeId, treeNode) {
				$("#s_parentUnitName").val(treeNode.unitName);
				$("#s_parentId").val(treeNode.unitId);
				$("#treediv2").hide();
				unitBlurValid();//点击节点的时候做校验
			}
		}
};

//菜单
var menusetting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentMenuId",
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
				$("#s_menuName").val(treeNode.name);
				$("#s_menuId").val(treeNode.id);
				$("#treediv").hide();
				checkMenu();
				$("#s_menuName").valid();
			}
		}
};


//如果加载过，就不重复后台加载
function showDiv(divobj, obj, zsetting, url) {
	if($("#tree").children("li") ==undefined ||$("#tree").children("li") ==null||$("#tree").children("li").length ==0){
		$.post(url, function(data2) {
			$.fn.zTree.init($("#tree"), zsetting, data2);
			
		},"json");
	}
	$(divobj).css("left", $(obj).offset().left );
	$(divobj).css("top", $(obj).offset().top+$(obj).height()+2);
		
	$(divobj).show();
	
	$("body").bind("click",$(divobj), onBodyDown2);
	
}
//每次都从后台加载
function showDivNoCache(divobj, obj, zsetting, url) {
	
	$.post(url, function(data2) {
		$.fn.zTree.init($("#tree"), zsetting, data2);
		
	},"json");

	$(divobj).css("left", $(obj).offset().left );
	$(divobj).css("top", $(obj).offset().top+$(obj).height()+2);
		
	$(divobj).show();
	
	$("body").bind("click",$(divobj), onBodyDown2);
	
}
//如果加载过，就不重复后台加载
function showDiv2(divobj, obj, zsetting, url) {
	if($("#tree2").children("li") ==undefined ||$("#tree2").children("li") ==null||$("#tree2").children("li").length ==0){
		$.post(url, function(data2) {
			$.fn.zTree.init($("#tree2"), zsetting, data2);
			
		},"json");
	}
	
	$(divobj).css("left", $(obj).offset().left );
	$(divobj).css("top", $(obj).offset().top+$(obj).height()+2);
	
	
	$(divobj).show();
	$("body").bind("click",$(divobj), onBodyDown2);
}
//机构、用户新增、编辑 机构框使用(消失时校验)
function showDivForUnit(divobj, obj, zsetting, url) {
	if($("#tree2").children("li") ==undefined ||$("#tree2").children("li") ==null||$("#tree2").children("li").length ==0){
		$.post(url, function(data2) {
			$.fn.zTree.init($("#tree2"), zsetting, data2);
			
		},"json");
	}
	
	$(divobj).css("left", $(obj).offset().left );
	$(divobj).css("top", $(obj).offset().top+$(obj).height()+2);
	
	
	$(divobj).show();
	$("body").bind("click",$(divobj), onBodyDownForUnit);
}



function showMenu(obj) {
	$(obj).parent().find(".menuContent").css({ top: $(obj).outerHeight() + "px"}).slideDown("fast");
	$("body").bind("click",$(obj).parent(), onBodyDown2);
}
function hideMenu(obj) {
	$("body").unbind("click", onBodyDown2);
	$(obj).fadeOut("fast");
}
function hideMenuForUnit(obj){
	$("body").unbind("click", onBodyDownForUnit);
	$(obj).fadeOut("fast");
}
function onBodyDown2(event){
	console.log('onBodyDown2...');
	var obj=event.data;
	console.log('id='+event.target.id);
	if (!(event.target.id == "s_unitName" || event.target.id=='s_postName' 
		|| event.target.id=='s_unitName' || event.target.id == 's_menuName'
		|| event.target.id=='s_parentUnitName'
		|| $(event.target).parents("#treediv").length>0 || $(event.target).parents("#treediv2").length>0)) {
		
		hideMenu(obj);
	}
}
//机构、用户新增、编辑的机构框使用
function onBodyDownForUnit(event){
	console.log('onBodyDown2 for unit...');
	var obj=event.data;
	console.log('id='+event.target.id);
	if (!(event.target.id == "s_unitName" || event.target.id=='s_parentUnitName'
		|| $(event.target).parents("#treediv").length>0 || $(event.target).parents("#treediv2").length>0)) {
		unitBlurValid();//消失的时候做校验
		hideMenuForUnit(obj);
	}
}


//列表使用
var unitsettingForList = {
		
		data : {
			
			simpleData : {
				enable : true,
				idKey : "unitId",
				pIdKey: "parentUnitid",
				rootPId : ""
			},
			key : {
				title : "unitName",
				name : "unitName"
			}
		},
		callback : {
			// 点击节点事件
			onClick : function(event, treeId, treeNode) {
				$("#s_unitName").val(treeNode.unitName);
				$("#s_unitId").val(treeNode.unitId);
				$("#treediv3").hide();
			}
		}
	};
function showDivForList(divobj, obj, zsetting, url) {
	if($("#tree").children("li") ==undefined ||$("#tree").children("li") ==null||$("#tree").children("li").length ==0){
		$.post(url, function(data2) {
			$.fn.zTree.init($("#tree"), zsetting, data2);
		},"json");
	}
		
	$(divobj).show();
	
	$("body").bind("click",$(divobj), onBodyDownForList);
}
function onBodyDownForList(event){

	var obj=event.data;
	console.log('id='+event.target.id);
	if (!(event.target.id == "s_unitName" || $(event.target).parents("#treediv3").length>0) ) {
		
		$("body").unbind("click", onBodyDownForList);
		$(obj).fadeOut("fast");
	}
}
