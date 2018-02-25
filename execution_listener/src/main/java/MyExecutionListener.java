import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class MyExecutionListener implements ExecutionListener {
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String id=execution.getCurrentActivityId();
		execution.setVariable("variableSetInExecutionListener", "firstValue");
	    execution.setVariable("eventReceived", execution.getEventName());
	}
}
