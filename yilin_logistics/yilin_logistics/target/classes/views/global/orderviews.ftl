<div class="row" style="margin-top: 13px">
    <div class="col-sm-12">
    	<table class="table table-bordered">
        	<thead>
            	<tr>
            		 <th data-toggle="true">载货车辆</th>
            		 <th>实际载重</th>
                     <th>货物名称</th>
                     <th>长度/米</th>
                     <th>高度/米</th>
                     <th>直径/cm</th>
                     <th>翼宽</th>               
               	</tr>
           </thead>
           <tbody>
               <#list list as vo>
                   <tr>
                   		<td><#if null == vo.goodsDetail.goodsName><font color="red">无</font><#else>${vo.goodsDetail.goodsName}</#if></td>
                   		<td><#if null == vo.goodsDetail.goodsName><font color="red">无</font><#else>${vo.goodsDetail.goodsName}</#if></td>
	                    <td><#if null == vo.goodsDetail.goodsName><font color="red">无</font><#else>${vo.goodsDetail.goodsName}</#if></td>
	                    <td><#if null == vo.goodsDetail.length><font color="red">无</font><#else>${vo.goodsDetail.length}</#if></td>
	                    <td><#if null == vo.goodsDetail.heigh><font color="red">无</font><#else>${vo.goodsDetail.height}</#if></td>
	                    <td><#if null == vo.goodsDetail.diameter><font color="red">无</font><#else>${vo.goodsDetail.diameter}</#if></td>
	                    <td><#if null == vo.goodsDetail.wingWidth><font color="red">无</font><#else>${vo.goodsDetail.wingWidth}</#if></td>
	              </tr>
               </#list>                    
           </tbody>                     	
        </table>
   	</div>
</div>