package au.com.amp.etl.util

/**
  * Created by ywksu on 1/24/2018.
  */
trait LoggingSupport {
  protected lazy val log = org.apache.log4j.Logger.getLogger(getClass)
}
