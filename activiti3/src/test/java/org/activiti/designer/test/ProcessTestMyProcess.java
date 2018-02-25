package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestMyProcess {

	private String filename = "/Users/macbook/Documents/workspace-sts-3.8.3.RELEASE/activiti3/src/main/resources/diagrams/ReceiveTaskSample.bpmn";

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
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		
		Execution execution = runtimeService.createExecutionQuery()
				  .processInstanceId(processInstance.getId())
				  .activityId("waitState")
				  .singleResult();
		assertNotNull(execution);

		runtimeService.signal(execution.getId());
		Thread.sleep(2000);	
		
		execution = runtimeService.createExecutionQuery()
				  .processInstanceId(processInstance.getId())
				  .activityId("waitState")
				  .singleResult();
		assertNull(execution);
		
		List<HistoricTaskInstance> taskList = activitiRule.getHistoryService()
				  .createHistoricTaskInstanceQuery()
				  .processInstanceId(processInstance.getId())
				  .list();
		
		for(HistoricTaskInstance instance : taskList)
			System.out.println(instance.getId());
	}
}