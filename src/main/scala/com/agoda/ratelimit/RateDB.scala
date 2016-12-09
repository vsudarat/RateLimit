package com.agoda.ratelimit

import scala.collection.mutable.ListBuffer
import scala.io.BufferedSource

/**
  * Created by sudar on 12/8/2016.
  */
class RateDB {

  val bufferedSource: BufferedSource = io.Source.fromURL(getClass.getResource("/hoteldb.csv"))
  var hotels = new ListBuffer[Hotel]()
  for (line <- bufferedSource.getLines.drop(1)) {
    // skip header
    val cols = line.split(",").map(_.trim)
    val hotel = Hotel(cols(0), cols(1), cols(2), cols(3).toInt)
    hotels += hotel
  }
  bufferedSource.close

  /**
    * get Hotel by city ID.
    *
    * @param cityID   - City ID
    * @param ordering - valid value are asc and desc
    * @return @Hotel
    */
  def get(cityID: String, ordering: String): List[Hotel] = {
    val filtered = hotels.filter(_.city.toLowerCase == cityID.toLowerCase())
    ordering match {
      case "asc" => filtered.sortBy(b => b.price).toList
      case "desc" => filtered.sortBy(b => b.price).reverse.toList
      case _ => filtered.toList // actually, ascending should be default but leave it up to requirement.
    }
  }
}
