package activiti2;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SumJavaDelegate implements JavaDelegate {

	  public void execute(DelegateExecution execution) throws Exception {
	    long input1 = (Long) execution.getVariable("input1");
	    long input2 = (Long) execution.getVariable("input2");
	    long var = input1+input2;
	    execution.setVariable("output", var);
	  }
	}
