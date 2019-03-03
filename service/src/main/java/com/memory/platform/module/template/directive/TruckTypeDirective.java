package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.truck.TruckType;
import com.memory.platform.module.truck.service.ITruckTypeService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月16日 上午10:45:26 
* 修 改 人： 
* 日   期： 
* 描   述： 车辆类型数据指令
* 版 本 号：  V1.0
 */
@Component("truckTypeDirective")
public class TruckTypeDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "truckTypeViews";

	
	@Autowired
	private ITruckTypeService truckTypeService;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		List<TruckType> list = truckTypeService.getTruckTypeList();
		setLocalVariable(VARIABLE_NAME, list, env, body);
	}

}
