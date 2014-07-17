import AssemblyKeys._

assemblySettings

name := "docs"

organization := "org.frenetic-lang"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.4"

scalacOptions ++=
  Seq("-deprecation",
      "-unchecked",
      "-feature",
      "-Xfatal-warnings")

val akkaVersion = "2.3.3"

val logbackVersion = "1.0.9"

val sprayVersion = "1.3.1"

lazy val logback =
  "ch.qos.logback" % "logback-classic" % logbackVersion % "runtime"

resolvers ++=
  Seq("Spray Repo" at "http://repo.spray.io",
      "Sonatype Snapshots" at
        "https://oss.sonatype.org/content/repositories/snapshots/")

libraryDependencies ++=
  Seq("io.spray" %% "spray-can" % sprayVersion,
      "io.spray" %% "spray-client" % sprayVersion,
      "io.spray" %% "spray-http" % sprayVersion,
      "io.spray" %% "spray-routing" % sprayVersion,
      "io.spray" %% "spray-json" % "1.2.6",
      "org.scalatest" %% "scalatest" % "2.2.0" % "test",
      "com.typesafe" % "config" % "1.2.1",
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-contrib" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "org.scala-lang.modules" %% "scala-async" % "0.9.1",
      "ch.qos.logback" % "logback-classic" % logbackVersion,
      "ch.qos.logback" % "logback-core" % logbackVersion,
      "edu.umass.cs" %% "docker" % "0.2-SNAPSHOT")


