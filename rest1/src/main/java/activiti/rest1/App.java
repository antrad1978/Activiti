package activiti.rest1;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
			String res=ActivitiRestClient.get("http://kermit:kermit@127.0.0.1:8080/activiti-rest/service/repository/deployments");
			String res2=ActivitiRestClient.get("http://kermit:kermit@127.0.0.1:8080/activiti-rest/service/runtime/process-instances/");
			
			//String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
			String input="{\"processDefinitionKey\":\"oneTaskProcess\",\"variables\": []}";
			String start=ActivitiRestClient.post("http://127.0.0.1:8080/activiti-rest/service/runtime/process-instances/", input,false);
			
			System.out.println(start);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
}
