name := "recorridos"

libraryDependencies ++= Seq(
  guice,
  jdbc,
  "com.h2database" % "h2" % "1.4.197"
)

PlayKeys.devSettings += ("play.http.router", "recorridos.Routes")
