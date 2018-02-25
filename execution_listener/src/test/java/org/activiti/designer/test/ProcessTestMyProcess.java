package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestMyProcess {

	private String filename = "/Users/macbook/Documents/workspace-sts-3.8.3.RELEASE/execution listener/src/main/resources/diagrams/ExecutionListener.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("myProcess.bpmn20.xml",
				new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		runtimeService.addEventListener(new MyEventListener());
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		try {
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variableMap);
			assertNotNull(processInstance.getId());
			System.out.println("id " + processInstance.getId() + " "
					+ processInstance.getProcessDefinitionId());
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		
	}
}