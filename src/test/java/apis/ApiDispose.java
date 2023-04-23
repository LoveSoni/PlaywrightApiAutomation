package apis;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

public class ApiDispose {

    @Test
    public void apiResponseDisposeTest() {
        //Request -1
        Playwright playwright = Playwright.create();
        APIRequest request = playwright.request();
        APIRequestContext apiRequestContext = request.newContext();
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        System.out.println("Status code is : " + statusCode);
        Assert.assertEquals(statusCode, HttpURLConnection.HTTP_OK);
        String statusResText = apiResponse.statusText();
        System.out.println(statusResText);
        String responseText = apiResponse.text();
        System.out.println(responseText);
        try {
            apiResponse.dispose();
            System.out.println(apiResponse.text());// // dispose method will only dispose respone body
        } catch (Exception e) {
        }


        // 2nd request
        apiRequestContext.dispose(); // request context will dispose complete request,
        // i.e. now will not be able to get response for first as well as second api

        APIResponse apiResponse1 = apiRequestContext.get("https://reqres.in/api/users/2");
        System.out.println("Getting response body " + apiResponse1.status());
        System.out.println("Getting response body " + apiResponse1.text());
        System.out.println("Getting response body " + apiResponse.text());

        // request context dispose


    }
}
