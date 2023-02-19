val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "lib_expr",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
  )

enablePlugins(Antlr4Plugin)
antlr4GenListener in Antlr4 := false
antlr4GenVisitor in Antlr4 := true
