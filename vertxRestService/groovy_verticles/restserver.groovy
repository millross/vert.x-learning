import org.vertx.groovy.core.http.RouteMatcher

def console = container.logger

def appConfig = [
        customerConfig: [test: "testVal"],
        routingConfig: [[path: "/details/:user/:id", type: "get", address: "test.address"],
                        [path:  "details/user", type: "post", address:  "test.post.address"]]
]

// Can we set something clever up so routing becomes part of config and we route based on the URL to an event
// bus address via a simple mapping, but always do the same thing?
// Need to add params from the url to some kind of nvp mapping, and also the request body, then generate a message
// and pump to the specified address. Can build internal message for this (map of maps) - consisting of params and body
def createRouteMatcher = {
    def rm = new RouteMatcher()

    // Extract the params from the uri, where appropriate, and submit to the relevant verticle
    rm.get('/customer/:id') { req ->
        vertx.getEventBus().send("customer.get", req.params["id"], { reply ->
            req.response.end reply.body
        })
    }

    rm.post("/customer/create") { req ->
        vertx.getEventBus.send("customer.create") {
            req.response.end reply.body
        }
    }


    return rm
}

vertx.createHttpServer().requestHandler (createRouteMatcher().asClosure()
).listen(8080, "localhost")

container.deployVerticle("customer.groovy", appConfig["customerConfig"])