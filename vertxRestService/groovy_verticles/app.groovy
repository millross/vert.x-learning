/**
 */
def appConfig = [
        customerConfig: [test: "testVal"],
        routingConfig: [routes: [[path: "/details/:user/:id", type: "get", address: "test.address"],
                [path:  "details/user", type: "post", address:  "test.post.address"]]]
]

container.deployVerticle("restserver.groovy", appConfig["routingConfig"])
container.deployVerticle("customer.groovy", appConfig["customerConfig"])