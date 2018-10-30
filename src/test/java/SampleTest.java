import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * Created by shridhk on 12/28/17.
 */
public class SampleTest {
    public AppiumDriver driver;
    DesiredCapabilities caps;

    /*@BeforeClass
    public void setUpiOS() throws MalformedURLException {
        caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone X");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.3");
        caps.setCapability(MobileCapabilityType.APP,
                System.getProperty("user.dir") + "/VodQA.zip");
        driver = new IOSDriver(new URL("http://127.0.0.1:7764/wd/hub"), caps);

    }*/

    @BeforeClass
    public void setUpAndroid() throws MalformedURLException {
        caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,AutomationName.ANDROID_UIAUTOMATOR2);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.1");
       // caps.setCapability(MobileCapabilityType.APP,
        //        System.getProperty("user.dir") + "/AndroidCalculator.apk");
       // caps.setCapability(MobileCapabilityType.APP,
       //         System.getProperty("user.dir") + "/VodQA.apk");
        //caps.setCapability(MobileCapabilityType.APP,
        //        System.getProperty("user.dir") + "/MoPubSampleApp_v5.4.0.apk");

        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.mopub.simpleadsdemo");
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                "com.mopub.simpleadsdemo.MoPubSampleActivity");


        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        System.out.println("App Launched");
    }


    @Test
    public void verifyTextForMoPubApp() throws InterruptedException {
        Thread.sleep(3000);

        try {
            WebDriverWait wait = new WebDriverWait(driver,10000);

            wait.until(ExpectedConditions.alertIsPresent());
            Alert allowLocationAlert = driver.switchTo().alert();
            allowLocationAlert.accept();

            Thread.sleep(800);
            Alert allowStorageAlert = driver.switchTo().alert();
            allowStorageAlert.accept();

            // Allow Location Sharing

            // Allow Storage Access
            Thread.sleep(2000);
            List<WebElement> bannerListElements = driver.findElementsById("com.mopub.simpleadsdemo:id/banner_description");

            wait.until(ExpectedConditions.elementToBeClickable(bannerListElements.get(0)));

            // Click first Banner
            bannerListElements.get(0).click();

            WebElement addBannerElement = driver.findElementById("com.mopub.simpleadsdemo:id/banner_mopubview");

            wait.until(ExpectedConditions.visibilityOf(addBannerElement));

            Thread.sleep(3000);
            // Click om Banner

            addBannerElement.click();

            Thread.sleep(10000);

            Set<String> contextNames = driver.getContextHandles();

            for(int i=0; i<contextNames.toArray().length; i++)
            {
                System.out.println("Context Name At " + i +" Is : " + contextNames.toArray()[i]);
            }

            driver.context(String.valueOf(contextNames.toArray()[1]));

            WebElement textToVerifyElement = driver.findElementByXPath("//div[contains(@class,'cta-banner__content')]");

            wait.until(ExpectedConditions.textToBePresentInElement(textToVerifyElement,"Looking for MoPub resources?"));

            if(textToVerifyElement.getText().contains("Looking for MoPub resources?"))
            {
                Assert.assertTrue(true,"Text Verified");
                System.out.println("Text <Looking for MoPub resources?> has been verified on ad screen.");
            }
            else

            {
                Assert.assertTrue(false,"Text Verification Failed");
                System.out.println("Text <Looking for MoPub resources?> verification failed on ad screen.");
            }

            //Assert.assertEquals(textToVerifyElement.getText(),"Looking for MoPub resources? Visit Education Center");
        }
        catch (Exception ex)
        {
            System.out.println("Exception : " + ex);
        }


    }

    // Horizontal Scroll Test
    /*@Test
    public void HorizontalSwipe() throws InterruptedException {

        Thread.sleep(2000);
        driver.findElementByAccessibilityId("login").click();

        Thread.sleep(3000);
        driver.findElementByAccessibilityId("slider1").click();

        Thread.sleep(3000);

        WebElement sliderEle = driver.findElementByAccessibilityId("slider");

        Dimension sliderDim = sliderEle.getSize();
        int sliderHeight = sliderDim.getHeight();
        int sliderWidth = sliderDim.getWidth();

        new io.appium.java_client.TouchAction<>(driver).press(ElementOption.element(sliderEle))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(ElementOption.element(sliderEle,sliderWidth/2, sliderHeight))
                .release()
                .perform();



    }
*/
   /* @Test
    public void VerticalSwipe() throws InterruptedException {

        Thread.sleep(2000);
        driver.findElementByAccessibilityId("login").click();

        Thread.sleep(3000);
        driver.findElementByAccessibilityId("verticalSwipe").click();

        Thread.sleep(3000);
        WebElement sliderEle = driver.findElementByAccessibilityId("listview");

        Dimension sliderDim = sliderEle.getSize();
        //int sliderHeight = sliderDim.getHeight();
        int sliderWidth = sliderDim.getWidth();

        new io.appium.java_client.TouchAction<>(driver).press(ElementOption.element(sliderEle))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(ElementOption.element(sliderEle,sliderWidth/2, 0))
                .release()
                .perform();

        Thread.sleep(3000);

    }*/

    /*@Test
    public void MultiTouchHorizontalScroll() throws InterruptedException {

            Thread.sleep(2000);
            driver.findElementByAccessibilityId("login").click();

            Thread.sleep(3000);
            driver.findElementByAccessibilityId("slider1").click();

            Thread.sleep(3000);

            WebElement firstSliderEle = driver.findElementByAccessibilityId("slider");

            Dimension sliderDim = firstSliderEle.getSize();
            int sliderHeight = sliderDim.getHeight();
            int sliderWidth = sliderDim.getWidth();

            io.appium.java_client.TouchAction  firstTouchAction = new io.appium.java_client.TouchAction<>(driver).press(ElementOption.element(firstSliderEle))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                    .moveTo(ElementOption.element(firstSliderEle, sliderWidth / 2, sliderHeight/2))
                    .release();


            WebElement secondSliderEle = driver.findElementByAccessibilityId("slider1");

            Dimension secondSliderDim = secondSliderEle.getSize();
            int secondSliderHeight = secondSliderDim.getHeight();
            int secondSliderWidth = secondSliderDim.getWidth();

            io.appium.java_client.TouchAction  secondTouchAction = new io.appium.java_client.TouchAction<>(driver).press(ElementOption.element(secondSliderEle))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                    .moveTo(ElementOption.element(secondSliderEle, secondSliderWidth / 2, secondSliderHeight/2))
                    .release();

            new MultiTouchAction(driver).add(firstTouchAction).add(secondTouchAction).perform();

            Thread.sleep(3000);


    }*/


    @Test
    public void DragAndDropTest() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElementByAccessibilityId("login").click();

        Thread.sleep(3000);
        driver.findElementByAccessibilityId("dragAndDrop").click();

        Thread.sleep(3000);
        WebElement elementToBeDragged = driver.findElementByAccessibilityId("dragMe");

        Thread.sleep(2000);
        WebElement draggedArea = driver.findElementByAccessibilityId("dropzone");

        new TouchAction<>(driver).press(ElementOption.element(elementToBeDragged))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(ElementOption.element(draggedArea))
                .release()
                .perform();

        Thread.sleep(3000);
    }

/*
    @Test
    public void doubleTapTest() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElementByAccessibilityId("login").click();

        Thread.sleep(3000);
        driver.findElementByAccessibilityId("doubleTap").click();

        Thread.sleep(3000);
        WebElement doubleTapBtnElement = driver.findElementByAccessibilityId("doubleTapMe");

        new TouchAction<>(driver)
                .tap(ElementOption.element(doubleTapBtnElement))
                .tap(ElementOption.element(doubleTapBtnElement))
                .perform();

        Thread.sleep(3000);
        Alert alert = driver.switchTo().alert();
        alert.accept();

        Thread.sleep(2000);

    }*/

    @Test
    public void longPressTest() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElementByAccessibilityId("login").click();

        Thread.sleep(3000);
        driver.findElementByAccessibilityId("longPress").click();

        Thread.sleep(3000);
        WebElement longPressBtnElement = driver.findElementByAccessibilityId("longpress");

        new TouchAction<>(driver)
                .longPress(ElementOption.element(longPressBtnElement))
                .release()
                .perform();

        Thread.sleep(3000);
        Alert alert = driver.switchTo().alert();
        alert.accept();

        Thread.sleep(2000);
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }




}
