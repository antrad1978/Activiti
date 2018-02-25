package activiti2;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SampleJavaDelegate implements JavaDelegate {

	  public void execute(DelegateExecution execution) throws Exception {
	    String var = (String) execution.getVariable("input");
	    var = var.toUpperCase();
	    execution.setVariable("input", var);
	  }
	}
