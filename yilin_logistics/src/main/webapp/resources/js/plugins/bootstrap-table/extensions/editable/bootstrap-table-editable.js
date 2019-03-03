/**
 * @author zhixin wen <wenzhixin2010@gmail.com>
 * extensions: https://github.com/vitalets/x-editable
 */

!function ($) {

    'use strict';

    $.extend($.fn.bootstrapTable.defaults, {
        editable: true,
        onEditableInit: function () {
            return false;
        },
        onEditableSave: function (field, row, oldValue, $el) {
            return false;
        },
        onEditableShown: function (field, row, $el, editable) {
            return false;
        },
        onEditableHidden: function (field, row, $el, reason) {
            return false;
        }
    });

    $.extend($.fn.bootstrapTable.Constructor.EVENTS, {
        'editable-init.bs.table': 'onEditableInit',
        'editable-save.bs.table': 'onEditableSave',
        'editable-shown.bs.table': 'onEditableShown',
        'editable-hidden.bs.table': 'onEditableHidden'
    });

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initTable = BootstrapTable.prototype.initTable,
        _initBody = BootstrapTable.prototype.initBody;

    BootstrapTable.prototype.initTable = function () {
        var that = this;
        _initTable.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.editable) {
            return;
        }

        $.each(this.columns, function (i, column) {
            if (!column.editable) {
                return;
            } 
            var editableOptions = {}, editableDataMarkup = [], editableDataPrefix = 'editable-';

            var processDataOptions = function(key, value) {
              // Replace camel case with dashes.
              var dashKey = key.replace(/([A-Z])/g, function($1){return "-"+$1.toLowerCase();});
              if (dashKey.slice(0, editableDataPrefix.length) == editableDataPrefix) {
                var dataKey = dashKey.replace(editableDataPrefix, 'data-');
                editableOptions[dataKey] = value;
              }
            };

            $.each(that.options, processDataOptions);

            var _formatter = eval(column.formatter);
            column.formatter = function (value, row, index) {
                var result = _formatter ? _formatter(value, row, index) : value;
                
                $.each(column, processDataOptions);

                $.each(editableOptions, function (key, value) {
                    editableDataMarkup.push(' ' + key + '="' + value + '"');
                });
				if(null != result && typeof(result) == 'object'){
				    if(!result.editable){
				    	return  result.value;
				    }    	
				}else{
					return ['<a href="javascript:void(0)"',
		                    ' data-name="' + column.field + '"',
		                    ' data-type="' + column.type + '"',
		                    ' data-value="' + result + '"',
		                    ' data-source="' + column.source + '"',
		                    editableDataMarkup.join(''),
		                    '>' + '</a>'
		                ].join('');
				}
            };
        });
    };

    BootstrapTable.prototype.initBody = function () {
        var that = this;
        _initBody.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.editable) {
            return;
        }

        $.each(this.columns, function (i, column) {
            if (!column.editable) {
                return;
            }
           
            that.$body.find('a[data-name="' + column.field + '"]').editable(column.editable)
                .off('save').on('save', function (e, params) {
                    var data = that.getData(),
                        index = $(this).parents('tr[data-index]').data('index'),
                        row = data[index],
                        oldValue = row[column.field];
                    if(typeof(column.savemethod) != "undefined" ){
        				var data = that.getData(),
                        index = $(this).parents('tr[data-index]').data('index'),
                        row = data[index],
                        oldValue = row[column.field];
        				var fun = eval(column.savemethod);
        				fun(column.field,params.submitValue,row);
                	}
                    if(column.save == "service"){
	                    var updatecolume = new Object();
	                    updatecolume.id= row.id;
	                    updatecolume.field= column.field;
	                    updatecolume.field_value= params.submitValue;
	                    updateColumn(updatecolume);
                    }else{
                    	$(this).data('value', params.submitValue);
                        row[column.field] = params.submitValue;
                        that.trigger('editable-save', column.field, row, oldValue, $(this));
                    }
                    
                });
            that.$body.find('a[data-name="' + column.field + '"]').editable(column.editable)
                .off('shown').on('shown', function (e, editable) {
                    var data = that.getData(),
                        index = $(this).parents('tr[data-index]').data('index'),
                        row = data[index];
                    
                    that.trigger('editable-shown', column.field, row, $(this), editable);
                });
            that.$body.find('a[data-name="' + column.field + '"]').editable(column.editable)
                .off('hidden').on('hidden', function (e, reason) {
                    var data = that.getData(),
                        index = $(this).parents('tr[data-index]').data('index'),
                        row = data[index];
                    
                    that.trigger('editable-hidden', column.field, row, $(this), reason);
                });
            
            that.$body.find('a[data-name="' + column.field + '"]').editable('option', 'validate', function(v, params) {
        		var  result = BootstrapTable.prototype.validator(v,column.validate)
        		if(!result.isfalse){
        			return result.msg;
        		}else{
        			if(  typeof(column.validatemethod) != "undefined" ){
        				var data = that.getData(),
                        index = $(this).parents('tr[data-index]').data('index'),
                        row = data[index],
                        oldValue = row[column.field];
        				var fun = eval(column.validatemethod);
        				return fun(row,v);
                	}
        		}
            });
        });
        this.trigger('editable-init');
    };

    BootstrapTable.prototype.validator = function (value,validate) {
    	  var validates =  validate.split(",")
    	  return BootstrapTable.prototype.validator_value(value,validates);
    };
    
    BootstrapTable.prototype.validator_value = function (value,validates) {
    	var  result = new Object();
    	result.isfalse = true;
    	result.msg = "";
    	for(var key in validates){
    		switch(validates[key]){
        	case "required":
        	  var  isfalse =  $.trim(value).length > 0 ? true:false;
        	  if(isfalse){
        		  break;
        	  }else{
        		  result.isfalse = isfalse;
            	  result.msg = "必填!";
            	  return result;
        	  }
        	  
        	case "email":
          	  var  isfalse =  /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(value);
          	  if(isfalse){
        		  break;
        	  }else{
        		  result.isfalse = isfalse;
              	  result.msg = "请输入有效的电子邮件!";
              	  return result;
        	  }
          	 
        	case "url":
        	  var  isfalse =  /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
        	  if(isfalse) {
        		  break;
        	  }else{
        		  result.isfalse = isfalse;
            	  result.msg = "请输入有效的网址!";
            	  return result;
        	  }
        	  
        	case "date":
          	  var  isfalse =  /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/.test(value);
          	  if(isfalse) {
		  		  break;
		  	  }else{
		  		  result.isfalse = isfalse;
	          	  result.msg = "请输入有效的日期 (YYYY-MM-DD)!";
		      	  return result;
		  	  }
        	case "number":
        	  var  isfalse =  /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(value);
        	  if(isfalse){
        		  break;
        	  }else{
        		  result.isfalse = isfalse;
            	  result.msg = "请输入正确的数字!";
            	  return result;
        	  }
        	 
        	case "digits":
          	  var  isfalse =  /^\d+$/.test(value);
          	  if(isfalse){
        		  break;
        	  }else{
        		  result.isfalse = isfalse;
              	  result.msg = "只能输入数字!";
        		  return result;
        	  }
          	  
        	case "isChinese":
        	  var  isfalse =  /^[\u4e00-\u9fa5]+$/.test(value);
        	  if(isfalse){
        		  break;
        	  }else{
        		  result.isfalse = isfalse;
            	  result.msg = "只能输入汉字!";
            	  return result;
        	  }
        	  
        	case "isFloatGtZero":
        		value=parseFloat(value);      
                if(value <= 0){
                	result.isfalse = false;
               	  	result.msg = "数值必须大于0!";
               	  	return result;
                }else{
                	break;
                }    
          	  
        	case "isMobile":
        	  var length = value.length;  
          	  var  isfalse =  (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
          	  if(isfalse){
        		  break;
        	  }else{
        		  result.isfalse = isfalse;
             	  result.msg = "请正确填写您的手机号码!";
             	  return result
        	  }
        	default:
        	}
        }
    	return result;
    	
   };
}(jQuery);
