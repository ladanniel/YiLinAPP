package com.memory.platform.global;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

/**
 * 工作流工具类
 *  
 * 
 */
public class WFUtil {

	private static Logger logger = Logger.getLogger(WFUtil.class);

	private static ProcessEngine processEngine = ProcessEngines
			.getDefaultProcessEngine();

	/**
	 * 发布流程 用于将画好的流程相关存入数据库
	 */
	public static void deploy() {
		logger.debug("开始发布流程");
		RepositoryService repositoryService = processEngine
				.getRepositoryService();
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("com/memory/platform/workFlow/LeaveFlow.bpmn")
				.deploy();
		logger.debug(deployment);
		logger.debug("流程发布成功");
	}

	/**
	 * 创建流程实例， 即xml文件中的process id="Leave"
	 */
	public static ProcessInstance startInstanceByKey(String instanceByKey) {
		logger.debug("启用流程实例");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		logger.debug("流程已经可以应用");
		ProcessInstance instance = runtimeService
				.startProcessInstanceByKey(instanceByKey);
		logger.debug("已返回流程实例instance,id为:" + instance.getId());
		logger.debug("获取instance：" + instance);
		logger.debug("获取instance_id：" + instance.getId());
		logger.debug("获取instance_ProcessDefinitionId："
				+ instance.getProcessDefinitionId());
		logger.debug("获取instance_ProcessInstanceId："
				+ instance.getProcessInstanceId());
		logger.debug("获取instance_BusinessKey：" + instance.getBusinessKey());

		return instance;
	}

	/**
	 * 获取流程信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public static ActivityImpl getProcessMap(String procDefId,
			String executionId) throws Exception {
		logger.debug("获取流程定义信息:" + procDefId);
		logger.debug("获取流程实例信息:" + executionId);
		RepositoryService repositoryService = processEngine
				.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(procDefId)
				.singleResult();
		logger.debug("查看流程定义名称:" + processDefinition.getName());
		ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;
		String processDefinitionId = pdImpl.getId();
		logger.debug("流程标识 :" + processDefinitionId);
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		ActivityImpl actImpl = null;

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createExecutionQuery().executionId(executionId).singleResult();
		// 执行实例

		String activitiId = execution.getActivityId();// 当前实例的执行到哪个节点
		logger.debug("当前执行节点id:" + activitiId);
		List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点
		logger.debug("当前任务的所有节点个数:" + activitiList.size());
		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (id.equals(activitiId)) {// 获得执行到那个节点
				actImpl = activityImpl;
				break;
			}
		}
		logger.debug(actImpl);
		return actImpl;
	}

	/**
	 * 获取流程图并 显示
	 * 
	 * @return
	 * @throws Exception
	 */
	public static InputStream findProcessPic(String procDefId) throws Exception {
		RepositoryService repositoryService = processEngine
				.getRepositoryService();
		ProcessDefinition procDef = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(procDefId)
				.singleResult();
		String diagramResourceName = procDef.getDiagramResourceName();
		InputStream imageStream = repositoryService.getResourceAsStream(
				procDef.getDeploymentId(), diagramResourceName);
		return imageStream;
	}

	/**
	 * 根据执行人查询任务
	 * 
	 * @author www.itxxz.com
	 * @return
	 */
	public static List<Task> findTaskByAssignee(String assignee) {
		logger.debug("开始查看任务...");
		/*
		 * TaskService taskService = (TaskService) CommonUtil
		 * .getBean("taskService");
		 */
		TaskService taskService = processEngine.getTaskService();
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();
		//List<Task> taskList = taskService.createTaskQuery().processInstanceId("45001").list();
		logger.debug("任务个数:" + taskList.size());
		return taskList;
	}

	/**
	 * 执行任务
	 * 
	 * @author www.itxxz.com
	 * @return
	 */
	public static void takeCompleteTask(String taskid) {
		TaskService taskService = processEngine.getTaskService();
		taskService.complete(taskid);
	}

	/**
	 * 查询所有任务
	 */
	public static List<Task> findAllTask() {
		logger.debug("开始查询当前任务");
		TaskService taskService = processEngine.getTaskService();
		List<Task> taskList = taskService.createTaskQuery().list();
		logger.debug("任务个数:" + taskList.size());
		for (Task t : taskList) {
			logger.debug("task_id:" + t.getId());
			logger.debug("task_name:" + t.getName());
			logger.debug("task_owner:" + t.getOwner());
			logger.debug("task_ProcessInstanceId:" + t.getProcessInstanceId());
			logger.debug("task_Assignee:" + t.getAssignee());
		}
		return taskList;
	}

	/**
	 * 获取流程定义
	 * 
	 * @return
	 */
	public String findProcessEngine() {
		return "";
	}

	/**
	 * 启动流程实例
	 * 
	 * @param procInstanceId
	 * @return
	 */
	public String startByInstanceId(String procInstanceId) {

		return "";
	}
}
