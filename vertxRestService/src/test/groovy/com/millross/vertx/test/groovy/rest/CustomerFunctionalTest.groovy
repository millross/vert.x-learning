package com.millross.vertx.test.groovy.rest

import org.vertx.java.core.json.JsonObject

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 */
class CustomerFunctionalTest extends GroovyTestBase {

    void test() {
        startApp("test_restserver.groovy", 1);
        println 'App started'

        def client = vertx.createHttpClient().setHost("localhost").setPort(8080);
        def done = new CountDownLatch(2);

        def request = client.post("/customer/create") {resp ->
            println(resp.statusCode)
            resp.bodyHandler {buffer ->
                String custId = buffer.toString()
                println ("Customer id = " + custId)
                getCustomerWithId(client, done, custId);
            }
            done.countDown();
        }

        request.setChunked(true);
        request.headers["Accept"] = "application/json"
        request.write(new JsonObject(createCustomerMap()).toString());
        request.end();

        done.await(1000, TimeUnit.MILLISECONDS);
        System.out.println("About to stop app");

    }

    void getCustomerWithId(client, done, id) {
        println("Get: Customer id = " + id);
        def request = client.get("/customer/" + id) { resp ->
            println(resp.statusCode);
            resp.bodyHandler { buffer ->
                println("Response body is " + buffer.toString());
            }
            done.countDown();
        }

        request.headers["Accept"] =  "application/json, text/html"
        request.end();
    }

    void testSomething() {
        assert 1 == 1
        assert 2 + 2 == 4 : "We're in trouble, arithmetic is broken"
    }

    private Map createCustomerMap() {
        Map customerMap = new HashMap();
        customerMap.put("name", "Joe Dolce");
        customerMap.put("address", "6500 Warwick Avenue");
        customerMap.put("status", "elevated");
        return customerMap;
    }

}
