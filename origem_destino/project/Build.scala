import sbt._
import Keys._
import play.Play.autoImport._

object ApplicationBuild extends Build {

  val appName         = "origem_destino"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(ws)

val main = Project(appName, file(".")).enablePlugins(play.PlayScala).settings(
    version := appVersion,
    scalaVersion := "2.11.4",
    libraryDependencies ++= appDependencies
  )


}
