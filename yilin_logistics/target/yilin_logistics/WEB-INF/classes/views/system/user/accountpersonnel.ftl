<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>员工管理</title> 
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
    <link href="${WEB_PATH}/resources/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/jsTree/style.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">

        
        <div class="row">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>组织机构 &nbsp;&nbsp;&nbsp;<small style="color: red;">(选择组织机构)</small></h5>
                    </div>
                    <div class="ibox-content" style="height: 672px">
                        <div id="dept_jstree">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-9">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5 ><font id="positions_msg"></font>员工列表</h5>
                    </div>
                    
                    <div class="ibox-content">
		                <div class="row row-lg">  
		                    <div class="col-sm-12">
		                        <!-- Example Events -->
		                        <div class="example-wrap"> 
						                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
					                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addUser" data-target="#userModal">
					                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增
					                                    </button>
					                                    <button type="button" class="btn btn-outline btn-default" id="editUser" >
					                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改
					                                    </button>
					                                     <font hidden="true" id="positions_val"></font>
					                                    <button type="button" class="btn btn-outline btn-default" id="delUser">
					                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>&nbsp;&nbsp;删除
					                                    </button> 
					                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="resetPassWord" >
					                                        <i class="fa fa-expeditedssl" aria-hidden="true"></i>&nbsp;&nbsp;重置密码
					                                    </button>
					                                </div>
		                                <table id="exampleTableEvents" data-height="645" data-mobile-responsive="true" >
		                                    <thead>
		                                        <tr>
		                                            <th data-field="state" data-radio="true"></th>  
		                                            <th data-field="id"  data-visible="false" >id</th>  
										            <th data-field="account" >帐号</th>  
										            <th data-field="name"  >姓名</th> 
										            <th data-field="role.name"  >角色</th>
										            <th data-field="companySection.name"  >组织机构</th> 
										            <th data-field="authentication" data-formatter="formatAuth" >实名认证</th>  
										            <th data-field="phone"  >电话</th> 
										            <th data-field="login_count"  >登录次数</th>
										            <th data-field="create_time"  data-formatter="formatTime" >创建日期</th>   
										            <th data-field="status" data-formatter="formatStatus" >状态</th>
		                                        </tr>
		                                    </thead>
		                                </table>
		                        </div>
		                        <!-- End Example Events -->
		                    </div>
		                </div>
		            </div>
                </div>
            </div>
        </div>
    </div>
    
    <div id="add-user"></div>
    <div id="edit-user"></div>
    <@import.tableManagerImportScript/>
    <script src="${WEB_PATH}/resources/js/demo/treeview-demo.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/jsTree/jstree.min.js"></script>
<script>
    	function queryParams(params) {
	        var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		        limit: params.limit,  //页面大小
		        offset: params.offset, //页码
		        search: $("input.form-control.input-outline").val(),
		        scompanySectionId: $("#positions_val").html(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/user/getPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:15,   
	            showRefresh:true, 
	            showColumns:true,
	            iconSize: "outline",
	            toolbar: "#exampleTableEventsToolbar",
	            sidePagination: "server", //设置为服务器端分页
	            queryParams: queryParams,//参数
	            searchText:"",   //设置搜索框文本初始值
	            minimumCountColumns: 1,  
	            showToggle:false, 
	            clickToSelect: true, 
	            icons: {
	                refresh: "glyphicon-repeat", 
	                columns: "glyphicon-list"
	            }
	        });
	        $('input.form-control.input-outline').attr("placeholder","输入关键字搜索");
	    });
	 $(document).ready(function() {
        $("#tool_btn").hide();
   		$('#dept_jstree').jstree({
        'core': {
                'data': ${json}
        }
        }).bind("select_node.jstree",function(event,obj) {
        		$("#positions_msg").html(obj.node.text);
        		$("#positions_val").html(obj.node.id);
                $("#exampleTableEvents").bootstrapTable('refresh');
        }).bind("loaded.jstree", function () {
	           $('#dept_jstree').jstree("open_all");
	    });
		
		$('#positions_jstree').jstree({
        'core': {
                'data': []
        }
        }).bind("select_node.jstree",function(event,data){
                alert(data.node.id);
        });
        
	});
	
		//点击新增按钮
		$("#addUser").click(function(){
				layer.open({
				      type: 2,
				      title: '<span style="color: #ed5565">添加员工</span>',
				      shadeClose: true,
				      shade: false,
				      maxmin: false, //开启最大化最小化按钮
				      area: ['70%', '70%'],
				      content: "${WEB_PATH }/system/user/view/addpersonnel.do",
				      btn: ['确定','取消'],
				      yes: function(index, layero){
					      var body = layer.getChildFrame('body', index);
					      var iframeWin = window[layero.find('iframe')[0]['name']];
					      iframeWin.saveInfo();
					  },
					  cancel: function(){
					  	  $('#addUser').attr("disabled",false); 
						  layer.closeAll();
					  }
				});
		});
		
		//重置密码
		 $("#resetPassWord").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var account = selectRow[0]; 
        	 if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一行您要重置密码的用户。</font>', {icon: 5}); 
			    return ;
	       	} 
    	  	layer.confirm('尊敬的用户您好：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">你确定重置用户为：【'+account.name+'】的密码吗？</span>',{icon: 3, title:'重置密码信息提示',btn: ['确定','取消']},
    		function(){
			  	updateResetPassWord(account.id);
			}, function(){
				
			});
        });
        
        function updateResetPassWord(id){
			 $.yilinAjax({
		   	  	type:'POST',
		   	  	url:'${WEB_PATH }/system/user/resetPassWord.do', 
		   	  	data:{id:id},
        		errorcallback:null,
        		successcallback:successResetPassWord
		   	});
		}
		
		function successResetPassWord(result) {
			layer.closeAll();
			if(result.success == true){
				layer.alert('<font color="red">重置成功</font>', {
					skin: 'layui-layer-molv' ,
					closeBtn: 0,
	 			  	icon: 5
				},function(index){
				   	layer.close(index);
				});
			}else{
				layer.alert('<font color="red">重置失败</font>', {
					skin: 'layui-layer-molv' ,
					closeBtn: 0,
	 			  	icon: 5
				},function(index){
				   	layer.close(index);
				});
			}
		}
		
		//点击编辑按钮
		$("#editUser").click(function(){
			var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
	        	layer.msg('<font color="red">温馨提示：请选择一条要修改的员工信息，在操作。</font>', {icon: 5});
	        	return ;
			}
	       	var user = selectRow[0];  
	       	layer.open({
				      type: 2,
				      title: '<span style="color: #ed5565">编辑员工</span>',
				      shadeClose: true,
				      shade: false,
				      maxmin: false, //开启最大化最小化按钮
				      area: ['70%', '70%'],
				      content: "${WEB_PATH }/system/user/view/editpersonnel.do?userId="+user.id,
				      btn: ['确定','取消'],
				      yes: function(index, layero){
					      var body = layer.getChildFrame('body', index);
					      var iframeWin = window[layero.find('iframe')[0]['name']];
					      iframeWin.editInfo();
					  },
					  cancel: function(){
					  	  $('#editUser').attr("disabled",false); 
						  layer.closeAll();
					  }
			    });
		});
		
		//删除操作
		$("#delUser").click(function(){
			var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(0 == selectRow.length){
				  layer.msg('<font color="red">温馨提示：请选择一条要删除的员工信息，在操作。</font>', {icon: 5});
				  return ;
	        }
	       	var userIds = "";
	       	for(var i=0;i<selectRow.length;i++){
	        		userIds +=  selectRow[i].id +",";
			}
			swal({
				    title: "您确定要删除该员工吗",
				    text: "删除数据，请谨慎操作！",
				    type: "warning",
				    showCancelButton: true,
				    confirmButtonColor: "#DD6B55",
				    confirmButtonText: "是的，我要删除！",
				    cancelButtonText: "让我再考虑一下…",
				    closeOnConfirm: false,
				    closeOnCancel: false
			},function(isConfirm) {
				    if(isConfirm) { 
						$.ajax({
								  type: 'POST',
								  url: '${WEB_PATH }/system/user/del.do',  
								  data: {"userIds":userIds},
								  dataType: "json",
									 success:function(result){  
										if(result.success == true){
											$('#userModal').remove();
											swal("", result.msg, "success");
									    	$("#exampleTableEvents").bootstrapTable('refresh');
										}
									 },
									 error:function(data){
									 	var data = $.parseJSON(data.responseText);
								        swal("", data.msg, "error");
									 }
						});
					}else {
			            swal("已取消", "您取消了删除操作！", "error")
			        }
				});
		});
		
		//点击查询搜索
		$("#search").click(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
		
		//状态格式化
		function formatStatus(val){ 
			if(val == 'start'){
				return '<span class="label label-primary">正常</span>';
			}else if(val == 'stop'){
				return '<span class="label label-default">停用</span>';
			}else if(val == 'cancel'){
				return '<span class="label label-danger">注销</span>';
			}
		}
		
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		function formatAuth(val){
			if(val == 'auth'){
				return '<span class="label label-primary">已认证</span>';
			}else if(val == 'notAuth'){
				return '<span class="label label-default">未认证</span>';
			}else{
				return '<span class="label label-default">未申请认证</span>';
			}
		}
		
		function formatTime(val){
			if(null == val || "" == val){
				return "重未登录";
			}
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
</script>
</body>

</html>