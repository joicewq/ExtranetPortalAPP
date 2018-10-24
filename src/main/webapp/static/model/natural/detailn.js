require.config(config);
require(["jquery","tab","base","pager"],function($,tab,base,pager){
 	
	var pageSize = 10;

 	var url = "/natural/queryNaturalRecord";
 	var showDeafaultTable=pager.methods.showDeafaultTable= function(pageNo, pageSize) {
 		pager.methods.showTemplateTable(pageNo, pageSize, url, "pagination", "view", "tmpl",
 				base.serializeObject($("#rf")),{});
 	}
 	
	$(function($) {
		pager.methods.showDeafaultTable(1, pageSize);
		tab({});
		
		function change(id,index){
			var customClass = "hidden";
			var option = {
					is_shade: false,
					file_ids: id,
					multi_selection: true,
					max_file_size: "20mb",
					prevent_duplicates: true,
					mime_types: "",
					btn_class: "hide",
					delete_class: "hide",
					upload_index:index
				};
			return option;
		}

		$(function() {
			
			var id1 = $("#describeId").val();
			var id2 = $("#cartonPackId").val();
			var id3 = $("#qualityReportId").val();
			var id4 = $("#standardFile").val();
			var id5 = $("#tradeFile").val();
			$("#uploadDiv1").load("/doc/index", change(id1,1));
			$("#uploadDiv2").load("/doc/index", change(id2,2));
			$("#uploadDiv3").load("/doc/index", change(id3,3));
			$("#uploadDiv4").load("/doc/index", change(id4,4));
			$("#uploadDiv5").load("/doc/index", change(id5,5));
		});
	});
});