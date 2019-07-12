import jdk.nashorn.internal.parser.JSONParser;
import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.client.HttpRequest;
import org.json.JSONObject;
import spark.Spark;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static spark.Spark.*;

public class Hello {
    public static void main(String[] args) {
        BasicConfigurator.configure();

        staticFiles.location("/assets");
        staticFiles.header("Access-Control-Allow-Origin", "*");

        options("/*", (req, res) -> {
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "*");
            res.type("application/json");
        });
        get("/hello", (req, res) -> "Hello World");

        get("/hello/:name", (request, response) -> "Hello, "  + request.params(":name"));

        post("/validate", (req,res) -> {
            res.type("application/json");
            res.status(200);
            //System.out.println(req.body().textArea());
            JSONObject jo = new JSONObject();
            JSONObject reqbody = new JSONObject(req.body());

            String regexPattern=reqbody.getString("regexPattern");
            String textArea=reqbody.getString("textArea");
            String fileInput=reqbody.getString("fileInput");

            System.out.println(regexPattern);
            System.out.println(textArea);
            System.out.println(fileInput);


            Pattern p = Pattern.compile(regexPattern);
            Matcher m = p.matcher(textArea);
            if(m.find())
                System.out.println("Found a Pattern in the Text");
            else
                System.out.println("Pattern not found in Text");

            Pattern p1=Pattern.compile(regexPattern);
            Matcher m1=p1.matcher(fileInput);
            if(m1.find())
                System.out.println("Found a pattern in File");
            else
                System.out.println("Pattern not found in File");

            jo.put("data", "data received");
            return jo;
        });



        }


    }
