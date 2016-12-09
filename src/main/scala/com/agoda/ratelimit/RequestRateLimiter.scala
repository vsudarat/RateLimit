package com.agoda.ratelimit

import spray.routing.Directive1

/**
  * Created by sudar on 12/9/2016.
  */
trait RequestRateLimiter[T] {
  /**
    * Upon request,
    * - check if there is no api-key in the RateLimiteCache then create a new pair of api-key:ExpiringCache that would
    * expire according to the configure in APIKeyStore.
    *
    * - if there is an invalid api-key then thrown an exception.
    *
    * - If there is an api-key exist in the RateLimiteCache,
    * Check to see if it is active, else Reject the request with 403 HTTP status.
    * If active, increase request count by 1. Remember that this information is keeping in expiring cache
    * if the cache exist and the	request count is not more than the configured duration
    * then it safe to operate the request.
    *
    * However, if the request count exceeds the limitation then new expiring cache will be created to be replaced
    * with that api-key and the value of RateLimit that has active 	status as false and expiring time to 5 minutes.
    * The api-key that was suspended will be work again after the expire time of 5 minutes as the cache removal.
    *
    * @param apikey
    */
  def doFilter(apikey: String)

  def limit[T](requestRateLimiter: RequestRateLimiter[T]): Directive1[T]
}
