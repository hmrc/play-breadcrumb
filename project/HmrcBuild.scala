import sbt._
import Keys._

import scala.util.Properties._

object HmrcBuild extends Build {

  import uk.gov.hmrc.DefaultBuildSettings
  import DefaultBuildSettings._
  import BuildDependencies._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}
  import play.core.PlayVersion
  import play.PlayImport._

  val nameApp = "play-breadcrumb"
  val versionApp = envOrElse("PLAY_BREADCRUMB_VERSION", "999-SNAPSHOT")

  val appDependencies = Seq(
    "com.typesafe.play" %% "play" % PlayVersion.current,
    "org.scalatest" %% "scalatest" % "2.2.1" % "test"
  )

  lazy val playBreadcrumb = Project(nameApp, file("."))
    .enablePlugins(play.PlayScala)
    .settings(version := versionApp)
    .settings(scalaSettings : _*)
    .settings(defaultSettings(false) : _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(versionApp),
      libraryDependencies ++= appDependencies,
      crossScalaVersions := Seq("2.11.5")
    )
}
