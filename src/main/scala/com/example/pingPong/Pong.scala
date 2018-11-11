package com.example.pingPong

import akka.actor._
import scala.io.StdIn

case object PongMessage

class Pong extends Actor {
  def pingMessage() { println("ping " + self.path) }
  def pongMessage() { println("pong " + self.path) }

  def receive: PartialFunction[Any, Unit] = {
    case PongMessage =>
      pongMessage()
      sender ! PingMessage
    case PingMessage =>
      pingMessage()
      sender ! PongMessage
    case StopMessage =>
      sender ! StopMessage
      println("ping stopped")
      context.stop(self)
  }
}

object PongTest extends App {
  val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong], name = "pong")

  // start the ping pong
  pong ! StartMessage

}