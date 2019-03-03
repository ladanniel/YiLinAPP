package com.memory.platform.module.system.controller;

//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.repository.ProcessDefinition;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
//import com.yinzhi.platform.module.system.service.IResourceService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memory.platform.module.global.controller.BaseController;

@Controller
@RequestMapping("/system/flow")
public class FlowController extends BaseController{
//	@Autowired
//	private IResourceService resourceService;
//	
//	@Autowired
//	protected RepositoryService repositoryService;
//	
//	@Autowired
//	private RuntimeService runtimeService;
//	
//	
//	/**
//	 * 发布流程
//	 * 
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("publish")
//	@ResponseBody
//	public Map<String, Object> publish(){
//		repositoryService.createDeployment().addClasspathResource("flows/bszx.bpmn20.xml").addClasspathResource("flows/bszx.png").deploy();
//		
//		return jsonView(true, "发布成功！");
//	}
//	
//	
//	/**
//	 * 流程定义列表
//	 * @return
//	 */
//	@RequestMapping("getProcessList")
//	@ResponseBody
//	public List<Map<String, Object>> getProcessList() {
//		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
//		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
//		
//		for(ProcessDefinition pro: list){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("id", pro.getId());
//			map.put("deploymentId", pro.getDeploymentId());
//			map.put("name", pro.getName());
//			map.put("key", pro.getKey());
//			map.put("version", pro.getVersion());
//			map.put("resourceName", pro.getResourceName());
//			map.put("diagramResourceName", pro.getDiagramResourceName());
//			
//			results.add(map);
//		}
//		return results;
//	}
//	
//	
//	/**
//	 * 显示流程图
//	 * @param processInstanceId
//	 * @param taskExecutionId
//	 * @param response
//	 * @throws IOException
//	 */
//	@RequestMapping("/flowImg")
//	public void flowImg(String processInstanceId, String taskExecutionId,
//			HttpServletResponse response) throws IOException {
//		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
//				.singleResult();
//		
//		
//		ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();  
//        String diagramResourceName = procDef.getDiagramResourceName();  
//        
//       // ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
//        //InputStream imageStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png",  runtimeService.getActiveActivityIds(taskExecutionId)); 
//        InputStream imageStream = repositoryService.getResourceAsStream(procDef.getDeploymentId(), diagramResourceName); 
//        byte[] b = new byte[1024];
//		int len = -1;
//		while ((len = imageStream.read(b, 0, 1024)) != -1) {
//			response.getOutputStream().write(b, 0, len);
//		}
//	}
}
