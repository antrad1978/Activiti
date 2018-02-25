package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestMyProcessSum {

	private String filename = "/Users/macbook/Documents/workspace-sts-3.8.3.RELEASE/activiti2/src/main/resources/diagrams/JavaServiceSum.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("myProcessSum.bpmn20.xml",
				new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		//ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcessSum", variableMap);
		
		ProcessInstance processInstance;
		for(int i=0;i<10000;i++){
			processInstance = runtimeService.startProcessInstanceByKey("myProcessSum", variableMap);
			processInstance.getId();
			
			runtimeService.addParticipantUser(processInstance.getId(), "gonzo");
			
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			TaskService taskService = processEngine.getTaskService();
			FormService formService = processEngine.getFormService();
			
			Task task1 = taskService.createTaskQuery()
	              .processInstanceId(processInstance.getId()).taskDefinitionKey("usertask1").singleResult();
			
			
			
			Map<String, String> taskVariables = new HashMap<String, String>();
			taskVariables.put("input1", "5");
			TaskFormData dataSum =formService.getTaskFormData(task1.getId());
			formService.submitTaskFormData(task1.getId(), taskVariables);
			//taskService.complete(task1.getId(),taskVariables);
			
			Task task2 = taskService.createTaskQuery()
		              .processInstanceId(processInstance.getId()).taskDefinitionKey("usertask2").singleResult();
				
			Map<String, String> taskVariables2 = new HashMap<String, String>();
			taskVariables2.put("input2", "15");
			taskService.setAssignee(task2.getId(), "kermit");
			formService.submitTaskFormData(task2.getId(), taskVariables2);
			//taskService.complete(task2.getId(),taskVariables2);
		}
		Thread.sleep(5000);
		
		/*assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		
		runtimeService.addParticipantUser(processInstance.getId(), "gonzo");
		
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		FormService formService = processEngine.getFormService();
		
		Task task1 = taskService.createTaskQuery()
              .processInstanceId(processInstance.getId()).taskDefinitionKey("usertask1").singleResult();
		
		
		
		Map<String, String> taskVariables = new HashMap<String, String>();
		taskVariables.put("input1", "5");
		TaskFormData dataSum =formService.getTaskFormData(task1.getId());
		formService.submitTaskFormData(task1.getId(), taskVariables);
		//taskService.complete(task1.getId(),taskVariables);
		
		Task task2 = taskService.createTaskQuery()
	              .processInstanceId(processInstance.getId()).taskDefinitionKey("usertask2").singleResult();
			
		Map<String, String> taskVariables2 = new HashMap<String, String>();
		taskVariables2.put("input2", "15");
		taskService.setAssignee(task2.getId(), "kermit");
		formService.submitTaskFormData(task2.getId(), taskVariables2);
		//taskService.complete(task2.getId(),taskVariables2);
		
		Task result = taskService.createTaskQuery()
	              .processInstanceId(processInstance.getId()).taskDefinitionKey("usertask3").singleResult();
		
		
		
		TaskFormData form=formService.getTaskFormData(result.getId());
		String val = form.getFormProperties().get(0).getValue();
		
		assertEquals("20", val);
		
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());*/
		
	}
}