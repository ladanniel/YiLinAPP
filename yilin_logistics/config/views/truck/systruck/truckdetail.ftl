<div style="" class="truckaxle ui-sortable"> 
    <div class="row">
	   <div class="col-sm-6" style="padding-left: 3%;">
		  <p><i class="fa green fa-truck"></i> <strong>车牌号：</strong><span>${truck.track_no}</span></p>
		  <p><i class="fa green fa-truck"></i> <strong>轮轴类型：</strong><span><#if truckAxle??><font color="red">无</font><#else>${truckAxle.axleType.name}</#if></span></p>
		  <p><i class="fa green fa-truck"></i> <strong>车辆轴数：</strong><span><#if truckAxle??><font color="red">无</font><#else>${truckAxle.bearingNum.name}</#if></span></p> 
	   </div>
	</div>
</div>
<div style="" class="truckcontainer ui-sortable"> 
	<div class="row">
	   <div  class="col-sm-6" style="padding-left: 3%;">
		<p><i class="fa green fa-truck"></i> <strong>车牌号：</strong><span>${truck.track_no}</span></p>
	   </div>
	</div> 	
    <div class="row">
	   <div  class="col-sm-6" style="padding-left: 3%;">
		<p><i class="fa green fa-truck"></i> <strong>货箱形式：</strong><span><#if truckContainer??><font color="red">无</font><#else>${truckContainer.containersType.name}</#if></span></p>
		<p><i class="fa green fa-truck"></i> <strong>货箱长度：</strong><span><#if truckContainer??><font color="red">无</font><#else>${truckContainer.containers_long}米</#if></span></p>
	  </div>
	  <div  class="col-sm-6" style="padding-left: 3%;">
		<p><i class="fa green fa-truck"></i> <strong>货箱宽度：</strong><span><#if truckContainer??><font color="red">无</font><#else>${truckContainer.containers_width}米</#if></span></p>
		<p><i class="fa green fa-truck"></i> <strong>货箱高度：</strong><span><#if truckContainer??><font color="red">无</font><#else>${truckContainer.containers_height}米</#if></span></p>
	  </div>
	</div>
</div>








