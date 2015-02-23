resolvers ++= Seq("typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/")

addSbtPlugin("uk.gov.hmrc" % "sbt-utils" % "2.1.0")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.7")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.1")