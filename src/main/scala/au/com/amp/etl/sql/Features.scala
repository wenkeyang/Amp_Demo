package au.com.amp.etl.sql

/**
  * Created by ywksu on 1/23/2018.
  */
class Features {
  val sbcc = "select ts,price as bcc_price from bcc"
  val sbtc = "select ts,price as btc_price from btc"
}
