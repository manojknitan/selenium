package com.learning.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.learning.testBase.BaseClass;
import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports reports;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("Application", "E-commerce");
        reports.setSystemInfo("Module", "Admin");
        reports.setSystemInfo("SubModules", "Customers");
        reports.setSystemInfo("User Name", System.getProperty("user.name"));
        reports.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        reports.setSystemInfo("Operating system", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        reports.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            reports.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult iTestResult)
    {
        test = reports.createTest(iTestResult.getTestClass().getName());
        test.assignCategory(iTestResult.getMethod().getGroups());

        test.log(Status.PASS, iTestResult.getName()+"got successfully executed");
    }

    public void onTestFailure(ITestResult result){
        test = reports.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.FAIL, result.getName()+"got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());

        try{
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result){
        test = reports.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName()+"got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext context){
        reports.flush();

        String pathOfReport = System.getProperty("user.dir")+"\\reports\\"+repName;
        File extentReport = new File(pathOfReport);

        try{
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //send report as email
        /*try{
            URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);

            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("manojknitan@gmail.com","password"));
            email.setSSLOnConnect(true);
            email.setFrom("manojknitan@gmail.com");
            email.setSubject("Test Result");
            email.setMsg("Pls find attached report...");
            email.addTo("manojknitan@gmail.com");
            email.attach(url, "extent-report", "please check report...");
            email.send();
        }
        catch (MalformedURLException | EmailException e) {
            e.printStackTrace();
        }*/

    }


}
