package frenetic.docs

import scala.async.Async.{async, await}
import scala.util.{Try, Success, Failure}
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.util.{Timeout}
import akka.pattern.{pipe, ask}
import scala.concurrent._
import scala.concurrent.duration._
import spray.routing._
import akka.event.Logging.{InfoLevel, DebugLevel, WarningLevel}

import plasma.docker.{Docker, container}

case object Build

class Builder(settings: Settings) extends Actor with ActorLogging {

  import context.dispatcher

  val cid = "doc"

  val docker = new Docker(settings.dockerUrl)

  def build() : Future[Unit] = async {
    log.info("Started build.")
    val config = container(settings.image).withNetwork(true)
    // TODO: possible warnings ignored
    await(docker.createContainer(config, Some(cid)))
    await(docker.startContainer(cid))
    val code = await(docker.waitContainer(cid))
    log.info(s"Container quit with code $code")
    if (code != 0) {
      log.error(new String(await(docker.logs(cid, false))))
      log.error(new String(await(docker.logs(cid, true))))
    }
    await(docker.deleteContainer(cid, true, true))
  }

  def buildRecover() = {
    Try(Await.result(build(), settings.hardTimeout)) match {
      case Failure(e) =>  {
        log.error("timeout building documentation")
        Await.result(docker.deleteContainer(cid, true, true), Duration.Inf)
      }
      case Success(_) => {
        log.info("building documentation succeeded")
      }
    }
  }

  def receive = {
    case Build => sender ! buildRecover()
  }

}