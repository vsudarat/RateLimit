package com.agoda.ratelimit

/**
  * Assume there is a registered API key published.
  * get to provide a Rate configuration per APIKey.
  * Invalid key will result in exception.
  *
  * Created by sudar on 12/9/2016.
  */
case class RequestRate(requestCount: Int, duration: Int, active: Boolean)

trait APIKeyStore {
  // define default
  val defaultRate = RequestRate(1, 10, true)
  // KeyStoreMap<apikey, RequestRate>

  def regis(apiKey: String, requestRate: RequestRate ) // rate would take default if not defined.
  def get(apiKey: String): RequestRate // exception when there is no api-key

}
