lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(demos, exercises, application, webapp)

lazy val demos = project
  .settings(
    name := "demos",
    settings
  )

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

lazy val webapp = project
  .settings(
    name := "webapp",
    settings
  )

lazy val settings = Seq(
  organization := "io.doubleloop",
  scalaVersion := "3.2.2",
  version := "0.1.0-SNAPSHOT",
  scalacOptions ++= scalacSettings,
  resolvers ++= resolversSettings,
  libraryDependencies ++= libsSettings,
  Test / parallelExecution := false,
  testFrameworks += new TestFramework("munit.Framework")
)

// More info:
// 2.x https://docs.scala-lang.org/overviews/compiler-options/index.html
// 3.x https://docs.scala-lang.org/scala3/guides/migration/options-new.html
// 2.x to 3.x https://docs.scala-lang.org/scala3/guides/migration/options-intro.html
lazy val scalacSettings = Seq(
  "-no-indent"
)

lazy val resolversSettings =
  Resolver.sonatypeOssRepos("public") ++
    Resolver.sonatypeOssRepos("snapshots") ++
    Resolver.sonatypeOssRepos("releases")

val CatsCoreVersion = "2.9.0"
val CatsEffectVersion = "3.5.7"
val CirisVersion = "3.0.0"
val Http4sVersion = "0.23.18"
val MunitVersion = "0.7.29"
val LogbackVersion = "1.4.14"
val MunitCatsEffectVersion = "1.0.7"

lazy val libsSettings = Seq(
  "org.typelevel" %% "cats-core" % CatsCoreVersion,
  "org.typelevel" %% "cats-effect" % CatsEffectVersion,
  "is.cir" %% "ciris" % CirisVersion,
  "org.http4s" %% "http4s-ember-server" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "ch.qos.logback" % "logback-classic" % LogbackVersion,
  "org.scalameta" %% "munit" % MunitVersion % Test,
  "org.typelevel" %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test
)
