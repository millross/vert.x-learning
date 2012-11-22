package com.millross.vertx.rest.test

import org.vertx.java.framework.TestBase
import org.vertx.groovy.core.Vertx

/**
 */
class GroovyTestBase extends TestBase {
    protected static vertx = new Vertx(super.vertx)
}
