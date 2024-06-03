package api.utilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class TestRailManager {
	
public static String TEST_RUN_ID = "353";
	
    public static String TEST_RAIL_USERNAME = System.getenv("TEST_RAIL_USERNAME");  // Credentials are maintained in Jenkins
    public static String TEST_RAIL_PASSWORD = System.getenv("TEST_RAIL_PASSWORD");  // Credentials are maintained in Jenkins
	
	public static String TEST_RAIL_ENGINE_URL = "https://uncia.testrail.io/";
	
	public static int TEST_CASE_PASS_STATUS = 1;
	public static int TEST_CASE_FAIL_STATUS = 5;
	
	
	public static void addResultsForTestCase(int testCaseId, int status, String msg) {
		
		String testRunID = TEST_RUN_ID;
		APIClient client = new APIClient(TEST_RAIL_ENGINE_URL);
		client.setUser(TEST_RAIL_USERNAME);
		client.setPassword(TEST_RAIL_PASSWORD);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status_id", status);
		data.put("comment", "This script is executed through test automation." + " " + msg);
		
		try {
			client.sendPost("add_result_for_case/"+testRunID+"/"+testCaseId, data);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (APIException e) {
			e.printStackTrace();
		}

}
}