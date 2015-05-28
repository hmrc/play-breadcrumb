import sbt.Keys._
import sbt._
import uk.gov.hmrc.versioning.SbtGitVersioning

object HmrcBuild extends Build {
  import play.core.PlayVersion
  import uk.gov.hmrc.DefaultBuildSettings._
  import uk.gov.hmrc._

  val nameApp = "play-breadcrumb"

  val appDependencies = Seq(
    "com.typesafe.play" %% "play" % PlayVersion.current,
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",
    "org.pegdown" % "pegdown" % "1.4.2" % "test"
  )

  lazy val playBreadcrumb = Project(nameApp, file("."))
    .enablePlugins(play.PlayScala, SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(
      targetJvm := "jvm-1.7",
      libraryDependencies ++= appDependencies,
      crossScalaVersions := Seq("2.11.5"),
      resolvers := Seq(
        Resolver.bintrayRepo("hmrc", "releases"),
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      )
    )
}