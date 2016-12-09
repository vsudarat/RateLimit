package com.agoda.ratelimit

/**
  * Work like a timer holding RequestRate information.
  *
  * Created by sudar on 12/9/2016.
  */
trait RateExpiringCache {
  val requestRate: RequestRate

  /**
    * do when expire
    */
  def expire() // when expire, remove itself from its container like RateLimitCache
}
