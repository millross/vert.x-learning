vertx.getEventBus().registerHandler("customer.get", { message ->
    message.reply([current_time: System.currentTimeMillis()].toString())
})

vertx.getEventBus().registerHandler("customer.create", { message ->
    message.reply("Customer created")
})
