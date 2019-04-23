name := """BiciApps"""

version := "1.0-SNAPSHOT"

lazy val kilometraje = (project in file("modules/kilometraje"))
  .enablePlugins(PlayScala)

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
  .dependsOn(kilometraje).aggregate(kilometraje)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(guice, javaForms, jdbc, "com.h2database" % "h2" % "1.4.197")

routesImport ++= Seq("models.registro._", "controllers.Binders._")
