<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog" style="width:60%">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title" style="text-align: center;">编辑联系人</h1>
                    <form class="form-horizontal m-t" id="addInfo">
                    <input type="text" name="id" hidden="true" value="${vo.id}"/>
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>姓名：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name"  type="text"  class="form-control"  required="" aria-required="true" maxlenth="16" value="${vo.name}">
                                </div>
                            </div> 
                            <div class="form-group">
                            	<label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>地区：</label>
                                <div class="col-sm-8">
                                    <input id="areaFullName" name="areaFullName" class="form-control" readonly type="text" value="${vo.areaFullName}">
                                </div>
                            </div> 
                            <div class="form-group">
                            	 <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>详细地址：</label>
                                <div class="col-sm-8">
                                   <input id="address" name="address"  type="text"  class="form-control" value="${vo.address}"  maxlenth="16">
                                   <input id="areaId" name="areaId" type="hidden" >
                                </div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>电话：</label>
                                <div class="col-sm-8">
                                    <input id="mobile" name="mobile"  type="text"  class="form-control"  required="" value="${vo.mobile}" aria-required="true" maxlenth="16">
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">email：</label>
                                <div class="col-sm-8">
                                    <input id="email" name="email"  type="text"  class="form-control" value="${vo.email}" maxlenth="32">
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>联系人类型：</label>
                                <div class="col-sm-8">
                                    <select class="form-control" name="contactsType" id="contactsType">
                                    	<option value="consignee" <#if vo.contactsType == "consignee">selected</#if>>收货人</option>
                                    	<option value="consignor" <#if vo.contactsType == "consignor">selected</#if>>发货人</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">联系人位置：</label>
								 <div class="col-sm-8">
								 	<a href="javascript:void(0)" class="various3"><span class="form-control-static fa green fa-map-marker">查看地图</span></a>
								 	<input id="point" name="point" hidden="true" value="${vo.point}"/>
								 	<div id="allmap1" hidden="true"></div>
								 </div>
								<div id="maptest"></div>
                            </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
                        <button type="submit" class="btn btn-primary" id="saveBut">保存</button>
                    </div>
                 	</form> 
                </div>
            </div>
        </div> 
    </div>
<script src="${WEB_PATH}/resources/js/city-picker.data.js"></script>
<script src="${WEB_PATH}/resources/js/city-picker.js"></script>
 <script>
 $.validator.setDefaults({
    highlight: function(e) {
        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
    },
    success: function(e) {
        e.closest(".form-group").removeClass("has-error").addClass("has-success")
    },
    errorElement: "span",
    errorPlacement: function(e, r) {
        e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
    },
    errorClass: "help-block m-b-none",
    validClass: "help-block m-b-none"
}),
 $(function(){  
 		$('#areaFullName').citypicker({areaId:'area_id'}); 
 		var e = "<i class='fa fa-times-circle'></i> ";
 		$("#addInfo").validate({ 
 			rules: {
		        	name: {
		                required: !0,
		            },
		            mobile:{
		            	required: !0,
		            	isMobile: !0
		            },
		            email:{
		            	email: !0
		            }
		        }
		});
        $('#saveBut').click(function(){
        	if($("#addInfo").valid()){ 
			   	  $('#saveBut').attr("disabled",true); 
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/goods/contacts/edit.do',  
					  data: $('#addInfo').serialize(),
					  dataType: "json",
						 success:function(result){  
							if(result.success == true){
								$('#userModal').remove();
								swal("", result.msg, "success");
						    	$("#exampleTableEvents").bootstrapTable('refresh');
							}
						 },
						 error:function(data){
						 	$('#saveBut').attr("disabled",false); 
						 	var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
						 }
					});
        	 }
        });
       //关闭层
       $('#close-but').click(function(){
       		$('#userModal').remove();
       }); 
       $('#close-x').click(function(){
       		$('#userModal').remove();
       }); 
       
       $(".various3").click(function(){
       		var areaFullName = $("#areaFullName").val();
       		var  address = $("#address").val();
       		if(null == areaFullName || "" == areaFullName || null == address || "" == address){
       			layer.msg('<font color="red">温馨提示：请选择地区并填写详细地址后查看地图。</font>', {icon: 5}); 
       			return;
       		}
       		$("#maptest").load("${WEB_PATH }/goods/contacts/view/gis.do");
       });
       
       $("#address").blur(function(){
			var map = new BMap.Map("allmap1");
			var point = new BMap.Point(26.574155,106.720628);
			map.centerAndZoom(point,12);
			var text = $("#areaFullName").val().replace("-","") + $("#address").val();
			// 创建地址解析器实例
			var myGeo = new BMap.Geocoder();
			myGeo.getPoint(text, function(point){
				if (point) {
					map.centerAndZoom(point, 16);
					map.addOverlay(new BMap.Marker(point));
					$("#point").val(point.lng+","+point.lat);
					$("#maptest").load("${WEB_PATH }/goods/contacts/view/gis.do");
				}
			}, "贵州省");
		});
   
  });   
  </script>   
