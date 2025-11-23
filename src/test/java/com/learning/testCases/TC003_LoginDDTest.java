package com.learning.testCases;

import com.learning.pageObjects.HomePage;
import com.learning.pageObjects.LoginPage;
import com.learning.pageObjects.MyAccountPage;
import com.learning.testBase.BaseClass;
import com.learning.utilities.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC003_LoginDDTest extends BaseClass
{
    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "datadriven")
    public void testLoginDataDrivenVerification(String email, String pass, String exp)
    {
        logger.info("******************* Starting TC003_LoginDDTest ****************************");

        try
        {
            HomePage page = new HomePage(driver);
            page.clickMyAccount();
            page.clickLogin();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmail(email);
            loginPage.setPassword(pass);
            loginPage.clickLogin();

            logger.info("Clicked on login button after filling details");

            MyAccountPage accountPage = new MyAccountPage(driver);
            boolean targetPage = accountPage.isMyAccountExists();

            if (exp.equalsIgnoreCase("Valid")) {
                if (targetPage == true) {
                    accountPage.clickLogout();
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue(false);
                }
            }

            if (exp.equalsIgnoreCase("Invalid")) {
                if (targetPage == true) {
                    accountPage.clickLogout();
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);
                }
            }
        }
        catch (Exception e)
        {
            Assert.fail();
        }
        logger.info("********************* Finished TC003_LoginDDTest ************************");
    }
}
