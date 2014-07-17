package frenetic.docs

import spray.can.Http
import akka.io.IO
import akka.actor.{ActorSystem, Props}

object Main extends App {

  private def withSettings(settings : Settings) : Unit = {
   implicit val system = ActorSystem("doc-builder", settings.config)
   implicit val log = system.log
   val server = system.actorOf(Props(classOf[Server], settings), "server")
   IO(Http) ! Http.Bind(server, interface = "0.0.0.0", port = settings.port)

  }

  args.toList match {
    case List(settingsFile) => withSettings(Settings(settingsFile))
    case _ => {
      println("Expected settings file as argument.")
      System.exit(1)
    }
  }
}