package com.millross.vertx.test.groovy.rest

import org.vertx.groovy.core.Vertx
import org.vertx.java.testframework.TestBase

/**
 */
abstract class GroovyTestBase extends TestBase {
    protected static vertx = new Vertx(super.vertx)
}
