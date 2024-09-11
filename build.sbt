import scoverage.ScoverageKeys

val appName = "api-platform-common-domain"

Global / bloopAggregateSourceDependencies := true
Global / bloopExportJarClassifiers := Some(Set("sources"))

ThisBuild / majorVersion     := 0
ThisBuild / isPublicArtefact := true
ThisBuild / scalaVersion     := "3.3.3"

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

lazy val library = (project in file("."))
  .settings(
    publish / skip := true,
    scalacOptions += "-explain"
  )
  .aggregate(
    apiPlatformCommonDomain, apiPlatformTestCommonDomain
  )

lazy val apiPlatformCommonDomain = Project("api-platform-common-domain", file("api-platform-common-domain"))
  .settings(
    libraryDependencies ++= LibraryDependencies.commonDomain,
    scalacOptions += "-explain",
    ScoverageSettings(),
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-eT"),

    Compile / unmanagedSourceDirectories += baseDirectory.value / ".." / "common" / "src" / "main" / "scala",
    Test / unmanagedSourceDirectories += baseDirectory.value / ".." / "common" / "src" / "test" / "scala",
    
    Test / unmanagedSourceDirectories += baseDirectory.value / ".." / "test-common" / "src" / "main" / "scala"
  )
  .disablePlugins(JUnitXmlReportPlugin)


lazy val apiPlatformTestCommonDomain = Project("api-platform-test-common-domain", file("api-platform-test-common-domain"))
  .dependsOn(
    apiPlatformCommonDomain
  )
  .settings(
    libraryDependencies ++= LibraryDependencies.root,
    scalacOptions += "-explain",
    ScoverageKeys.coverageEnabled := false,
    Compile / unmanagedSourceDirectories += baseDirectory.value / ".." / "common" / "src" / "main" / "scala",
    Compile / unmanagedSourceDirectories += baseDirectory.value / ".." / "test-common" / "src" / "main" / "scala"
  )
  .disablePlugins(JUnitXmlReportPlugin)


commands ++= Seq(
  Command.command("run-all-tests") { state => "test" :: state },

  Command.command("clean-and-test") { state => "clean" :: "compile" :: "run-all-tests" :: state },

  // Coverage does not need compile !
  Command.command("pre-commit") { state => "clean" :: "scalafmtAll" :: "scalafixAll" :: "coverage" :: "run-all-tests" :: "coverageOff" :: "coverageAggregate" :: state }
)

