import sbt.Keys._
import sbt._

object HmrcBuild extends Build {
  import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
  import play.core.PlayVersion
  import uk.gov.hmrc.DefaultBuildSettings._
  import uk.gov.hmrc.PublishingSettings._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}

  val nameApp = "play-breadcrumb"
  val versionApp = "0.2.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.typesafe.play" %% "play" % PlayVersion.current,
    "org.scalatest" %% "scalatest" % "2.2.1" % "test"
  )

  lazy val playBreadcrumb = Project(nameApp, file("."))
    .enablePlugins(play.PlayScala)
    .enablePlugins(AutomateHeaderPlugin)
    .settings(version := versionApp)
    .settings(scalaSettings : _*)
    .settings(defaultSettings(false) : _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(versionApp),
      libraryDependencies ++= appDependencies,
      crossScalaVersions := Seq("2.11.5"),
      resolvers := Seq(
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
        "typesafe-snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
      )
    )
    .settings(publishAllArtefacts: _*)
    .settings(SbtBuildInfo(): _*)
    .settings(POMMetadata(): _*)
    .settings(HeaderSettings()) 
}

object POMMetadata {
  def apply() = {
      pomExtra :=
        <url>https://www.gov.uk/government/organisations/hm-revenue-customs</url>
        <licenses>
          <license>
            <name>Apache 2</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
          </license>
        </licenses>
          <scm>
            <connection>scm:git@github.com:hmrc/playbreadcrumb.git</connection>
            <developerConnection>scm:git@github.com:hmrc/playbreadcrumb.git</developerConnection>
            <url>git@github.com:hmrc/playbreadcrumb.git</url>
          </scm>
          <developers>
            <developer>
              <id>harish-hurchurn</id>
              <name>harishhurchurn</name>
              <url>http://www.ioctl.me</url>
            </developer>
            <developer>
              <id>ni</id>
              <name>nicfellows</name>
              <url>http://www.nicshouse.co.uk</url>
            </developer>
            <developer>
              <id>howyp</id>
              <name>PrasadSrimula</name>
              <url>http://www.hmrc.gov.uk</url>
            </developer>
          </developers>
  }
}

object HeaderSettings {
  import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
  import de.heikoseeberger.sbtheader.license.Apache2_0
 
  def apply() = headers := Map("scala" -> Apache2_0("2015", "HM Revenue & Customs"))
}
