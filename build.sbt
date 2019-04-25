name := """BiciApps"""

version := "1.0-SNAPSHOT"

lazy val common = (project in file("modules/common"))
  .enablePlugins(PlayJava, PlayEbean)

lazy val kilometraje = (project in file("modules/kilometraje"))
  .enablePlugins(PlayJava, PlayEbean)
  .dependsOn(common)

lazy val recomendaciones = (project in file("modules/recomendaciones"))
  .enablePlugins(PlayJava, PlayEbean)
  .dependsOn(common)

lazy val recorridos = (project in file("modules/recorridos"))
  .enablePlugins(PlayJava, PlayEbean)
  .dependsOn(common)

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
  .dependsOn(kilometraje,recomendaciones,recorridos, common).aggregate(kilometraje,recomendaciones,recorridos,common)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(guice, javaForms, jdbc, "com.h2database" % "h2" % "1.4.197")

routesImport ++= Seq("com.co.common.models.registro._", "controllers.Binders._")
