/////start////
/**
 * 设置未来(全局)的AJAX请求默认选项
 * 主要设置了AJAX请求(非跨域）遇到Session过期的情况
 */
$.ajaxSetup({
    complete: function(xhr,status) {
        var redirect = xhr.getResponseHeader("redirect");
        var url = xhr.getResponseHeader('url');      
        if(redirect!=null) { 
            var top = getTopWinow();           
            top.location.href = url;               
        }
    }
});

/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * @return 当前页面的顶层窗口对象
 */
function getTopWinow(){
    var p = window;
    while(p != p.parent){
        p = p.parent;
    }
    return p;
}

///////////end////


var currentPageNo = 1 ;
 var currentPageSize = 10;
 var totalPageCount = 1;
 var queryUrl ;


/**
 * 异步加载内容,放到#mainRightContent中
 */
 //2016-7-15 抽取list页面的table，增加_isReloadTable参数
 var pageContentDiv;
function loadConent(_url,_data,_isReloadTable){
	if(pageContentDiv == undefined || pageContentDiv == null || pageContentDiv == ''){
		//默认,点击左侧菜单时
		pageContentDiv = '#app-content .pagerloader-data-c .animation-dv';
	}
	console.log(_url);
	console.log(_data);
	//刷新表格
	if(_isReloadTable){
		pageContentDiv = '#table-papination';
	}/*else{
		pageContentDiv = '#app-content .pagerloader-data-c .animation-dv';
	}*/
	
	$.ajax({
		url:_url,
		type:"post",
		data:_data,
		dataType:"html",
		success:function(data){
			$(pageContentDiv).html(data)
		},
		error:function(){
			
		}
	});
} 
/**
 * 关闭layerOpen
 */
function closeLayerOpen($this){
	parent.layer.close(parent.layer.getFrameIndex(window.name));
}
//单独刷新table数据
function jumpTablePage(shouldChangePageNo,pageSize){
	var totalPage = totalPageCount;
	var isLegalPageNO = true;
	//页码数校验
	if(isNaN(shouldChangePageNo) || parseInt(shouldChangePageNo)<1 || parseInt(totalPage)<= 0 || parseInt(shouldChangePageNo) > parseInt(totalPage)){
		isLegalPageNO = false;
	}
	
	if(!isLegalPageNO){
		layer.msg("请输入正确页数",{
			icon: 0
		});
		$("#shouldChangePage").val(currentPageNo);
		return false;
	}
	
	
	var isReloadTable = true;
	jumpPage(shouldChangePageNo,pageSize,isReloadTable);
}


/*function jumpPage(){
	// alert('changePage');
	 jumpPage(currentPageNo,currentPageSize);
 }
 function jumpPage(pageNo){
	 jumpPage(pageNo,currentPageSize);
 }*/
 function jumpPage(pageNo,pageSize,isReloadTable){
	 var url=queryUrl;
	 //刷新表格数据的路径在list的路径添加table
	 if(isReloadTable){
		 url = url+"/table";
	 }
	 var formId = "#_query_form";
	 if(pageSize == undefined || pageSize == null || pageSize == '' ){
		 pageSize = currentPageSize;
	 }
	 
	 var totalPage = totalPageCount;
	 if(pageNo == undefined || pageNo == null || pageNo == ''){
		 pageNo = currentPageNo;
	 }
	 
	 changePage(url,formId,pageNo,pageSize,totalPage,isReloadTable);
 }

/**
 * 分页查询
 */
function changePage(/**查询的url */listUrl,/**查询条件form表单的id,格式"#formId",如果没条件，则传"" */queryFormId,pageNo,pageSize,totalPage,isReloadTable){
//	alert('pageNo='+pageNo+"   totalPage="+totalPage);
	console.log(listUrl);
	var queryData = "pageNo="+pageNo+"&pageSize="+pageSize;
	if(queryFormId != null && queryFormId != ""){
		queryData = queryData+"&"
		queryData = queryData+$(queryFormId).serialize();
		queryData = decodeURIComponent(queryData,true);
		
	}
//	alert(queryData);

	loadConent(listUrl,queryData,isReloadTable);
	
}
//重置form表单,如果有原始值，则重置回原始值
function resetForm(form){
	$(form)[0].reset();
}
//清空form表单,哪怕有原始值的也清空
function clearForm(form){
	$(form).find(':input').not(':button, :submit, :reset,:hidden').val('').removeAttr('checked').removeAttr('selected');
	$(form).find('.js-example-basic-single').val(null).trigger("change");

}

//table checkbox全选或全不选
function tableCheckAll(obj){
	var check = $(obj).is(':checked');
	$("table tr td input[type='checkbox']").each(function(index,o){
		$(o).attr('checked',check);
	});
}
/**
 * 列表页，批量删除
 * @param url
 * @param data
 */
function deleteBatch(url){
	 var ids = getListTableCheckboxValue() ;
	 if( ids == ''){
		 warnTip('请选择一条记录');
		 return;
	 }
	 var isDeling = false;
	layer.confirm('请确认是否删除', {
  	    btn: ['确定', '取消']
  	    ,yes: function(index){
  	    	console.log("isDeling:"+isDeling);
  	    	if(isDeling){
  	    		return;
  	    	}
  	    	isDeling=true;
  	    	$.post(url,{'ids':ids},function(result){
  	  		if(result.code=="success"){
  	  			 successTip();
  	  			//刷新列表
  	  			jumpPage(1,'',true);
  	  		}else{
  	  		errorTip();
  	  		}
  	  		isDeling = false;
  	  	},"json");
  	    }
  	});
}
/**
 * 跳到编辑页
 * @param _url
 * @param _title
 */
function goUpdate(_url,_title){
	 
	 var ids = getListTableCheckboxValue() ;
	 if( ids == '' || ids.split(",").length>1){
		 warnTip('请选择一条记录');
		 return;
	 }
	var url = _url +"/" + ids;
	
	layerOpen(url,_title);
	
	}
/**
 * 列表页list 下的table 下的checkbox值
 */
function getListTableCheckboxValue(){
	return getCheckboxValue("#_list_table tbody tr td input[type='checkbox']:checked");
}
/**
 * 获取checkbox的值，用英文逗号隔开,不区分选不选中
 * @param position
 */
function getCheckboxValue(position){
	var v = '';
	$(position).each(function(index,o){
		if(index == 0){
			v = v + $.trim($(o).val());
		}else{
			v = v + ',' + $.trim($(o).val());
		}
	});
	return v;
}
//编辑时是否只选择了1条记录,并提示
function isCheckOneWhenEdit(){
	
	var checkedCount = $("#_list_table tbody tr td input[type='checkbox']:checked").length;
	
	 if( checkedCount != 1 ){
		 
		 warnTip('请选择一条记录');
		 return false; 
	 }
	 
	 return true;
}
//列表页中table
function listTableCheckAll(obj){
	$("#_list_table tbody tr td input[type='checkbox']").attr('checked',$(obj).is(':checked'));
}


function errorTip(){
	layer.msg('操作失败', {icon: 2});
}
function warnTip(tip){
	layer.msg(tip, {icon: 0});
}
function successTip(){
	layer.msg('操作成功', {icon: 1});
}

function goAdd(url,title,area){
	layerOpen(url,title,area);
}
function layerOpen(url,title,area){
	if(area == undefined || area == null || area == ''){
		area = ['850px' , '550px'];
	}
	layer.open({
	    type: 2,
	    title: title,
	    maxmin: false,
	    shadeClose: false, //点击遮罩关闭层
	    area : area,
	    content: url 
	});
}
/**
 * 定时关闭layer的 IFrame层
 */
function timeCloseFrame(){
	setInterval("parent.layer.close(parent.layer.getFrameIndex(window.name))",2000);
}

//IAM 自定义的方法

//填写内容不能为空或过长，或含<script>
function isNullOrTooLong(text,length){
	return text == null  || $.trim(text) == '' || $.trim(text).length>parseInt(length) || isContailJs(text);
}
//不能过长
function isTooLong(text,length){
	if(text == null || $.trim(text) == ''){
		return false;
	}
	return $.trim(text).length>parseInt(length) || isContailJs(text);
}
//是否包含<script
function isContailJs(text){
	if(text == null) return false;
	 var t = /<\s*script/ig;
	 return t.test(text);
}
//用户登录名只能英文数字
jQuery.validator.addMethod("userLoginName", function(value, element) {       
	return this.optional(element) || /^[A-Za-z0-9]+$/.test(value);       
	}, "请输入正确的登录名");

function isGoodUrl(url){
	if(url == null || $.trim(url)=='') return false;
	if(url.length>300) return false;
	var t =/^[\/\?\w\d#\%\&\:]+$/ig;
	return t.test(url);
}

//自定义校验规则
jQuery.validator.addMethod("iamUrl", function(value, element) {
	var val = $.trim($(element).val());
	var t =/^[\/\?\w\d#\%\&\-\|!:\\.]+$/ig;
	return this.optional(element)||t.test(value);
},"请输入正确的url");
jQuery.validator.addMethod("specialCharValidateIAM", function(value, element) {
	var val = $.trim($(element).val());
	$(element).val(val);
	var pattern = new RegExp("[`~$^':;'\"#<>/?\\.；：%……+￥（）【】‘”“'。，、？]");
	return this.optional(element)||!pattern.test(value);
},"请不要输入特殊字符");

jQuery.validator.addMethod("stringCheckIAM", function(value, element) {       
	return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5-_]+$/.test(value);       
	}, "只能包含中文、英文、数字、下划线等字符");  