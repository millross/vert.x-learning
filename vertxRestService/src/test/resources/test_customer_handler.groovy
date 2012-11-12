/**
 * Dummy handler for customer routing (assumption is that server requests write to the event bus). We will write back
 * standard data. We'll just use a local map to spoof a database (can I dummy a persistor?)
 */

import static java.util.UUID.randomUUID
import static java.util.UUID.fromString

def localCache = [:]

vertx.getEventBus().registerHandler("customer.get", { message ->
    x = localCache.get(fromString(message.body))
    message.reply(x.toString())
})

vertx.getEventBus().registerHandler("customer.create", { message ->
    def custObject = message.getBody()
    def custId = randomUUID()
    custObject.id = custId
    // convert message body into a customer object, or at least a json object
    localCache.put(custId, custObject)
    message.reply([id: custId])
})
