import org.vertx.groovy.core.buffer.Buffer

vertx.getEventBus().registerHandler("customer.get", { message ->
    message.reply([current_time: System.currentTimeMillis()].toString())
})

vertx.getEventBus().registerHandler("customer.create", { message ->
    println(message.getBody().toString());
    message.reply(new Buffer("Customer created"))
})
