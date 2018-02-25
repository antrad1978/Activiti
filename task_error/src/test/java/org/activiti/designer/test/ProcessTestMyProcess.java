package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.Assert;

public class ProcessTestMyProcess {

	private String filename = "/Users/macbook/Documents/workspace-sts-3.8.3.RELEASE/task_error/src/main/resources/diagrams/task_error.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("myProcess.bpmn20.xml",
				new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variableMap);
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		
		Execution execution = runtimeService.createExecutionQuery()
				  .processInstanceId(processInstance.getId())
				  .activityId("firstTask")
				  .singleResult();
		
		assertNotNull(execution);
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId()).taskDefinitionKey("firstTask").singleResult();
		List<Task> res=taskService.createTaskQuery()
                .processInstanceId(processInstance.getId()).list();
		String id = res.get(0).getId();
		Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("url", "www.libero.it");
        taskVariables.put("data", "data");
        taskService.setAssignee(task.getId(), "kermit");
		taskService.complete(task.getId(),taskVariables);
		
		Map<String,Object> vars =runtimeService.getVariables(task.getProcessInstanceId());
		assertEquals(3, vars.size());
		
		for(String s : vars.keySet())
			System.out.println(s);
		
		Task feedback = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId()).taskDefinitionKey("feedbackTask").singleResult();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("ok", true);
		taskService.complete(feedback.getId(),variables);
		
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());

	}
}