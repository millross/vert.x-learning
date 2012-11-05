/**
 * Dummy handler for customer routing (assumption is that server requests write to the event bus). We will write back
 * standard data. We'll just use a local map to spoof a database (can I dummy a persistor?)
 */
import org.vertx.groovy.core.buffer.Buffer

def localCache = [:]

vertx.getEventBus().registerHandler("customer.get", { message ->
    x = localCache.get("Hello")
    message.reply([response: x].toString())
})

vertx.getEventBus().registerHandler("customer.create", { message ->
    println(message.getBody().toString());
    localCache.put("Hello", "Test1")
    message.reply(new Buffer("Customer created"))
})
