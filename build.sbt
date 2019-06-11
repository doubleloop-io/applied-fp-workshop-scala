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
  .aggregate(exercises, marsroverkata, random, inventory, structures)

lazy val exercises = project
  .settings(
    name := "exercises",
    settings
  )

lazy val marsroverkata = project
  .settings(
    name := "marsroverkata",
    settings
  )

lazy val random = project
  .settings(
    name := "random",
    settings
  )

lazy val inventory = project
  .settings(
    name := "inventory",
    settings
  )

lazy val structures = project
  .settings(
    name := "structures",
    settings
  )

lazy val settings = Seq(
  organization := "io.doubleloop",
  scalaVersion := "2.12.8",
  version := "0.1.0-SNAPSHOT",
  scalacOptions ++= scalacSettings,
  resolvers ++= resolversSettings,
  libraryDependencies ++= libsSettings,
  testFrameworks += new TestFramework("minitest.runner.Framework"),
  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0"),
)

lazy val scalacSettings = Seq(
  "-encoding",
  "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-explaintypes",
  "-opt-warnings",
  "-language:existentials",
  "-language:higherKinds",
  "-opt:l:inline",
  "-opt-inline-from:<source>",
  "-Ypartial-unification",
  "-Yrangepos",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-extra-implicit",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Xsource:2.13",
  "-Xlint:_,-type-parameter-shadow,-unused",
  "-Xfuture",
  "-Xfatal-warnings"
)

lazy val resolversSettings = Seq(
  Resolver.sonatypeRepo("public"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

lazy val Http4sVersion  = "0.20.1"
lazy val LogbackVersion = "1.2.3"
lazy val CirceVersion   = "0.11.1"

lazy val libsSettings = Seq(
  "org.typelevel"  %% "cats-core"           % "1.6.1",
  "org.typelevel"  %% "cats-effect"         % "1.3.1",
  "org.typelevel"  %% "cats-mtl-core"       % "0.5.0",
  "net.debasishg"  %% "redisclient"         % "3.9",
  "io.circe"       %% "circe-core"          % CirceVersion,
  "io.circe"       %% "circe-generic"       % CirceVersion,
  "org.http4s"     %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s"     %% "http4s-circe"        % Http4sVersion,
  "org.http4s"     %% "http4s-dsl"          % Http4sVersion,
  "ch.qos.logback" % "logback-classic"      % LogbackVersion,
  "io.monix"       %% "minitest"            % "2.4.0" % Test
)
