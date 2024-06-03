package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import api.utilities.JiraIssueCreator;

import api.utilities.TestRailManager;

import api.endpoints.Routes;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener extends Routes implements ITestListener {

	
	private static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    //private WebDriver driver; // Assuming you have a WebDriver instance

    
    @Override
    public void onStart(ITestContext context) {
        extentReports = ExtentManager.createInstance();
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        extentTest.get().assignAuthor("maxxmuthu");
        extentTest.get().assignCategory("Smoke Tests");
        extentTest.get().getModel().setDescription("This test verifies the API functionality.");
    }
   
    public void onTestSuccess(ITestResult result) {
    	extentTest.get().log(Status.PASS, MarkupHelper.createLabel("Test passed", ExtentColor.GREEN));
    	
    	// Add results to TestRail
        Object testClass = result.getInstance();
        try {
            Field field = testClass.getClass().getField("testCaseId");
            int testCaseId = field.getInt(testClass);
            TestRailManager.addResultsForTestCase(testCaseId, TestRailManager.TEST_CASE_PASS_STATUS, "Test Case is passed successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void onTestFailure(ITestResult result) {
    	extentTest.get().log(Status.FAIL, MarkupHelper.createLabel("Test failed", ExtentColor.RED))
        .log(Status.FAIL, result.getThrowable());
    	
    	// Add results to TestRail with custom log message
        Object testClass = result.getInstance();
        try {
            Field field = testClass.getClass().getField("testCaseId");
            int testCaseId = field.getInt(testClass);
            String logMessage = "Test Case got Failed." + " Method: " + result.getMethod().getMethodName() + " : FAILED. " + result.getThrowable().getMessage();
            TestRailManager.addResultsForTestCase(testCaseId, TestRailManager.TEST_CASE_FAIL_STATUS, logMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // JIRA Issue Creation
        String summary = "Test failed due to: " +  result.getMethod().getMethodName() + " " + result.getThrowable().getMessage();
        JiraIssueCreator.createJiraIssue(summary);
    }

    
    public void onTestSkipped(ITestResult result) {
    	 extentTest.get().log(Status.SKIP, "Test skipped");
        
    }

   
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Do nothing
    }

    public static void logResponseDetails(Response response) {
    	extentTest.get().info("Response Status Code: " + response.getStatusCode());
    	extentTest.get().info("Response Body: " + response.getBody().asString());
    }

    public static void logRequestDetails(String httpMethod, String endpoint) {
    	extentTest.get().info("Request Method: " + httpMethod);
    	extentTest.get().info("Endpoint URL: " + endpoint);
    }
    

}