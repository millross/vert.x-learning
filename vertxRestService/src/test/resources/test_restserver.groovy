import org.vertx.groovy.deploy.Container
import org.vertx.groovy.testframework.TestUtils
import org.vertx.java.deploy.impl.VertxLocator

tu = new TestUtils(vertx) // Need this effectively globally scoped so we can call it from vertxStop()

// Need reference to container for deployment of verticles
def container = new Container(VertxLocator.container)

container.deployVerticle("restserver.groovy", null, 1)
container.deployVerticle("test_customer_handler.groovy", null, 1) {tu.appReady()}

def vertxStop() {
    tu.unregisterAll()
    tu.appStopped()
}
