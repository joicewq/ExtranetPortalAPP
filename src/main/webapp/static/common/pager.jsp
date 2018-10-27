<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<link rel="stylesheet" href="${ctx }/static/css/layout.css" />
	<script>
		function pageSetting (num,obj){
		if(obj){			
			if( isNaN($('#pageNum').val()) || parseInt($('#pageNum').val()) < 1){
				$('#pageNum').val(1);
			}
			if( parseInt($('#pageNum').val()) > parseInt(num)){
				$('#pageNum').val(num);
				parseInt(num) == 0 && $('#pageNum').val(" ");
			}			
		}
	}
	</script>
<!-- 表分页信息模版 -->
<textarea id="pageMsgTemplate" style="display: none">
	<![CDATA[
             <div class="ds-page clearfix">                 
                 
                     <ul>
		              
						{#if $T.curPage>1}
						<li><a href="javascript:pager.showDeafaultTable('{$T.columnId}',1,{$T.pageLine});">首页</a></li>
						{#else}{* 没有上一页*}
							<li><a href="javascript:void(0);">首页</a></li>
						{#/if}
					  </li>
		              
					  	{#if $T.curPage>1}
							<li><a href="javascript:pager.showDeafaultTable('{$T.columnId}',{$T.curPage-1},{$T.pageLine});">上一页</a></li>
						{#else}{* 没有上一页*}
							<li><a href="javascript:void(0);">上一页</a></li>
						{#/if}
					  

						{#if $T.curPage==$T.totalPage &&$T.totalPage>2}{*如果本页等于最后页,总数大于2,补上倒数第三页*}
							<li><a onclick="javascript:pager.showDeafaultTable('{$T.columnId}',{$T.curPage-2},{$T.pageLine});">{$T.curPage-2}</a></li>
						{#/if}

					  	{#if $T.curPage>1}{*如果本页大于1,那么显示前一页*}
							<li><a onclick="javascript:pager.showDeafaultTable('{$T.columnId}',{$T.curPage-1},{$T.pageLine});">{$T.curPage-1}</a></li>
						{#/if}
					  <li><a  class="active">{$T.curPage}</a></li>{*显示当前页*}
					  	{#if $T.curPage<$T.totalPage}{*如果本页小于最后页,显示最后一页*}
							<li><a onclick="javascript:pager.showDeafaultTable('{$T.columnId}',{$T.curPage+1},{$T.pageLine});">{$T.curPage+1}</a></li>
						{#/if}
					  	{#if $T.curPage==1 &&$T.pages>2}{*如果本页等于第一页,总数大于2,补上倒数第三页*}
							<li><a onclick="javascript:pager.showDeafaultTable('{$T.columnId}',{$T.curPage+2},{$T.pageLine});">{$T.curPage+2}</a></li>
						{#/if}

					  	{#if $T.curPage<$T.totalPage}
							<li><a href="javascript:pager.showDeafaultTable('{$T.columnId}',{$T.curPage+1},{$T.pageLine});">下一页</a></li>
						{#else}{* 没有下一页*}
							<li><a href="javascript:void(0);">下一页</a></li>
						{#/if}
					  	{#if $T.curPage<$T.totalPage}
							<li><a href="javascript:pager.showDeafaultTable('{$T.columnId}',{$T.totalPage},{$T.pageLine});">尾页</a></li>
						{#else}{* 没有下一页*}
							<li><a href="javascript:void(0);">尾页</a></li>
						{#/if}
		              
		              <li>共{$T.totalPage}页，到第</li>
                    <li><input type="text"  id="pageNum" onchange="pageSetting({$T.totalPage},false)" onkeyup="pageSetting({$T.totalPage},true)" class="input-page"></li>
                    <li>页</li>
                    <li><a onclick="javascript:pager.showDeafaultTable('{$T.columnId}',$('#pageNum').val(),{$T.pageLine})" class="active hover">确定</a></li>
	              </ul>
	          </div>
	      </div>
	      
	]]>
</textarea>

