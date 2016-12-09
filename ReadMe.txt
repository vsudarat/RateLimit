For the solution of RateLimit, I would create some logic that work like a filter to verify the incomming request before process the request. The filter is defined as "RequestRateLimit" interface.
- It required to have "agoda-api-key" in request header. If there is no such a header then reject the request.
- The filter would closely work with "RateLimitCache" that would be a simple cache that keep things in the memory.
- RateLimitCache itself would has a Map<apiKey: String,cache: ExpiringCache>. 
- ExpiringCache would work as a timer to remove the cache when expire. ExpiringCache hold an information of api-key, active status and request count. Upon expire time, remove itself from "RateLimitCache". 
 
Upon request,
    - check if there is any api-key in the RateLimiteCache, If not, then create a new pair of api-key:ExpiringCache that would expire according to the configure in APIKeyStore.
    - if there is an invalid api-key then thrown an exception.
    - If there is an api-key exist in the RateLimiteCache,
		Check to see if it is active, else Reject the request with 403 HTTP status.
		If active, increase request count by 1. Remember that this information is keeping in expiring cache
		if the cache exist and the	request count is not more than the configured duration, then it safe to operate the request.
    
		However, if the request count exceeds the limitation then new expiring cache will be created to be replaced with that api-key and the value of RateLimit that has active  status as false and expiring time to 5 minutes.
    - The api-key that was suspended will be work again after the expire time of 5 minutes as the cache removal.

	