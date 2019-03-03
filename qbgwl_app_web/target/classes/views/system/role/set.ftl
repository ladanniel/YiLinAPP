<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only">关闭</span>
                    </button>
                    <h1 class="modal-title">菜单设置</h1>
                    <div class="modal-body"> 
						<div id="menu_tree" class="test"></div>                          
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
                        <button type="button" class="btn btn-primary" id="saveBut">保存</button>
                    </div>
                </div>
            </div>
        </div> 
    </div>
<script>
 
 $(function(){ 
		$('#menu_tree').jstree({'plugins':["wholerow","checkbox"], 'checkbox': { cascade: "", three_state: false }, 'core' : {
			 'data': ${menujson}
		}}).bind("loaded.jstree", function () {
           $('#menu_tree').jstree("open_all");
        }).bind('click.jstree', function(event) {
           var menuId = $(event.target).parents('li').attr('id');  //获取点击ID
	       var list = $('#menu_tree').jstree().get_children_dom(menuId);
	       console.log(list);
	    });  
		
		
		$('#saveBut').click(function(){
       		var ids=""; 
			var nodes=$('#menu_tree').jstree().get_selected_parent(true); //使用get_checked方法  
			if(nodes.length == 0){
				swal("", '请选择菜单才能保存！', "error");
				return false;
			}
			for(var i = 0;i<nodes.length;i++){
				ids += nodes[i].id+","; 
			} 
			$('#saveBut').attr("disabled",true);
			$.yilinAjax({
		   	  	type:'POST',
		   	  	url:'${WEB_PATH }/system/role/set.do',
		   	  	data:{roleId: '${role.id}', menuIds: ids},
		   	  	btn:$('#saveBut'),
        		errorcallback:null,
        		successcallback:success
	   	    });
       }); 
       //关闭层
       $('#close-but').click(function(){
       		$('#userModal').remove();
       }); 
       $('#close-x').click(function(){
       		$('#userModal').remove();
       }); 
       function success(result){  
			if(result.success == true){
				$('#userModal').remove();
		    	swal("", result.msg, "success");
		    	$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				$('#saveBut').attr("disabled",false);    
				swal("", result.msg, "error");
			}
	   }
  });   
  </script>   
