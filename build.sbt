name := """BiciApps"""

version := "1.0-SNAPSHOT"

lazy val kilometraje = (project in file("modules/kilometraje"))
  .enablePlugins(PlayJava, PlayEbean)

lazy val recomendaciones = (project in file("modules/recomendaciones"))
  .enablePlugins(PlayJava, PlayEbean)

lazy val recorridos = (project in file("modules/recorridos"))
  .enablePlugins(PlayJava, PlayEbean)

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
  .dependsOn(kilometraje,recomendaciones,recorridos).aggregate(kilometraje,recomendaciones,recorridos)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(guice, javaForms, jdbc, "com.h2database" % "h2" % "1.4.197")

routesImport ++= Seq("models.registro._", "controllers.Binders._")
