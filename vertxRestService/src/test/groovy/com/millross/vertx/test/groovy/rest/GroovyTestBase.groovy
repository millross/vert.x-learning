package com.millross.vertx.test.groovy.rest

import org.vertx.java.framework.TestBase
import org.vertx.groovy.core.Vertx

/**
 */
abstract class GroovyTestBase extends TestBase {
    protected static vertx = new Vertx(super.vertx)
}
