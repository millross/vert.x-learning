package com.mycompany.myproject
import org.vertx.groovy.core.http.HttpClient
import org.vertx.groovy.platform.Verticle
/**
 * @author jez
 */
class TwitterStreamingVerticle extends Verticle {

    def oAuthNonce = {
        def alphabet = (('A'..'Z')+('0'..'9')).join()
        def nonceLength = 32
        new Random().with {
            (1..nonceLength).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
        }
    }

    def oAuthTimestamp = {
        return ((System.currentTimeMillis() / 1000) as Long).toString()
    }

    def buildOAuthHeader(String httpMethod, String url) {
        def oAuthHeader="OAuth";
        def oAuthSignatureMethod = "HMAC-SHA1"
        def oAuthVersion = "1.0"

        def params = [
            oauth_signature_method: oAuthSignatureMethod,
            oauth_version: oAuthVersion,
            oauth_nonce: oAuthNonce(),
            oauth_timestamp: oAuthTimestamp(),
            oauth_consumer_key: "BLAHBLAHBLAH",
            oauth_token: "BLAHBLAHBLAH"
        ]


        oAuthHeader=oAuthHeader+" oauth_signature_method=" + oAuthSignatureMethod
        oAuthHeader=oAuthHeader+",oauth_version=" + oAuthVersion
        oAuthHeader=oAuthHeader+",oauth_nonce=" + params.oAuthNonce
        oAuthHeader=oAuthHeader+",oauth_timestamp=" + params.oAuthTimeStamp
        oAuthHeader=oAuthHeader+",oauth_consumer_key=" + params.oauth_consumer_key
        oAuthHeader=oAuthHeader+",oauth_token=" + params.oauth_token
        oAuthHeader=oAuthHeader+",oauth_signature=" + buildOAuthSignature(httpMethod, url, params)

        // Need to map above list of params for header next

        println(buildOAuthSignature(httpMethod, url, params))

        return oAuthHeader
    }

    def buildOAuthParamsString(Map params) {
        return (params.collectEntries ([:])  {key, value ->
            [URLEncoder.encode(key, "UTF-8"), URLEncoder.encode(value, "UTF-8") ] }
            .sort()
            .collect {k, v -> k + "=" + v }
            .join("&")
        )
    }

    def buildOAuthSignature(String httpMethod, String url, Map params) {
        def baseOAuthSignatureString = httpMethod.toUpperCase() + "&" +
                URLEncoder.encode(url, "UTF-8") + "&" +
                URLEncoder.encode(buildOAuthParamsString(params), "UTF-8")
        // STILL NEED TO TURN THIS INTO SIGNATURE
        return baseOAuthSignatureString
    }


    def start() {
       HttpClient client = vertx.createHttpClient(port: 443, host: "stream.twitter.com")
        def request = client.get("/statuses/sample") { resp ->
            println "Got a response: ${resp.statusCode}"
            container.logger.info("HELLO")
        }
        request.putHeader("Authorization", buildOAuthHeader("GET", "https://stream.twitter.com/statuses/sample"))
        request.end()
        container.logger.info("TwitterVerticle started")

    }

}
