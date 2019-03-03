<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">新增APP资源</h1>
                    <form class="form-horizontal" id="addMenuAppInfo">
	                    <div class="modal-body"> 
	                    		<div class="form-group">
	                    			<label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>类型：</label>
	                                <div class="col-sm-8">
	                                 <select class="form-control" name="type">
				                          <option value="Native">原生</option>
				                          <option value="Html5">HTML5</option> 
	                                 </select> 
	                                </div> 
	                            </div>
	                            <div class="form-group">
	                    			<label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>应用类型：</label>
	                                <div class="col-sm-8">
	                                 <select class="form-control" name="appType">
				                          <option value="MoneyManager">资金管理</option>
				                          <option value="SupplyManager">货源管理</option> 
	                                 </select> 
	                                </div> 
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>APP资源名称：</label>
	                                <div class="col-sm-8">
	                                    <input id="name" name="name" minlength="2" type="text"  class="form-control"  required="" aria-required="true">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-4 control-label">资源图标：</label>
	                                <div class="col-sm-8">
	                                    <input type="text" id="iconcls" name="iconcls" class="form-control"   >
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-4 control-label">资源图标颜色：</label>
	                                <div class="col-sm-8">
	                                    <input id="iconclsColor" name="iconclsColor" class="form-control"   >
	                                </div>
	                            </div> 
	                            <div class="form-group">
	                                <label class="col-sm-4 control-label">资源地址：</label>
	                                <div class="col-sm-8">
	                                    <input id="path" name="path" class="form-control"  >
	                                </div>
	                            </div> 
	                    </div>
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
	                        <button type="button" class="btn btn-primary" id="saveMenuAppBut">保存</button>
	                    </div>
                 	</form> 
                </div>
            </div>
        </div> 
    </div>
 <script> 
 $(function(){   
    var $addMenuAppInfo =  $('#addMenuAppInfo').validate({
	  rules: {
	    type: {
	      required: true
	    },
	    name: {
	      required: true
	    },
	    path: {
	      required: true
	    },
	    appType:{
	      required: true
	    }
	  } 
	});
    $('#saveMenuAppBut').click(function(){
    	var $valid = $("#addMenuAppInfo").valid();
  		if($valid) {
	   	  $('#saveBut').attr("disabled",true);   
	   	  $.yilinAjax({
		   	  	type:'POST',
		   	  	url:'${WEB_PATH }/system/menuapp/add.do',
		   	  	data:$('#addMenuAppInfo').serialize(),
        		errorcallback:null,
        		successcallback:success
	   	  }); 
	   	}
		var e = "<i class='fa fa-times-circle'></i> ";
    });
    //关闭层
    $('#close-but').click(function(){
   		$('#userModal').remove();
    }); 
    $('#close-x').click(function(){
   		$('#userModal').remove();
    }); 
  });   
  
  function success(result){
    if(result.success == true){
		$('#userModal').remove();
		swal("", result.msg, "success");
    	$("#menuAppTableEvents").bootstrapTable('refresh');
	}else{
		$('#saveBut').attr("disabled",false);    
		swal({
	        title: "操作失败",
	        text: result.msg
    	})
	}
 } 
  </script>   
