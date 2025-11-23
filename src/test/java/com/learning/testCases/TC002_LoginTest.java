package com.learning.testCases;

import com.learning.pageObjects.HomePage;
import com.learning.pageObjects.LoginPage;
import com.learning.pageObjects.MyAccountPage;
import com.learning.testBase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC002_LoginTest extends BaseClass {

    @Test(groups = {"sanity","master"})
    public void testLoginVerification(){
        logger.info("********************* Starting TC002_LoginTest **************************");

        try {
            HomePage page = new HomePage(driver);
            page.clickMyAccount();
            page.clickLogin();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmail(prop.getProperty("email"));
            loginPage.setPassword(prop.getProperty("password"));
            loginPage.clickLogin();

            logger.info("Clicked on login button after filling details");

            MyAccountPage accountPage = new MyAccountPage(driver);
            boolean targetPage = accountPage.isMyAccountExists();

            Assert.assertTrue(targetPage);

            logger.info("Verification completed");
        }
        catch (Exception e)
        {
            Assert.fail();
        }

        logger.info("***************** Finished TC002_LoginTest *********************************");
    }
}
