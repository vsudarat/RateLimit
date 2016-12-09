package com.agoda.ratelimit

/**
  * Created by sudar on 12/8/2016.
  */
import spray.json.DefaultJsonProtocol

case class Hotel(city: String, hotelID: String, room: String, price: Int)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val hotelFormat = jsonFormat4(Hotel)
}
