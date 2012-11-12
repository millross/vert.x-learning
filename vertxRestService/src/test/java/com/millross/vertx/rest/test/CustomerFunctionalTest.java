package com.millross.vertx.rest.test;

import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.framework.TestBase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CustomerFunctionalTest extends TestBase {
    // TODO: Logging

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test()
    public void test() throws Exception {
        startApp("test_restserver.js", 1);
        System.out.println("App started");
        final HttpClient client = vertx.createHttpClient().setHost("localhost").setPort(8080);
        final CountDownLatch done = new CountDownLatch(2);

        HttpClientRequest request = client.post("/customer/create", new Handler<HttpClientResponse>() {
            @Override
            public void handle(HttpClientResponse resp) {
                resp.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        String custId = buffer.toString();
                        System.out.println("Customer id = " + custId);
                        getCustomerWithId(client, done, custId);

                    }
                });
                System.out.println(resp.statusCode);
                done.countDown();
            }
        });

        request.setChunked(true);
        request.headers().put("Accept", "application/json");
        request.write(new JsonObject(createCustomerMap()).toString());
        request.end();

        done.await(1000, TimeUnit.MILLISECONDS);
        System.out.println("About to stop app");
    }

    private void getCustomerWithId(final HttpClient client, final CountDownLatch done, final String id) {
        System.out.println("Get: Customer id = " + id);
        HttpClientRequest request;
        request = client.get("/customer/" + id, new Handler<HttpClientResponse>() {
            public void handle(HttpClientResponse resp) {
                System.out.println(resp.statusCode);
                resp.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response body is " + buffer.toString());
                    }
                });
                done.countDown();
            }
        });

        request.headers().put("Accept", "application/json, text/html");
        request.end();
    }

    private Map createCustomerMap() {
        Map customerMap = new HashMap();
        customerMap.put("name", "Joe Dolce");
        customerMap.put("address", "6500 Warwick Avenue");
        customerMap.put("status", "elevated");
        return customerMap;
    }
}
