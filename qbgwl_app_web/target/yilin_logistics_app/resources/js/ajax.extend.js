
(function($){
	$.fn.serializeJson=function(){ 
        var serializeObj={}; // 目标对象 
        var array=this.serializeArray(); // 转换数组格式 
        // var str=this.serialize(); 
        $(array).each(function(){ // 遍历数组的每个元素 {name : xx , value : xxx} 
                if(serializeObj[this.name]){ // 判断对象中是否已经存在 name，如果存在name 
                      if($.isArray(serializeObj[this.name])){ 
                             serializeObj[this.name].push(this.value); // 追加一个值 hobby : ['音乐','体育'] 
                      }else{ 
                              // 将元素变为 数组 ，hobby : ['音乐','体育'] 
                              serializeObj[this.name]=[serializeObj[this.name],this.value]; 
                      } 
                }else{ 
                        serializeObj[this.name]=this.value; // 如果元素name不存在，添加一个属性 name:value 
                } 
        }); 
        return serializeObj; 
	};  
	$.extend({
        yilinAjax: function (options) {
            var defaults = {
            	type:"Charles",
            	url:"22",
            	data:null,
            	cache:true,
            	btn:null,
            	errorcallback:null,
            	successcallback:null
            	
            };
            var opts = $.extend(defaults, options);
            $.ajax({
				  type: options.type,
				  url: options.url,  
				  data: options.data,
				  cache: options.cache != null ? options.cache:defaults.cache,
				  dataType: "json",
				  success:function(result){ 
					 if(typeof options.successcallback === 'function'){
						 options.successcallback(result); // 给callback赋值，callback是个函数变量
					 }
				  },
				  error: function(XMLHttpRequest) {  //#3这个error函数调试时非常有用，如果解析不正确，将会弹出错误框
					  if(typeof options.errorcallback === 'function' && options.errorcallback != undefined){
						  options.errorcallback(XMLHttpRequest); // 给callback赋值，callback是个函数变量
					  }else{
						  if(XMLHttpRequest.status == "403"){
	                        	swal({
							        title: "操作失败",
							        text: "没有权限！"
						    	});
	                      }
						  if(XMLHttpRequest.status == "500"){
							    var data = $.parseJSON(XMLHttpRequest.responseText);
	                        	swal({
							        title: "操作失败",
							        text: "系统出现错误，错误信息："+data.msg
						    	});
	                      }
					  }
					  if(options.btn != undefined && options.btn != null){
						  options.btn.attr("disabled",false);    
					  }
	             }
			});
        }
    });
	
	
	$.extend({
        yilinvalidator: function (value) {
             alert(value);
        }
    });
	
})(jQuery);