
<#switch status>
  <#case 0>
    <div class="row">
		<div class="col-sm-12">
			<p>司机：${account.name}</p>
			<p>电话：${account.phone}</p>
			<p>订单确认时间：${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</p>
		</div>
	</div>
    <#break>
    <#case 1>
    <div class="col-sm-12">
       <p>装车重量：${robOrderConfirm.actualWeight} (吨)</p>
	   <p>运输时间：${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</p>
	</div>
    <#break>
  <#case 2>
    <div class="col-sm-12">
       <p>实际运输重量：${robOrderConfirm.actualWeight} (吨)</p>
       <p>补交运输费：¥ ${price}</p>
	   <p>运输时间：${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</p>
	</div>
    <#break>
  <#case 3>
    <div class="col-sm-12">
       <p>确认收货人：${goodsBasic.consigneeName}</p>
       <p>联系电话：${goodsBasic.consigneeMobile}</p>
       <p>收货时间：${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</p>
	</div>
    <#break>
   <#case 4>
    <div class="col-sm-12">
       <p>快递公司：${robOrderConfirm.lgisticsName}</p>
       <p>快递单号：${robOrderConfirm.lgisticsNum}</p>
       <p>寄送时间：${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</p>
       <p style=" margin: 0 0 10px;text-align: center;">
       		<a class="btn btn-success btn-outline btn-xs wl">
         		<i class="fa fa-truck"> </i>  查看物流信息  
       		</a>&nbsp;&nbsp;
       		<a  class="btn btn-success btn-outline btn-xs wx">
         		<i class="fa fa-file-o"> </i>  查看回执图片   
       		</a>
        </p>
       
	</div>
    <#break>
      <#case 5>
	    <div class="col-sm-12">
	       <p>回执人员：${account.name}</p>
	       <p>手   机  号：${account.phone}</p>
	       <p style=" margin: 0 0 10px;text-align: center;">
	       		<a class="btn btn-success btn-outline btn-xs receipt">
	         		<i class="fa fa-file-o"> </i>  查看详细
	       		</a>
	        </p>
	         <p>操作时间：${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</p>
		</div>
    <#break>
    
    <#case 6>
	    <div class="col-sm-12">
	      <p>运输费：¥ ${money}</p>
	      <p>支付时间：${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</p>
		</div>
    <#break>
    <#case 7>
	    <div class="col-sm-12">
	     <p>操作人员：${log.auditPerson}</p>
	     <p>销单时间：${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</p>
		</div>
    <#break>
  <#default>
    
</#switch>