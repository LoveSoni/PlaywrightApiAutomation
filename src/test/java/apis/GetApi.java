package apis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

public class GetApi {

    @Test
    public void getUsersApiTest() throws IOException {
        Playwright playwright = Playwright.create();
        APIRequest request = playwright.request();
        APIRequestContext apiRequestContext = request.newContext();
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        System.out.println("Status code is : " + statusCode);
        Assert.assertEquals(statusCode, HttpURLConnection.HTTP_OK);
        String statusResText = apiResponse.statusText();
        System.out.println(statusResText);

        byte[] bodyArray = apiResponse.body();
        for (byte b : bodyArray) {
            System.out.print((char) b);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(bodyArray);
        System.out.println(jsonNode.toPrettyString());

        String url = apiResponse.url();
        System.out.println("Api url is : " + url);

        Map<String, String> responseHeaders = apiResponse.headers();
        System.out.println("Headers are : " + responseHeaders);
    }

    @Test
    public void getCallWithQueryParams() {
        Playwright playwright = Playwright.create();
        APIRequest request = playwright.request();
        APIRequestContext apiRequestContext = request.newContext();
        RequestOptions requestOptions = RequestOptions.create();
        requestOptions.setQueryParam("gender","malet stat");
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users", requestOptions);
        int statusCode = apiResponse.status();
        System.out.println(apiResponse.text());
    }
}
