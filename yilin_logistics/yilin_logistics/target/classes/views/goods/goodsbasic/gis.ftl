<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
#allmap {width:100%; height:470px;overflow: hidden;margin:0;}
#l-map{height:100%;width:78%;float:left;border-right:2px solid #bcbcbc;}
#r-result{height:100%;width:20%;float:left;}
.myclass{ height:50px; width:150px; font-size:12px; line-height:22px;}
.bigdiv{width:100%; height:100%; margin:0 auto;}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=B3f7707c25da5b29a6ff69618788a296"></script>
<title>百度地图api，并给标注动画</title>
</head>
<body>

<div class="modal inmodal" id="user-modal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;z-index:999999!important;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x-more">&times;</span><span class="sr-only" >关闭</span>
                    </button>
					<div id="allmap"></div>
                </div>
            </div>
        </div> 
    </div>
</div>
<script type="text/javascript">
	$('#close-x-more').click(function(){
	     $('#user-modal').remove();
	     var consigneeCoordinate = $('#consigneeCoordinate').val();
	     var deliveryCoordinate = $('#deliveryCoordinate').val();
	     if(consigneeCoordinate != "" && deliveryCoordinate != ""){	
	     	mapDrivingRoute(deliveryCoordinate,consigneeCoordinate);
	     }
	});
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point_value = $("#${pointId}").val();
	var arr = null;
	if(null == point_value || "" == point_value){
		var point_val = "${point}";
		arr = point_val.split(",");
	}else{
		arr = point_value.split(",");
	}
	var point = new BMap.Point(arr[0],arr[1]);
	map.centerAndZoom(point,16);
	map.enableScrollWheelZoom();//启动鼠标滚轮缩放地图
	map.enableKeyboard();//启动键盘操作地图
	map.addOverlay(new BMap.Marker(point));
	if(null == point_value || "" == point_value){
		// 创建地址解析器实例
		var myGeo = new BMap.Geocoder();
		var text = $("#${areaName}").val().replace("-","") + $("#${addressId}").val();
		// 将地址解析结果显示在地图上,并调整地图视野
		myGeo.getPoint(text, function(point){
			if (point) {
				map.centerAndZoom(point, 16);
				map.addOverlay(new BMap.Marker(point));
				$("#${pointId}").val(point.lng+","+point.lat);
			}else{
				layer.msg('<font color="red">您选择地址没有解析到结果,请手动选择地址！。</font>', {icon: 5,time: 2000});
			}
		}, "贵州省");
	}
	
	map.addEventListener("click", function(e){
		map.clearOverlays();
		var point = e.point;
		map.enableScrollWheelZoom();//启动鼠标滚轮缩放地图
		map.enableKeyboard();//启动键盘操作地图
		map.addOverlay(new BMap.Marker(point));
		$("#${pointId}").val(point.lng+","+point.lat);
	});
	
</script>
</body>
</html>