import sbt.Tests.{SubProcess, Group}
import sbt._
import Keys._

object HmrcBuild extends Build {

  import uk.gov.hmrc.HmrcResolvers._
  import uk.gov.hmrc.PublishingSettings._
  import uk.gov.hmrc.NexusPublishing._
  import uk.gov.hmrc.DefaultBuildSettings
  import scala.util.Properties.envOrElse
  import DefaultBuildSettings._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}
  import TestPhases._
  val nameApp = "play-breadcrumb"
  val versionApp = envOrElse("HTTP_CACHING_CLIENT_VERSION", "999-SNAPSHOT")

  lazy val keyStoreClient = Project(nameApp, file("."))
    .enablePlugins(play.PlayScala)
    .settings(version := versionApp)
    .settings(scalaSettings : _*)
    .settings(defaultSettings() : _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(versionApp),
      libraryDependencies ++= AppDependencies(),
      crossScalaVersions := Seq("2.11.4")
    )
    .settings(publishAllArtefacts: _*)
    .settings(nexusPublishingSettings: _*)
    .settings(inConfig(TemplateTest)(Defaults.testSettings): _*)
    .configs(IntegrationTest)
    .settings(inConfig(TemplateItTest)(Defaults.itSettings): _*)
    .settings(
      Keys.fork in IntegrationTest := false,
      unmanagedSourceDirectories in IntegrationTest <<= (baseDirectory in IntegrationTest)(base => Seq(base / "it")),
      addTestReportOption(IntegrationTest, "int-test-reports"),
      testGrouping in IntegrationTest := oneForkedJvmPerTest((definedTests in IntegrationTest).value),
      parallelExecution in IntegrationTest := false)
    .settings(SbtBuildInfo(): _*)
}


private object TestPhases {

  val allPhases = "tt->test;test->test;test->compile;compile->compile"
  val allItPhases = "tit->it;it->it;it->compile;compile->compile"

  lazy val TemplateTest = config("tt") extend Test
  lazy val TemplateItTest = config("tit") extend IntegrationTest

  def oneForkedJvmPerTest(tests: Seq[TestDefinition]) =
    tests map {
      test => new Group(test.name, Seq(test), SubProcess(ForkOptions(runJVMOptions = Seq("-Dtest.name=" + test.name))))
    }
}
private object AppDependencies {

  import play.core.PlayVersion
  import play.PlayImport._

  private val playMicroServiceVersion = "5.5.0"

  val compile = Seq(
    "com.typesafe.play" %% "play" % PlayVersion.current % "provided",
    ws % "provided",
    "uk.gov.hmrc" %% "play-microservice" % playMicroServiceVersion % "provided",
    "uk.gov.hmrc" %% "json-encryption" % "1.5.0"
  )

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test : Seq[ModuleID] = ???
  }

  object Test {
    def apply() = new TestDependencies {
      override lazy val test = Seq(
        "org.scalatest" %% "scalatest" % "2.2.1" % scope,
        "org.pegdown" % "pegdown" % "1.4.2" % scope,

        "uk.gov.hmrc" %% "play-microservice" % playMicroServiceVersion % scope classifier "tests"
      )      
    }.test
  }

  object IntegrationTest {
    def apply() = new TestDependencies {

      override lazy val scope: String = "it"

      override lazy val test = Seq(
        "uk.gov.hmrc" %% "play-microservice" % playMicroServiceVersion % scope classifier "tests",
        "org.scalatest" %% "scalatest" % "2.2.0" % scope,
        "org.pegdown" % "pegdown" % "1.4.2" % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % scope
      )
    }.test
  }

  def apply() = compile ++ Test() ++ IntegrationTest()
}

