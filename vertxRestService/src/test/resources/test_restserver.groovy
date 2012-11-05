import org.vertx.java.framework.TestUtils

def eb = vertx.eventBus
def tu = new TestUtils()

vertx.deployVerticle("restserver.groovy", null, 1, function() {tu.appReady()})

// Register a handler which will respond to customer requests with dummy data
eb.registerHandler()

def vertxStop = {
    tu.unregisterAll()
    tu.appStopped()
}