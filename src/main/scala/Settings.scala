package frenetic.docs

import com.typesafe.config.{ConfigFactory, Config}
import java.nio.file.{Paths}
import java.util.concurrent.TimeUnit
import scala.concurrent.duration._

class Settings(val config: Config) {

  import scala.collection.JavaConversions._

  val image  = config.getString("image")

  val dockerUrl = config.getString("dockerUrl")

  val port = config.getInt("port")

  val hardTimeout =
    config.getDuration("hard-timeout", TimeUnit.MILLISECONDS).milliseconds

 require(port >= 1 && port <= 65535,
         s"expected valid port number, got $port")

}

object Settings {

  def apply(path: String) = {
    val file = Paths.get(path).toFile
    val config = ConfigFactory.parseFile(file).resolve
      .withFallback(ConfigFactory.load)
    new Settings(config)
  }

}