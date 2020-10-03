package config.hooks;


import com.aventstack.extentreports.Status;

import config.extent_reports.ExecutionReportHtml;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class ReportFactoryTest {
	
	@Before
	public void beforeMethod(Scenario cenario) {
		ExecutionReportHtml.extentReportHtml(cenario);
	}
	
	@After
	public void afterCenario(Scenario cenario) {
		if (cenario.isFailed()) {
			ExecutionReportHtml.getExtentTest().log(Status.FAIL, "Cenario " + cenario.getName() + " executado com falhas!");
			ExecutionReportHtml.getExtentReport().flush();
		} else {
			ExecutionReportHtml.getExtentTest().log(Status.PASS, "Cenario " + cenario.getName() + " executado com sucesso!");
			ExecutionReportHtml.getExtentReport().flush();
		}
	}
}
