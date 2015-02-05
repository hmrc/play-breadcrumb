import sbt._
import Keys._

object HmrcBuild extends Build {

  import uk.gov.hmrc.DefaultBuildSettings
  import DefaultBuildSettings._
  import BuildDependencies._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}
  import play.core.PlayVersion
  import play.PlayImport._

  val nameApp = "play-breadcrumb"
  val versionApp = "0.0.0"

  val appDependencies = Seq(
    "com.typesafe.play" %% "play" % PlayVersion.current,
    ws % "provided",
    "org.scalatest" %% "scalatest" % "2.2.1" % "test"
  )

  lazy val playBreadcrumb = Project(nameApp, file("."))
    .enablePlugins(play.PlayScala)
    .settings(version := versionApp)
    .settings(scalaSettings : _*)
    .settings(defaultSettings() : _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(versionApp),
      libraryDependencies ++= appDependencies,
      resolvers := Seq(
        Opts.resolver.sonatypeReleases,
        Opts.resolver.sonatypeSnapshots,
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
        "typesafe-snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
      ),
      crossScalaVersions := Seq("2.11.5")
    )
}
