<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>发货管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <style>
    .widget.style1 h2 {
	    font-size: 40px;
	}
	.widget.style1 h3{
	    font-size: 24px;
	}
	</style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
    	<@confirmStatisticsCount>
        <!-- Panel Other -->
         <div class="row">
            <div class="col-sm-3">
                <div class="widget style1 lazur-bg" >
                    <div class="row">
                        <div class="col-xs-4 text-center">
                            <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe63d;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                            <span> 今日运单量</span>
                            <h2 class="font-bold">${confirmCount.confirmCount.toDayCount?string(',###.##')}/单</h2>
                            <span>${confirmCount.confirmCount.toDayWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="widget style1 navy-bg">
                    <div class="row">
                        <div class="col-xs-4">
                            <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe617;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                            <span>成功运单量</span>
                            <h2 class="font-bold">${confirmCount.confirmCount.ordercompletionCount?string(',###.##')}/单</h2>
                            <span>${confirmCount.confirmCount.ordercompletionWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="widget style1 red-bg">
                    <div class="row">
                        <div class="col-xs-4">
                             <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe616</i>
                        </div>
                        <div class="col-xs-8 text-right">
                            <span>销单运单量</span>
                            <h2 class="font-bold">${confirmCount.confirmCount.singlepinCount?string(',###.##')}/单</h2>
                            <span>${confirmCount.confirmCount.singlepinWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="widget style1 yellow-bg">
                    <div class="row">
                        <div class="col-xs-4">
                            <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe63e;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                            <span>总运单量 </span>
                            <h2 class="font-bold">${confirmCount.confirmCount.allToolCount?string(',###.##')}/单</h2>
                            <span>${confirmCount.confirmCount.allToolWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-2">
                <div class="widget style1 red-bg">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;" >&#xe618;</i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<span>等待装货 （${(confirmCount.confirmCount.receivingCount!0/confirmCount.confirmCount.toolCount!0)?string.percent}）</span>
                            <h3 class="font-bold">${confirmCount.confirmCount.receivingCount?string(',###.##')}/单</h3>
                            <span>${confirmCount.confirmCount.receivingWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 navy-bg" style="background-color: #f8ac59;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;" >&#xe62d;</i> 
                        </div>
                        <div class="col-xs-9 text-right">
                        	<span>运输中（${(confirmCount.confirmCount.transitCount!0/confirmCount.confirmCount.toolCount!0)?string.percent}）</span>
                            <h3 class="font-bold">${confirmCount.confirmCount.transitCount?string(',###.##')}/单</h3>
                            <span>${confirmCount.confirmCount.transitWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 lazur-bg" >
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont"  style="font-size:3em;font-style:normal;" >&#xe63f;</i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<span>送达 （${(confirmCount.confirmCount.deliveredCount!0/confirmCount.confirmCount.toolCount!0)?string.percent}）</span>
                            <h3 class="font-bold">${confirmCount.confirmCount.deliveredCount?string(',###.##')}/单</h3>
                            <span>${confirmCount.confirmCount.deliveredWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 lazur-bg" style="background-color: #00bb9c;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;">&#xe64b;</i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<span>回执发还中（${(confirmCount.confirmCount.receiptCount!0/confirmCount.confirmCount.toolCount!0)?string.percent}） </span>
                            <h3 class="font-bold">${confirmCount.confirmCount.receiptCount?string(',###.##')}/单</h3>
                            <span>${confirmCount.confirmCount.receiptWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 lazur-bg" style="background-color: #1a7bb9;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;">&#xe64f;</i> 
                        </div>
                        <div class="col-xs-9 text-right">
                            <span>送还回执中（${(confirmCount.confirmCount.confirmreceiptCount!0/confirmCount.confirmCount.toolCount!0)?string.percent}） </span>
                            <h3 class="font-bold">${confirmCount.confirmCount.confirmreceiptCount?string(',###.##')}/单</h3>
                            <span>${confirmCount.confirmCount.confirmreceiptWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 red-bg">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;">&#xe64c;</i> 
                        </div>
                        <div class="col-xs-9 text-right">
                            <span>急救（${(confirmCount.confirmCount.emergencyCount!0/confirmCount.confirmCount.toolCount!0)?string.percent}）</span>
                            <h3 class="font-bold">${confirmCount.confirmCount.emergencyCount?string(',###.##')}/单</h3>
                            <span>${confirmCount.confirmCount.singlepinWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
             <div class="col-sm-2">
                <div class="widget style1 red-bg">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;">&#xe64e;</i> 
                        </div>
                        <div class="col-xs-9 text-right">
                            <span>仲裁（${(confirmCount.confirmCount.arbitrationCount!0/confirmCount.confirmCount.toolCount!0)?string.percent}）</span>
                            <h3 class="font-bold">${confirmCount.confirmCount.arbitrationCount?string(',###.##')}/单</h3>
                            <span>${confirmCount.confirmCount.arbitrationWeight!0?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
       </@confirmStatisticsCount>
        
       <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>每月运单统计</h5>
                        <div class="ibox-tools">
                        	 <#if USER.company.companyType.name == "货主" && USER.userType == "company">
	                                                                                              员工：
	                        	<select name="userID" id="userID" style="width:60px;" >
	                        		<option value="" hassubinfo="true" >全部</option>
	                        		 <#list comUserList as info>
					                       <option value="${info.id}" hassubinfo="true" >${info.name}</option>
					                 </#list>
		                        </select>
		                       </#if>
		                         &nbsp;&nbsp;&nbsp;
		                        <#if USER.company.companyType.name == "车队" && USER.userType == "company">
	                     		   司机：
	                        	<select name="turckUserId" id="turckUserId" style="width:60px;" >
	                        		<option value="" hassubinfo="true" >全部</option>
	                        		 <#list userList as info>
					                       <option value="${info.id}" hassubinfo="true" >${info.name}</option>
					                 </#list>
		                        </select>
		                        &nbsp;&nbsp;&nbsp;
		                     </#if>
                        	请选择年份：
                        	<select style="width:60px;"  name="year" id="year">
                        		<#list years as year>
                            		<option value="${year.id}" hassubinfo="true" <#if year.id == yearDay>selected = "selected"</#if>>${year.text}</option>
                            	</#list>
	                        </select>
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                          <div id="echarts-goods-month" class="echarts" style="height: 400px"></div>
                    </div>
                </div>
            </div>
            <#if USER.company.companyType.name == "系统" || USER.company.companyType.name == "管理">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>运单量排名</h5>
                        <div class="ibox-tools">
                        	   请选择排名类型：
                        	<select style="width:80px;"  name="type" id="type">
                            		<option value="company" selected = "selected">货主</option>
                            		<option value="truckCompany" >车队</option>
	                        </select>
	                        &nbsp;&nbsp;&nbsp;
                        	请选择排名数：
                        	<select style="width:60px;"  name="ranking" id="ranking">
                            		<option value="10" >10</option>
                            		<option value="20" >20</option>
	                        </select>
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<div id="echarts-goods-ranking" class="echarts" style="height: 400px"></div>
                    </div>
                </div>
            </div>
           </#if>
        </div
       <!-- End Panel Other -->
      
    </div>
    <@import.tableManagerImportScript/> 
	<script src="${WEB_PATH}/resources/js/plugins/echarts/echarts.js"></script>
	
	<script>
	var echarts_goods_month = null;
	$(function(){
		  initGoodsMonth();
          $('#year').change(function(){ 
          	 initGoodsMonth();
          });
          $('#turckUserId').change(function(){ 
	         initGoodsMonth();
	      });
	       $('#userID').change(function(){ 
	          initGoodsMonth();
	       });
          <#if USER.company.companyType.name == "系统" || USER.company.companyType.name == "管理">
	          $('#ranking').change(function(){ 
	          	 initGoodsRanking();
	          });
	           $('#type').change(function(){ 
	          	 initGoodsRanking();
	          });
	          initGoodsRanking();
         </#if>
	});
	<#if USER.company.companyType.name == "系统" || USER.company.companyType.name == "管理">
		function initGoodsRanking(){
		      var echarts_goods_month = echarts.init(document.getElementById("echarts-goods-ranking"));
			  echarts_goods_month.showLoading({
	                    text: "图表数据正在努力加载..."
	          });
			  $.ajax({
				type: 'GET',
				url: '${WEB_PATH }/order/statistics/confirmRankingStatistics.do',  
				data: {"ranking":$("#ranking").val(),"type":$("#type").val()},
				dataType: "json",
				success:function(result){
					if(result.success){
						var echarts_goods_ranking = echarts.init(document.getElementById("echarts-goods-ranking"));
						var option = initOption_1(result.xAxis,result.counts,result.weights,"运单量统计排名");
						echarts_goods_ranking.setOption(option), 
	        			$(window).resize(echarts_goods_ranking.resize);
					}else{
						layer.msg('<font color="red">统计数据出现问题，请稍后再试！。</font>', {icon: 5}); 
					}
				}
			  });   
		}
	</#if>
	function initGoodsMonth(){
	      var echarts_goods_month = echarts.init(document.getElementById("echarts-goods-month"));
		  echarts_goods_month.showLoading({
                    text: "图表数据正在努力加载..."
          });
		  $.ajax({
			type: 'GET',
			url: '${WEB_PATH }/order/statistics/confirmMonthStatistics.do',  
			data: {"year":$("#year").val(),"turckUserId":$("#turckUserId").val(),"userID":$("#userID").val()},
			dataType: "json",
			success:function(result){
				if(result.success){
					var echarts_goods_month = echarts.init(document.getElementById("echarts-goods-month"));
					var option = initOption(result.xAxis,result.counts,result.orderCompletioncounts,result.weight,result.endWeight,$("#year").val()+"年每月运单量统计");
					echarts_goods_month.setOption(option), 
        			$(window).resize(echarts_goods_month.resize);
				}else{
					layer.msg('<font color="red">统计数据出现问题，请稍后再试！。</font>', {icon: 5}); 
				}
			}
		  });   
	}
	
	
	function initOption_1(xAxis,counts,weights,title){
		var option = {
		    title : {
		        text: title,
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		       data:['运单单数','运单重量']
		    },
		    toolbox: {
		        feature: {
		            dataView: {show: true, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar']}, 
		            saveAsImage: {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : xAxis
		        }
		    ],
		    yAxis : [
			    {
		            type: 'value',
		            name: '运单单数', 
		            axisLabel: {
		                formatter: '{value}单'
		            }
		        },
		        {
		            type: 'value',
		            name: '运单重量', 
		            axisLabel: {
		                formatter: '{value}吨'
		            },
		            splitLine : {
		                show: false
		            }
		        }
		    ],
		    series : [
		        {
		            name:'运单单数',
		            type:'bar',
		            data:counts,
		        },
		        {
		            name:'运单重量',
		            type:'bar',
		            yAxisIndex: 1,
		            data:weights,
		        }
		    ]
		};
		
		return option;
		return option;
	}
	
	
	function initOption(xAxis,counts,orderCompletioncounts,weight,endWeight,title){
		var option = {
		    title : {
		        text: title,
		        subtext: ''
		    },
		    tooltip : {
		        show: true,
		        trigger: 'item'
		    },
		    legend: {
		        data:['总运单','完结运单','待完成重量','完结重量']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : xAxis
		        }
		    ],
		    yAxis : [
		          {
		            type: 'value',
		            name: '单数', 
		            axisLabel: {
		                formatter: '{value}单'
		            }
		        },
		        {
		            type: 'value',
		            name: '重量', 
		            axisLabel: {
		                formatter: '{value}吨'
		            },
		            splitLine : {
		                show: false
		            }
		        }
		    ],
		    series : [
		        {
		            name:'完结运单',
		            type:'bar',
		            stack: '订单',
		            itemStyle: {normal: {color:'rgba(193,35,43,1)', label:{show:true}}},
		            data:orderCompletioncounts
		        },
		        {
		            name:'总运单',
		            type:'bar',
		            stack: '订单',
		            itemStyle: {normal: {color:'rgba(193,35,43,0.5)', label:{show:true,formatter:function(p){return p.value > 0 ? (p.value +'\n'):'';}}}},
		            data:counts
		        },
		        {
		            name:'完结重量',
		            type:'bar',
		            stack: '重量',
		            yAxisIndex: 1,
		            itemStyle: {normal: {color:'rgba(181,195,52,1)', label:{show:true,textStyle:{color:'#27727B'}}}},
		            data:endWeight
		        },
		         {
		            name:'待完成重量',
		            type:'bar',
		            stack: '重量',
		            yAxisIndex: 1,
		            itemStyle: {normal: {color:'rgba(252,206,16,0.5)', label:{show:true,formatter:function(p){return p.value > 0 ? (p.value):'';}}}},
		            data:weight
		        }
		    ]
		};
                    
                    
                    
		
		return option;
	}
    </script>
</body>

</html>