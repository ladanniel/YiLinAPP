 
<style type="text/css">
#routeMap {width:100%; height:340px;overflow: hidden;margin:0;}
#l-map{height:100%;width:78%;float:left;border-right:2px solid #bcbcbc;}
#r-result{height:100%;width:20%;float:left;}
</style> 

<div class="modal inmodal" id="user-modal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;z-index:999999!important;">
        <div class="modal-dialog " style="width: 75%;">
            <div class="modal-content animated bounceInRight ">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x-more">&times;</span><span class="sr-only" >关闭</span>
                    </button>
					 <div class="panel panel-default">
                        <div class="panel-heading">
                        	<h4 class="panel-title">
				               <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"><font id="routeMapArea">${goodsBasic.deliveryAreaName}&nbsp;&nbsp;${goodsBasic.deliveryAddress}&nbsp;&nbsp;&nbsp;&nbsp;------> &nbsp;&nbsp;&nbsp;&nbsp;${goodsBasic.consigneeAreaName}&nbsp;&nbsp;${goodsBasic.consigneeAddress}</font><font class="text-danger" id="routeMapInfo">正在计算中......</font></a>
				            </h4>
                        </div>
                        <div class="panel-body">
                            <div id="routeMap"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div> 
    </div>
</div>
<script type="text/javascript">
 $(function(){  
 	$('#close-x-more').click(function(){
	     $('#user-modal').remove();
	});
	var pointStarte = "${goodsBasic.deliveryCoordinate}";
	var pointEnd = "${goodsBasic.consigneeCoordinate}";
	var routemap = new BMap.Map("routeMap");
	routemap.centerAndZoom(new BMap.Point(116.404, 39.915), 12);
	routemap.enableScrollWheelZoom();//启动鼠标滚轮缩放地图
	routemap.enableKeyboard();//启动键盘操作地图
	var output = "";
	var searchComplete = function (results){
		if (transit.getStatus() != BMAP_STATUS_SUCCESS){
			return ;
		}
		var plan = results.getPlan(0);
		output += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总路程为：" ;
		output += plan.getDistance(true) + "，";             //获取距离
		output += "预计时间：";
		output += plan.getDuration(true) + "，";                //获取时间
		$('#routeMapInfo').html(output);
	}
	var transit = new BMap.DrivingRoute(routemap, {renderOptions: {map: routemap},
		onSearchComplete: searchComplete,
		onPolylinesSet: function(){        
	}});
	var point_delivery = pointStarte.split(",");
 	var point_consignee = pointEnd.split(",");
 	var point_starte = new BMap.Point(point_delivery[0],point_delivery[1]);
 	var point_end = new BMap.Point(point_consignee[0],point_consignee[1]);
	transit.search(point_starte,point_end);
}); 
</script> 