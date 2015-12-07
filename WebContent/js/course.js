var vedioFile = $( "#vedioFile" ),
chid = $( "#chid" ),
vid = $( "#vid" ),
allFields = $( [] ).add( vedioFile,chid,vid );

var qtitle=$("#qtitle"),
qtype=$("#qtype"),
answer=$("#answer"),
qid=$("#qid"),
questionFields=$([]).add(qid,qtitle,qtype,answer);
var gridId;
var io=document.getElementById("data");
io.style.position = 'absolute';
io.style.top = '-1000px';
io.style.left = '-1000px';
function ajaxFileUpload()
{
	$("#loading")
	.ajaxStart(function(){
		$(this).show();
	})
	.ajaxComplete(function(){
		$(this).hide();
	});

	$.ajaxFileUpload
	(
		{
			url:'ws/upload/video',
			secureuri:false,
			fileElementId:'vedioFile',
			dataType: 'json',
			success: function (data, status)
			{
				if(typeof(data.error) != 'undefined')
				{
					if(data.error != '')
					{
						alert(data.error);
					}else
					{
						alert(data.msg);
					}
				}
			},
			error: function (data, status, e)
			{
				alert(e);
			}
		}
	)
	
	return false;

};


  
$("#qListDialog").dialog({autoOpen: false,
    height: 600,
    width: 600,
    modal: true,
      close: function() {
    	
      }
  });
$("#videoListDialog").dialog({autoOpen: false,
    height: 600,
    width: 600,
    modal: true,
      close: function() {
    	
      }
  });
    
  
$("#qdelDialog").dialog({autoOpen: false,
    height: 200,
    width: 300,
    modal: true,
    buttons: {
        "删除": function() {
        	$.get('ws/questions/del?qid='+delQId);
        	jQuery("#"+gridId).trigger("reloadGrid");
        	$( this ).dialog( "close" );
        },
        "取消": function() {
          $( this ).dialog( "close" );
        }
      },
      close: function() {
    	
      }
  });
  
$("#vdelDialog").dialog({autoOpen: false,
    height: 200,
    width: 300,
    modal: true,
    buttons: {
        "删除": function() {
        	$.get('ws/videos/del?vid='+delVid);
        	jQuery("#"+gridId).trigger("reloadGrid");
        	$( this ).dialog( "close" );
        },
        "取消": function() {
          $( this ).dialog( "close" );
        }
      },
      close: function() {
    	
      }
  });
   
var videoObj;
$( "#manageVedio" ).dialog({
    autoOpen: false,
    height: 500,
    width: 550,
    modal: true,
    buttons: {
      "保存": function() {
        var bValid = true;
        allFields.removeClass( "ui-state-error" );      
        if ( bValid ) {
          //ajaxFileUpload();
          $("#uploadForm").submit();
          $( this ).dialog( "close" );
          setTimeout(function()
			{
        	  jQuery("#"+gridId).trigger("reloadGrid")
        	},500);
        }
      },
      "取消": function() {
        $( this ).dialog( "close" );
      }
    },
    close: function() {
      allFields.val( "" ).removeClass( "ui-state-error" );
    }
  });
var chapaterExpand=function(subgrid_id,chapeter_id){
	
	var subgrid_table_id, pager_id;
	subgrid_table_id = subgrid_id+"_t";
	pager_id = "p_"+subgrid_table_id;
	$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
	jQuery("#"+subgrid_table_id).jqGrid({
			url:"ws/chapters/children?pId="+chapeter_id,
			datatype: "json",
			colNames: ['章节名称','操作'],
			colModel: [
				{name:"name",index:"name",editable:true,width:180},
				
				{name:"action",index:'action2',align:'center',width:200,sortable:false,formatter:function(cellvalue, options, rowObject){
					
					//添加html元素
					var del="<button onclick=\"manageVideo('"+options.rowId+
							"')\">维护视频</button><button onclick=\"manageQuestion('"+
									options.rowId+"')\">维护习题</button>";
					return del;
					}
				}
			],
		   	rowNum:10,
		   	pager: pager_id,
		   	sortname: 'num',
		    sortorder: "asc",
		    height: '100%',
		    editurl:"ws/chapters?pId="+chapeter_id,
		 	multiselect: false
		});
		jQuery("#"+subgrid_table_id).navGrid("#"+pager_id);
}
var editVideo=function(vid){
	 $("#vedioFile").val("");
	 if(vid&&vid!="undefined"){
		 var video=videoObj[vid];
		 $("#vid").val(vid);
		 $("#videoName").val(video.savedName);
		 $("#videoPreView").attr("src","download?id="+vid);
		 $("#videoView").show();
		 $("#videoMsg").show();
	 }else{
		 $("#vid").val("");
		 $("#videoName").val("");
		 $("#videoView").hide();
		 $("#videoMsg").hide();
		 
	 }
//	 gridId=subgrid;
	 
	 $( "#manageVedio" ).dialog( "open" );
};
var delVid;
var deleteVideo=function(vid){
	delVid=vid;
	$("#vdelDialog").dialog("open");
}
var manageVideo=function(cid){
	videoObj=$([]);
	$("#videoListDialog").dialog("open" );
	$("#chid").val(cid);
	var subgrid_table_id="video_list_table_"+cid;
	gridId=subgrid_table_id;
	$("#videolist").html("<table id='"+subgrid_table_id+"'></table>");
	jQuery("#"+subgrid_table_id).jqGrid({        
	   	url:'ws/videos?chId='+cid,
	   	height:'100%',
		datatype: "json",
	   	colNames:['视频','描述','操作'],
	   	colModel:[
	   		{name:'name',index:'title', editable:true, width:100},
	   		{name:'savedName',index:'savedName', editable:true, width:250},
	   		{name:"action",index:'action2',align:'center',width:200,sortable:false,
	   			formatter:function(cellvalue, options, rowObject){
	   				videoObj[options.rowId]=rowObject;
					//添加html元素
					var del="<button onclick=\"editVideo('"+options.rowId+"')\">编辑</button>"+
					"<button onclick=\"deleteVideo("+options.rowId+")\">删除</button>";
					return del;
				}
			}
	   		],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	//pager: '#subjectpager',
	   	sortname: 'id',
	    viewrecords: false,
	    sortorder: "desc",
	    caption:"视频列表"
	});
}

var manageQuestion=function(cid){
	questionObj=$([]);
	$("#qListDialog").dialog("open" );
	$("#chId").val(cid);
	var subgrid_table_id="qlist_table_"+cid;
	gridId=subgrid_table_id;
	$("#qlist").html("<table id='"+subgrid_table_id+"'></table>");
	jQuery("#"+subgrid_table_id).jqGrid({        
	   	url:'ws/questions?chId='+cid,
	   	height:'100%',
		datatype: "json",
	   	colNames:['题目','题型','操作'],
	   	colModel:[
	   		{name:'title',index:'title', editable:true, width:350},

	   		{name:'type',index:'type', editable:true, formatter:'select', editoptions:{value:'1:单选;2:多选;3:判断'}, width:50},
			{name:"action",index:'action2',align:'center',width:200,sortable:false,
	   			formatter:function(cellvalue, options, rowObject){
				questionObj[options.rowId]=rowObject;
				//添加html元素
				var del="<button onclick=\"editQuestion('"+options.rowId+"')\">编辑</button>"+
				"<button onclick=\"deleteQuestion("+options.rowId+")\">删除</button>"+
				"<button onclick=\"addImage("+options.rowId+")\">图片</button>";
				return del;
				}
			}
	   		],
	   	rowNum:10,
	   	rowList:[10,20,30],
	  // 	pager: '#questionpager',
	   	sortname: 'id',
	    viewrecords: false,
	    sortorder: "desc",
	    caption:"习题列表"
	});
};
try{$("#qEdit").dialog( 'destroy' );}catch(e){};
$("#qEdit").dialog({autoOpen: false,
    height: 500,
    width: 1000,
    modal: true,
    buttons: {
        "保存":  function() {
            $("#qEditForm").submit();
            qtitle.val("");
        	qtype.val("");
        	answer.val("");
        	qid.val("");
        
            questionFields.val( "" ).removeClass( "ui-state-error" );
            setTimeout(function()
					{jQuery("#"+gridId).trigger("reloadGrid")},500);
         
            $( this ).dialog( "close" );
        },
        "取消": function() {
            $( this ).dialog( "close" );
        }
      },
      close: function() {
    	  questionFields.val( "" ).removeClass( "ui-state-error" );
      }
  });
  

var courseGridId;
$( "#manageCourseImage" ).dialog({
    autoOpen: false,
    height: 400,
    width: 450,
    modal: true,
    buttons: {
      "保存": function() {
        var bValid = true;
        $("#courseImageFile").removeClass( "ui-state-error" );      
        
        if ( bValid ) {
          //ajaxFileUpload();
          $("#uploadCourseImageForm").submit();
          $( this ).dialog( "close" );
          setTimeout(function()
					{jQuery("#"+courseGridId).trigger("reloadGrid")},500);
        }
      },
      "取消": function() {
        $( this ).dialog( "close" );
      }
    },
    close: function() {
    	 $("#courseImageFile").val( "" ).removeClass( "ui-state-error" );
    	 $("#image_cid").val( "" ).removeClass( "ui-state-error" );
    	 
    }
  });
 var addCourseImage=function(cid,subgrid_table_id){
	 $("#image_cid").val( cid );
	 var course=courseObj[cid];
	 courseGridId=subgrid_table_id;
	 if(course.imagePath&&course.imagePath!=""){
			$('#cImage').attr(
				    'src','qimages?image='+course.imagePath+'&course='+cid);
			$('#cImage').show();
		}else{
			
			$('#cImage').hide();
		}
	 $( "#manageCourseImage" ).dialog("open");
 }

$( "#manageQimage" ).dialog({
    autoOpen: false,
    height: 400,
    width: 450,
    modal: true,
    buttons: {
      "保存": function() {
        var bValid = true;
        $("#imageFile").removeClass( "ui-state-error" );      
        
        if ( bValid ) {
          //ajaxFileUpload();
          $("#uploadImageForm").submit();
          $( this ).dialog( "close" );
          setTimeout(function()
					{jQuery("#"+gridId).trigger("reloadGrid")},500);
        }
      },
      "取消": function() {
        $( this ).dialog( "close" );
      }
    },
    close: function() {
    	 $("#imageFile").val( "" ).removeClass( "ui-state-error" );
    	 $("#image_qid").val( "" ).removeClass( "ui-state-error" );
    	 
    }
  });
 var addImage=function(qid){
	 $("#image_qid").val( qid );
	 var question=questionObj[qid];
	 if(question.imagePath&&question.imagePath!=""){
			$('#qImage').attr(
				    'src','qimages?image='+question.imagePath);
			$('#qImage').show();
		}else{
			
			$('#qImage').hide();
		}
	 $( "#manageQimage" ).dialog("open");
 }
var addQeuestion=function(){
	$("#qtitle").val("");
	$("#qtype").val("");
	$("#answer").val("");
	$("#qid").val("");
	$("#qexplaination").val("");
	$('#qOptionsTable tbody > tr').remove();
	$("#qEdit").dialog("open");
	$("#addOptions").attr("disabled",false); 
	
	$("#qPreviewTitle").empty();
	$("#qPreviewOptions").empty(); 
	opIndex=1;
}
var questionObj;
var opIndex=1;
var editQuestion=function(qId){
	$("#qEdit").dialog("open");
	var question=questionObj[qId];
	//alert(question.options);
	var opt=[];
	if(question.options!=null)
	{
		opt=question.options.split(":=;=:")
	};
	var cOpt=[];
	if(question.correctAnswer!=null)
	{
		cOpt=question.correctAnswer.split(":=;=:");
	}
	$("#qtitle").val(question.title);
	$("#qtype").val(question.type);
	$("#answer").val(question.correctAnswer);
	$("#qid").val(question.id);
	$("#qexplaination").val(question.explaination);
	$('#qOptionsTable tbody > tr').remove();
	opIndex=1;
	var sType="";
	var readonly="";
	if(question.type==1){
		//单选
		sType="radio";
		$("#addOptions").attr("disabled",false); 
	}else if(question.type==2){
		sType="checkbox"
		$("#addOptions").attr("disabled",false); 
	}else if(question.type==3){
		sType="radio";
		$("#addOptions").attr("disabled",true); 
		readonly=" readonly='true' ";
	}else{
		sType="radio";
	}
	
	var table = $('#qOptionsTable'); 
	for(var ind=0;ind<opt.length;ind++){
		var checked=false;
		for(var i=0;i<cOpt.length;i++){
			if(cOpt[i]==opIndex){
				checked=true;
				break;
			}
		}
		var ele="<tr><td><input name='options' value='"+
		opt[ind].replace(/&/g,'&amp;')+ "' "+readonly+" onkeyup='keyupFn(this)'/></td><td><input type='"+
		sType+"' name='cOptions' value='"+
		opIndex+"' ";
		if(checked){
			ele+="checked"
		}
		ele+=" /></td></tr>";
		opIndex++;
		table.append(ele);
	}
	previewQuestion();
}
var previewQuestion=function(){
	previewQTitle();
	previewOptions();
}
var previewQTitle=function(){
var di = document.createElement("div");    

	di.innerHTML =$("#qtitle").val();//+textStr;  
	
	$("#qPreviewTitle").empty().append(di);
	M.parseMath(di);  
}
var previewOptions=function(){
	var di = $("#qPreviewOptions");
	var qt=$("#qtype").val();
	if(qt==1){
		//单选
		sType="radio";
	}else if(qt==2){
		sType="checkbox"
		
	}else if(qt==3){
		sType="radio";
	}else{
		sType="radio";
	}
	di.empty();
	$('#qOptionsTable tbody > tr').each(function(){
		var opDiv= document.createElement("div");
		var inputs = $(this).find('td > input');
		
		opDiv.innerHTML="<input type='"+sType+"' name='cPreviewOptions'/> "+inputs[0].value;
		M.parseMath(opDiv);
		di.append(opDiv);	
	}
	);
}
var keyupFn=function(ele){
	h = ele.value.replace(/</g, '&lt;')
	if (ele.value != h){
		ele.value=h;
	}
	previewQuestion();
}

var delQId;
var deleteQuestion=function(qId){
	delQId=qId;
	$("#qdelDialog").dialog("open");
}
var qtypech=function(){
	var qt=$("#qtype").val();
	
	var table = $('#qOptionsTable'); 
	$('#qOptionsTable tbody > tr').remove();
	opIndex=1;
	if(qt==1||qt==2){
		$("#addOptions").attr("disabled",false); 
	}else{
		$("#addOptions").attr("disabled",true); 
		var ele="<tr><td><input name='options' readonly='true' value='正确'/></td><td><input type='radio' name='cOptions' value='1'/></td></tr>";
		table.append(ele);
		ele="<tr><td><input name='options' readonly='true' value='错误'/></td><td><input type='radio' name='cOptions' value='2'/></td></tr>";
		table.append(ele);
	}
}
var addOptionsFn=function(){
	var qt=$("#qtype").val();
	var sType="";
	if(qt==1){
		//单选
		sType="radio";
	}else if(qt==2){
		sType="checkbox"
		
	}else if(qt==3){
		sType="radio";
	}else{
		sType="radio";
	}
	var ele="<tr><td><input name='options' onkeyup='keyupFn(this)'/></td><td><input type='"+sType+"' name='cOptions' value='"+opIndex+"'/></td></tr>";
	opIndex++;
	var table = $('#qOptionsTable'); 
	table.append(ele);

}
var courseObj;
var courseExpand=function(subgrid_id,row_id){
	courseObj=$([]);
	var subgrid_table_id, pager_id;
	subgrid_table_id = subgrid_id+"_t";
	pager_id = "p_"+subgrid_table_id;
	$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
	
	jQuery("#"+subgrid_table_id).jqGrid(
		{
			url:'ws/courses?subId='+row_id,
		   	height:'100%',
			datatype: "json",
		   	colNames:['年级', '科目','出版社','操作'],
		   	colModel:[  		
		   		{name:'grade.name',index:'grade',width:200, editable:true,edittype:'select',editoptions:{dataUrl:'ws/grades/dropdown',size:1}},		   		
		   		{name:'subject.name',index:'subject', width:200,editable:true,edittype:'select',editoptions:{dataUrl:'ws/subjects/dropdown',size:1}},
		   		{name:'publisher.name',index:'publisher', width:200,editable:true,edittype:'select',editoptions:{dataUrl:'ws/publishers/dropdown',size:1}},
		   		{name:"action",index:'action2',align:'center',width:50,sortable:false,
		   			formatter:function(cellvalue, options, rowObject){
					courseObj[options.rowId]=rowObject;
					//添加html元素
					var del="<button onclick=\"addCourseImage("+options.rowId+",'"+subgrid_table_id+"')\">图片</button>";
					return del;
					}
				}
		   	],
		   	rowNum:30,
		   	rowList:[10,20,30],
		   	pager: pager_id,
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    editurl:"ws/courses",
		    multiselect: false,
		    subGrid: true,
		    subGridRowExpanded: function(subgrid_id, row_id) {
		   		// we pass two parameters
		   		// subgrid_id is a id of the div tag created whitin a table data
		   		// the id of this elemenet is a combination of the "sg_" + id of the row
		   		// the row_id is the id of the row
		   		// If we wan to pass additinal parameters to the url we can use
		   		// a method getRowData(row_id) - which returns associative array in type name-value
		   		// here we can easy construct the flowing
		   		var subgrid_table_id, pager_id;
		   		subgrid_table_id = subgrid_id+"_t";
		   		pager_id = "p_"+subgrid_table_id;
		   		$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
		   		jQuery("#"+subgrid_table_id).jqGrid({
		   			url:"ws/chapters?cId="+row_id,
		   			datatype: "json",
		   			colNames: ['章节名称'],
		   			colModel: [
		   				{name:"name",index:"name",editable:true,width:700}
		   			],
		   		   	rowNum:10,
		   		   	pager: pager_id,
		   		   	sortname: 'num',
		   		    sortorder: "asc",
		   		    height: '100%',
		   		    editurl:"ws/chapters?cId="+row_id,
		   		 	multiselect: false,
		   	    	subGrid: true,
		   	    	subGridRowExpanded:chapaterExpand,
		   	    	subGridRowColapsed: function(subgrid_id, row_id) {}
		   		});
		   		jQuery("#"+subgrid_table_id).navGrid("#"+pager_id);
		   	},
		   	subGridRowColapsed: function(subgrid_id, row_id) {
		   		// this function is called before removing the data
		   		//var subgrid_table_id;
		   		//subgrid_table_id = subgrid_id+"_t";
		   		//jQuery("#"+subgrid_table_id).remove();
		   	}
		}
	);
	jQuery("#"+subgrid_table_id).navGrid("#"+pager_id);
}
jQuery("#courselist").jqGrid({        
   	url:'ws/subjects',
   	height:'100%',
	datatype: "json",
   	colNames:['科目'],
   	colModel:[
   		{name:'name',index:'name', editable:true, width:700}   		
   		],
   	rowNum:10,
   	rowList:[10,20,30],
//   	pager: '#coursepager',
   	sortname: 'id',
    viewrecords: true,
    sortorder: "desc",
    caption:"课程列表",
    editurl:"ws/subjects",
    subGrid: true,
    subGridRowExpanded:courseExpand,
    subGridRowColapsed: function(subgrid_id, row_id) {
   		// this function is called before removing the data
   		//var subgrid_table_id;
   		//subgrid_table_id = subgrid_id+"_t";
   		//jQuery("#"+subgrid_table_id).remove();
   	}
});


		

//jQuery("#courselist").navGrid("#coursepager");
