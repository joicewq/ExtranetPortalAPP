function pages(page){
	var text="";
		text+='<ul>';
		if (page.pageNum==1){
			text+='<li><a>首页</a></li>';
		}else{
			text+='<li><a onclick="javascript:showTemplateTable(1,'+page.pageSize+');">首页</a></li>';
		}
		if(page.pageNum>1){
			text+='<li><a href="javascript:showTemplateTable('+page.prePage+','+page.pageSize+');">上一页</a></li>';
		}else{
			text+='<li><a href="javascript:void(0);">上一页</a></li>';
		}
		if((page.pageNum==page.pages)&&(page.pages>2))
			text+='<li><a onclick="javascript:showTemplateTable('+(page.prePage-1)+','+page.pageSize+');">'+(page.prePage-1)+'</a></li>'; 
		if(page.pageNum>1){
			text+='<li><a onclick="javascript:showTemplateTable('+page.prePage+','+page.pageSize+');">'+page.prePage+'</a></li>'; 
		}
		if(page.pageNum<1){
			text+='<li><a  class="active">1</a></li>';
		}else{
			text+='<li><a  class="active">'+page.pageNum+'</a></li>';
		}
		if(page.pageNum<page.pages){
			text+='<li><a onclick="javascript:showTemplateTable('+page.nextPage+','+page.pageSize+');">'+page.nextPage+'</a></li>'; 
		}
		if((page.pageNum==1)&&(page.pages>2))
			text+='<li><a onclick="javascript:showTemplateTable('+(page.nextPage+1)+','+page.pageSize+');">'+(page.nextPage+1)+'</a></li>'; 
			
		if(page.pageNum<page.pages){
			text+='<li><a href="javascript:showTemplateTable('+page.nextPage+','+page.pageSize+');">下一页</a></li>';
		}else{
			text+='<li><a href="javascript:void(0);">下一页</a></li>';
		}
		if(page.pageNum==page.pages){
			text+='<li><a>尾页</a></li>';
		}else{
			text+='<li><a onclick="javascript:showTemplateTable('+page.pages+','+page.pageSize+');">尾页</a></li>';
		}
		if(page.pages==0){
			text+='<li>共1页，到第</li>';
		}else{
			text+='<li>共'+page.pages+'页，到第</li>';
		}
			text+='<li><input type="text"  id="pageNum" class="input-page" onchange="pageSetting('+page.pages+',false)" onkeyup="pageSetting('+page.pages+',true)"></li>';
			text+='<li>页</li>';
			text+='<li><a onclick="javascript:showTemplateTable(-1,'+page.pageSize+')" class="active hover">跳转</a></li>';
			text+='</ul>';
	return text;                                                                                                              
}
