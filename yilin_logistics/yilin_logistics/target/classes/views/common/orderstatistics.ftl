<!-- 订单统计-->
<#macro order >
	<style>
	.label-primary-red {
	    background-color: #c23531;
	    color: #FFF;
	}
	
	.label-primary-2 {
	    background-color: #2f4554;
	    color: #FFF;
	}
	</style>
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>运单统计</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-7">
                                 <div style="width: 100%;height:330px;padding-left:25px;" id="flot-dashboard-chart"></div>
                            </div>
                            <div class="col-sm-5">
                            	<div style="width: 100%;height:330px;padding-left:25px;" id="echarts-order"></div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        <script>
        	$(function(){
        		<@robOrderConfirm >
	        	option = {
				    title : {
				        text: '运单状态',
				        subtext: '',
				        x:'center'
				    },
				     color: [ '#C1232B', '#B5C334', '#FCCE10',
									'#E87C25', '#76EE00', '#FE8463', '#9BCA63',
									'#FAD860', '#F3A43B', '#60C0DD', '#D7504B',
									'#C6E579', '#F4E001', '#F0805A', '#26C0C0',
									'#FF00FF', '#C67171', '#97FFFF', '#3CB371'],
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c}"
				    },
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ${map.legendData}
				    },
				    series : [
				        {
				            name: '运单状态',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data:${map.seriesData},
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                }
				            }
				        }
				    ]
				};
			
				
        		 var l = echarts.init(document.getElementById("echarts-order"));
        		 l.setOption(option);
       			 $(window).resize(l.resize);
       			 
       			 
       			option_1 = {
					    title : {
					        text: '近一周运单统计',
					        subtext: ''
					    },
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:['运单量']
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            mark : {show: true},
					            dataView : {show: true, readOnly: false},
					            magicType : {show: true, type: ['line', 'bar']},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    xAxis : [
					        {
					            type : 'category',
					            data : ${map.xAxis}
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					
					        {
					            name:'运单量',
					            type:'bar',
					            data:${map.yseriesData},
					        }
					    ]
					};
              
				
				 var cc = echarts.init(document.getElementById("flot-dashboard-chart"));
        		 cc.setOption(option_1);
       			 $(window).resize(cc.resize);
       	      </@robOrderConfirm>
        	})
        </script>
</#macro>
<!-- 订单统计 -->
 
 