package task_error;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ErrorDelegate implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		try {
			int res = 12/0;
		} catch (Exception e) {
			// TODO: handle exception
			throw new BpmnError("error_custom");
		}
	}

}
