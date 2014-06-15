package integration_tests.groovy

import com.mycompany.myproject.TwitterStreamingVerticle
import org.vertx.groovy.testtools.VertxTests

import static org.vertx.testtools.VertxAssert.assertNotNull
import static org.vertx.testtools.VertxAssert.testComplete
/**
 * @author jez
 */
VertxTests.initialize(this)
VertxTests.startTests(this)

def testDeployTwitterVerticle() {
    container.deployVerticle("groovy:" + TwitterStreamingVerticle.class.getName())


    vertx.setTimer(10000, { timerID ->
        assertNotNull(timerID)

        // We're done after 10 seconds of tweet stream
        testComplete()
    })

}
