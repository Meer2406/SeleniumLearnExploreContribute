//Aim: Reset password after logged-in

package webMainScripts.loginLogout;

import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.gjt.mm.mysql.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import utils.SelectBrowser;
import utils.WebCommonMethods;
import webMethods.LoginLogoutMethods;


public class ResetPasswordAndLoginWithOldPassword extends SelectBrowser
{
	WebCommonMethods general;
	LoginLogoutMethods loginLogout;
		
	@BeforeMethod
    public void openTheBrowser() throws IOException 
    {
    	WebDriver d = getBrowser();
	    loginLogout = PageFactory.initElements(d, LoginLogoutMethods.class);
	    general = PageFactory.initElements(d, WebCommonMethods.class);// initiating the driver and the .class file (the pageObject script)	    
	    
	  	WebCommonMethods.openURL();
    } 

	//validLogin test 
	@Test(priority=1, groups={"loginlogout"})
	public void resetPasswordCheck() throws IOException, BiffException, InterruptedException
	{	String[] users={"admin"};
		System.out.println("Checking for Reset password test...");
		for(int i=0; i<users.length; i++)
		{
			//reset password to new value
			LoginLogoutMethods.login(users[i]);
			LoginLogoutMethods.resetPasswordAndLoginWithOldPassword(users[i]);
			
			Cell[] record = WebCommonMethods.webReadExcel("validLogin",users[i]);  //sending userName, password
	  	  	//System.out.println("record: "+ record.length);
	        String username = record[0].getContents();
	        String password = record[2].getContents();
	        String profileName = record[3].getContents();
			LoginLogoutMethods.validLogin(username,password,profileName);
			LoginLogoutMethods.resetPasswordToOldValueAndLogin(users[i]);
			
//			Cell[] userNameRecord = WebCommonMethods.webReadExcel("validLogin", users[i]);
//			LoginLogoutMethods.validLogin(userNameRecord[0].getContents(), userNameRecord[2].getContents(), userNameRecord[3].getContents());
//			Thread.sleep(10000);
//			WebCommonMethods.callingImplicitSleep();
//
//			LoginLogoutMethods.resetPasswordToOldValueAndLogin(users[i]);
			//Thread.sleep(7000);
			//LoginLogoutMethods.logout();
		}
	}

	
	@AfterMethod(alwaysRun=true)
    public void catchExceptions(ITestResult result) throws IOException 
    {    
    	String methodname = result.getName();
        if(!result.isSuccess()){  
        	WebCommonMethods.screenshot(methodname);
        	
        }
        WebCommonMethods.quit(); // Calling function close to quit browser instance
    }

}
