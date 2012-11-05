// Can I use groovy for this?

load('test_utils.js')
load('vertx.js')

var tu = new TestUtils();
var eb = vertx.eventBus;

vertx.deployVerticle("restserver.groovy", null, 1, function() {})
vertx.deployVerticle("test_customer_handler.groovy", null, 1, function() {tu.appReady()})

function vertxStop() {
    tu.unregisterAll();
    tu.appStopped();
}