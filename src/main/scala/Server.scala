package frenetic.docs

import scala.util.{Success, Failure}
import akka.actor.ActorRef
import akka.util.{Timeout}
import akka.pattern.{pipe, ask}
import scala.concurrent._
import spray.can.Http
import spray.http._
import spray.json._
import scala.concurrent.duration._
import spray.routing._
import akka.actor.{Props, ActorLogging}
import akka.event.Logging.{InfoLevel, DebugLevel, WarningLevel}
import akka.contrib.throttle.TimerBasedThrottler
import akka.contrib.throttle.Throttler.{SetTarget, Rate}

class Server(settings: Settings)
  extends HttpServiceActor with ActorLogging {

  import context.dispatcher
  import spray.httpx.SprayJsonSupport._

  val builder = context.actorOf(Props(classOf[Builder], settings), "builder")

  val throttled =
    context.actorOf(Props(classOf[TimerBasedThrottler], new Rate(1, 10.minutes)),
                    "throttler")
  throttled ! SetTarget(Some(builder))

  def receive = runRoute {
    logRequest(("got a request", DebugLevel)) {
      (post & path("github-hook")) {
        complete {
          throttled ! Build
          "OK"
        }
      }
    }
  }

}