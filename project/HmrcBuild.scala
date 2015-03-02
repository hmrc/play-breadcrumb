import sbt.Keys._
import sbt._
import uk.gov.hmrc.SbtBuildInfo

object HmrcBuild extends Build {
  import play.core.PlayVersion
  import uk.gov.hmrc.DefaultBuildSettings._
  import uk.gov.hmrc.ShellPrompt

  val nameApp = "play-breadcrumb"
  val versionApp = "0.1.1"

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
      crossScalaVersions := Seq("2.11.5"),
      resolvers := Seq(
        Opts.resolver.sonatypeReleases,
        Opts.resolver.sonatypeSnapshots,
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
        "typesafe-snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
      )
    )
    .settings(SbtBuildInfo(): _*)
    .settings(SonatypeBuild(): _*)
}

object SonatypeBuild {

  import xerial.sbt.Sonatype._

  def apply() = {
    sonatypeSettings ++ Seq(
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
            <id>nicfellows</id>
            <name>Nic Fellows</name>
          </developer>
          <developer>
            <id>prasadsrimula</id>
            <name>Prasad Srimula</name>
          </developer>
          <developer>
            <id>harishhurchurn</id>
            <name>Harish Hurchurn</name>
          </developer>
        </developers>
    )
  }
}
