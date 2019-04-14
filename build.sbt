name := """BiciApps"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(guice, jdbc, "com.h2database" % "h2" % "1.4.197")
