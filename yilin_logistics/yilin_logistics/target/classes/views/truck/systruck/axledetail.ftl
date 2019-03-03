
<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog" style="width: 80%;">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">[${truckAxle.truck.track_no}]---轮轴属性</h1>
                    <div class="modal-body" style="max-height:450px;overflow-y: auto;"> 
						  	<div class="row" >
							  	<div class="col-sm-12">
							        <div class="tabs-container">
							            <ul class="nav nav-tabs">
							            	<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">车辆轮轴信息</a> </li>
							            </ul>
							            <div class="tab-content">
								                <div id="tab-1" class="tab-pane active">
								                    <div class="ibox-content">
								                    	<div class="row" style="font-size: 14px;">
									                        <div  class="col-sm-7" style="padding-left: 3%;">
									                            <p><i class="fa green fa-file-text"></i><strong>车牌号：</strong><span>${truckAxle.truck.track_no}</span></p>
									                            <p><i class="fa green fa-file-text"></i> <strong>轮轴类型：</strong><span>${truckAxle.axleType.name}</span></p>
									                            <p><i class="fa green fa-file-text"></i> <strong>车辆轴数：</strong><span>${truckAxle.bearingNum.name}</span></p>
								                            </div>
							                            </div>
							                        </div>
								                </div>
							            </div>
							        </div>
							    </div>
							</div>
                    </div>
                </div>
            </div>
        </div> 
    </div>
 <script>
 $(function(){ 
 	 $(".fancybox").fancybox({openEffect:"none",closeEffect:"none"}); 
    $('#close-x').click(function(){
   		$('#userModal').remove();
    }); 
});
 
  </script>   
