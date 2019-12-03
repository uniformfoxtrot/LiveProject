package apiTests;


import apiObjects.SimpleJavaObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.io.FileReader;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class APITest
{
    String systemPath = System.getProperty("user.dir");
    String JSON_FILE=systemPath+"/src/test/java/resources/post-body.json";
    String EXCEL_FILE=systemPath+"/src/main/resources/Untitled 1.xlsx";
    @Test
    public void basicPostUsingJsonFile() throws Exception
    {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(JSON_FILE));
        JSONObject jsonObject  = (JSONObject) obj;

        given().
                header("contentType","applcation/json").and().
                body(jsonObject.toJSONString()).
                when().
                post("http://dummy.restapiexample.com/api/v1/create").
                then().
                statusCode(200).
                log().all();
    }

    // Test using fetching data from excel
    @Test
    public void basicPostUsingExcelFile() throws Exception
    {
        ArrayList<String> as = ExcelUtils.getData(EXCEL_FILE,"TestCaseName",
                "Sheet1","basicPostUsingExcelFile");
        String body = as.get(2);

        given().
                header("contentType","applcation/json").and().
                body(body).
                when().
                post("http://dummy.restapiexample.com/api/v1/create").
                then().
                statusCode(200).
                log().all();
    }

    // Test using Jackson API,POJO implementation
    @Test
    public void basicPostUsingJacksonPOJO() throws Exception
    {
        String requestBody = null;
        SimpleJavaObject obj = new SimpleJavaObject();
        obj.setAge(23);
        obj.setId(1023);
        obj.setName("Kili kili");
        obj.setSalary(20000);

        ObjectMapper objectMapper = new ObjectMapper();
        requestBody = objectMapper.writeValueAsString(obj);
        given().
                header("contentType","applcation/json").and().
                body(requestBody).
                when().
                post("http://dummy.restapiexample.com/api/v1/create").
                then().
                statusCode(200).
                log().all();
    }

}
