package com.example.pingPong

import akka.actor._
import scala.io.StdIn

case object PingMessage
case object StartMessage
case object StopMessage

class Ping extends Actor {
  def pingMessage() { println("ping " + self.path) }
  def pongMessage() { println("pong " + self.path) }

  val selection: ActorSelection = context.actorSelection("akka.tcp://PingPongSystem@127.0.0.1:2553/user/pong")

  def receive: PartialFunction[Any, Unit] = {
    case StartMessage =>
      pingMessage()
      selection ! PingMessage
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

object PingTest extends App {
  val system = ActorSystem("PingPongSystem")
  val ping = system.actorOf(Props[Ping], name = "ping")

  // start the ping pong
  ping ! StartMessage

  println(">>> Press ENTER to exit <<<")
  try {
    StdIn.readLine()
    ping ! StopMessage
  }
  finally system.terminate()

}