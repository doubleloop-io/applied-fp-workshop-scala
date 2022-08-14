addCommandAlias("fm", "all compile:scalafmt test:scalafmt")
addCommandAlias("cc", "all clean compile")
addCommandAlias("c", "compile")
addCommandAlias("r", "run")
addCommandAlias("t", "test")
addCommandAlias("to", "testOnly")
addCommandAlias("ps", "projects")
addCommandAlias("p", "project")

lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(scalarecap)

lazy val scalarecap = project
  .settings(
    name := "scalarecap",
    settings
  )

lazy val settings = Seq(
  organization      := "io.doubleloop",
  scalaVersion      := "3.1.3",
  semanticdbVersion := scalafixSemanticdb.revision, // only required for Scala 2.x
  semanticdbEnabled := true,                        // enable SemanticDB
  version           := "0.1.0-SNAPSHOT",
  scalacOptions ++= scalacSettings,
  resolvers ++= resolversSettings,
  libraryDependencies ++= libsSettings,
  testFrameworks += new TestFramework("munit.Framework")
)

lazy val scalacSettings = Seq(
)

lazy val resolversSettings = Seq(
  Resolver.sonatypeRepo("public"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

lazy val libsSettings = Seq(
  "org.scalameta" %% "munit"       % "0.7.29" % Test
)
