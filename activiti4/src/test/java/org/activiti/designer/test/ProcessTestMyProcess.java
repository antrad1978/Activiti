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
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestMyProcess {

	private String filename = "/Users/macbook/Documents/workspace-sts-3.8.3.RELEASE/activiti4/src/main/resources/diagrams/SignalSample.bpmn";

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
		//variableMap.put("output", "0");
		ProcessInstance processInstance =runtimeService.startProcessInstanceByKey("myProcess", variableMap);
		runtimeService.signalEventReceived("signal");
		 
		/*assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		FormService formService = processEngine.getFormService();
		
		Task task1 = taskService.createTaskQuery()
              .processInstanceId(processInstance.getId()).taskDefinitionKey("usertask1").singleResult();

		Map<String, String> taskVariables = new HashMap<String, String>();
		taskVariables.put("value", "500");
		formService.submitTaskFormData(task1.getId(), taskVariables);
		
		List<HistoricProcessInstance> process = activitiRule.getHistoryService().createHistoricProcessInstanceQuery().finished().list();
		
		assertEquals(process.size(), 1);
		List<HistoricVariableInstance> variables = activitiRule.getHistoryService()
				  .createHistoricVariableInstanceQuery().variableName("output")
				  .list();
		
		double res = Double.parseDouble(variables.get(0).getValue().toString());
		
		assertEquals(110, res,0);*/
	}
}