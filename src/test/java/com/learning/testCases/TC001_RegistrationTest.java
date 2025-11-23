package com.learning.testCases;

import com.learning.pageObjects.HomePage;
import com.learning.pageObjects.RegistrationPage;
import com.learning.testBase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TC001_RegistrationTest extends BaseClass {

    @Test(groups = {"regression","master"})
    public void testAccountRegistrationVerification() {

        logger.info("******************** Starting TC001_RegistrationTest***************************");

        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked on MyAccount link");

            homePage.clickRegister();
            logger.info("Clicked on Register link");

            RegistrationPage reg = new RegistrationPage(driver);

            logger.info("Providing consumer details...");
            reg.setFirstName(randomString().toUpperCase());
            reg.setLastName(randomString().toUpperCase());
            reg.setEmail(randomString() + "@gmail.com");
            reg.setTelephone(randomNumber());

            String pass = randomAlphaNumeric();
            reg.setPassword(pass);
            reg.setConfirmPassword(pass);

            reg.setPrivacyPolicy();
            reg.clickContinue();

            logger.info("Validating expected message...");
            String cnfMsg = reg.getConfirmationMessage();

            if(cnfMsg.equals("Your Account Has Been Created!")) {
                Assert.assertTrue(true);
            }
            else{
                logger.error("Test failed..");
                logger.debug("Need to lookup for more details.");
                Assert.assertTrue(false);
            }

            //Assert.assertEquals(cnfMsg, "Your Account Has Been Created!");
        }
        catch (Exception e)
        {
            Assert.fail();
        }

        logger.info("******************** Finished TC001_RegistrationTest ***************************");
    }

}
