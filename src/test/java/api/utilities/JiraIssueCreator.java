package api.utilities;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JiraIssueCreator {
	
	@SuppressWarnings("deprecation")
	public static void createJiraIssue(String summary) {
        String jiraUrl = "https://uncia.atlassian.net/rest/api/3/issue";
        String username = System.getenv("JIRA_USERNAME");   // Credentials are maintained in Jenkins
        String apiToken = System.getenv("JIRA_API_TOKEN");   // Credentials are maintained in Jenkins

        Map<String, Object> fields = new HashMap<>();
        fields.put("summary", summary);
        
        Map<String, String> project = new HashMap<>();
        project.put("key", "QA");
        fields.put("project", project);
        
        Map<String, String> issueType = new HashMap<>();
        issueType.put("name", "Bug");
        fields.put("issuetype", issueType);
        
        Map<String, String> reporter = new HashMap<>();
        reporter.put("id", "712020:a4d2eb06-b133-4581-be88-f9ff5f9eba22");
        fields.put("reporter", reporter);
        
        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("fields", fields);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(jiraUrl);
            String auth = username + ":" + apiToken;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeader = "Basic " + new String(encodedAuth);

            httpPost.setHeader("Authorization", authHeader);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(bodyData);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);

			String response = EntityUtils.toString(httpClient.execute(httpPost).getEntity());
            System.out.println("Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
