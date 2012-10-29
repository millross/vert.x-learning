import org.vertx.java.framework.TestUtils

def eb = vertx.eventBus
def tu = new TestUtils()

vertx.deployVerticle("restserver.groovy", null, 1, function() {tu.appReady()})

def vertxStop = {
    tu.unregisterAll()
    tu.appStopped()
}