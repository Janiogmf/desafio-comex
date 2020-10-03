package config.extent_reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import cucumber.api.Scenario;

public class ExecutionReportHtml {
	private static ExtentHtmlReporter htmReporter;
	private static ExtentReports extentReport;
	private static ExtentTest extentTest;

	public static void extentReportHtml(Scenario cenario) {
		if (extentReport == null) {
			extentReport = new ExtentReports();
			htmReporter = new ExtentHtmlReporter(
					"src/test/resources/reports_executions/report.html");
			extentReport.attachReporter(htmReporter);
		}
		extentTest = extentReport.createTest(cenario.getName());
	}

	public static ExtentReports getExtentReport() {
		return extentReport;
	}
	
	public static ExtentTest getExtentTest() {
		return extentTest;
	}
}
