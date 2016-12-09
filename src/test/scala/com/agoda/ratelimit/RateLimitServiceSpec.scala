package com.agoda.ratelimit

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._

class RateLimitServiceSpec extends Specification with Specs2RouteTest with RateLimitService {
  def actorRefFactory = system

  "RateLimitService" should {

    "leave GET requests to other paths unhandled" in {
      Get() ~> myRoute ~> check {
        handled must beFalse
        // responseAs[String] must contain("Say hello")
      }
    }

    "return a greeting for GET requests to the root path" in {
      Get("/hotels") ~> myRoute ~> check {
        handled must beFalse
      }
    }

    "return a hotel for PATH city ID as Bangkok" in {
      Get("/hotels/Bangkok") ~> myRoute ~> check {
        handled must beTrue
//        responseAs[List[Hotel]]
        responseAs[String] must contain("Bangkok")
      }
    }

    "return a hotel for PATH city ID as Bangkok with ?order=asc" in {
      Get("/hotels/Bangkok?order=asc") ~> myRoute ~> check {
        handled must beTrue
        responseAs[String] must contain("Bangkok")
        // assert asc result
      }
    }

    "return a hotel for PATH city ID as Bangkok with ?order=desc" in {
      Get("/hotels/Bangkok?order=desc") ~> myRoute ~> check {
        handled must beTrue
        responseAs[String] must contain("Bangkok")
        // assert desc result
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      Put("/hotels/Bangkok") ~> sealRoute(myRoute) ~> check {
        status === MethodNotAllowed
        responseAs[String] === "HTTP method not allowed, supported methods: GET"
      }
    }
  }
}
