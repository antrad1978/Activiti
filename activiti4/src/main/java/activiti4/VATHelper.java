package activiti4;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class VATHelper implements JavaDelegate {

	  public void execute(DelegateExecution execution) throws Exception {
	    long input1 = Long.parseLong(execution.getVariable("value").toString());
	    double var = (input1/100)*22;
	    execution.setVariable("output", var);
	  }
	}