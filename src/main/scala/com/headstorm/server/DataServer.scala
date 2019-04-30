package com.headstorm.server

import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._

object Main extends IOApp {

  val httpService = HttpRoutes.of[IO] {
    case GET -> Root / "api"/ "season" / "location" / "latitude"/ latitude / "longitude" / longitude =>
      (latitude, longitude)
      Ok()
  }.orNotFound

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8081, "localhost")
      .withHttpApp(httpService)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}