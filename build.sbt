
lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(exercises, application)

lazy val exercises = project
  .settings(
    name := "exercises",
    settings
  )

lazy val application = project
  .settings(
    name := "application",
    settings
  )

lazy val settings = Seq(
  organization := "io.doubleloop",
  scalaVersion := "3.1.3",
  version := "0.1.0-SNAPSHOT",
  scalacOptions ++= scalacSettings,
  resolvers ++= resolversSettings,
  libraryDependencies ++= libsSettings,
  Test / parallelExecution := false,
  testFrameworks += new TestFramework("munit.Framework")
)

lazy val scalacSettings = Seq(
  "-no-indent"
)

lazy val resolversSettings = Seq(
  Resolver.sonatypeRepo("public"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

lazy val libsSettings = Seq(
  "org.typelevel" %% "cats-core" % "2.8.0",
  "org.typelevel" %% "cats-effect" % "3.3.14",
  "org.scalameta" %% "munit" % "0.7.29" % Test,
  "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test
)
