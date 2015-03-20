resolvers ++= Seq("typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/")

addSbtPlugin("de.heikoseeberger" % "sbt-header" % "1.3.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-utils" % "2.2.0")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.7")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.1")
