import scoverage.ScoverageKeys
import sbt._
import sbt.Keys._
import uk.gov.hmrc.DefaultBuildSettings.targetJvm
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
import bloop.integrations.sbt.BloopDefaults

val appName = "api-platform-common-domain"

val scala2_13 = "2.13.12"

ThisBuild / majorVersion     := 0
ThisBuild / isPublicArtefact := true
ThisBuild / scalaVersion     := scala2_13

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

lazy val library = (project in file("."))
  .settings(
    publish / skip := true
  )
  .aggregate(
    apiPlatformCommonDomain, apiPlatformCommonDomainFixtures, apiPlatformCommonDomainTest
  )


lazy val apiPlatformCommonDomain = Project("api-platform-common-domain", file("api-platform-common-domain"))
  .settings(
    libraryDependencies ++= LibraryDependencies.commonDomain,
    ScoverageSettings(),
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-eT"),
    Compile / unmanagedSourceDirectories += baseDirectory.value / ".." / "common" / "src" / "main" / "scala"
  )
  .disablePlugins(JUnitXmlReportPlugin)

lazy val apiPlatformCommonDomainFixtures = Project("api-platform-common-domain-fixtures", file("api-platform-common-domain-fixtures"))
  .dependsOn(
    apiPlatformCommonDomain % "compile"
  )
  .settings(
    libraryDependencies ++= LibraryDependencies.root,
    ScoverageKeys.coverageEnabled := false,
    Compile / unmanagedSourceDirectories += baseDirectory.value / ".." / "test-common" / "src" / "main" / "scala"
  )
  .disablePlugins(JUnitXmlReportPlugin)


lazy val apiPlatformCommonDomainTest = Project("api-platform-common-domain-test", file("api-platform-common-domain-test"))
  .dependsOn(
    apiPlatformCommonDomain,
    apiPlatformCommonDomainFixtures
  )
  .settings(
    publish / skip := true,
    libraryDependencies ++= LibraryDependencies.root,
    ScoverageSettings(),
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-eT"),
    Test / unmanagedSourceDirectories += baseDirectory.value / ".." / "common" / "src" / "test" / "scala",
    Test / unmanagedSourceDirectories += baseDirectory.value / ".." / "test-common" / "src" / "main" / "scala"
  )
  .disablePlugins(JUnitXmlReportPlugin)

commands ++= Seq(
  Command.command("run-all-tests") { state => "test" :: state },

  Command.command("clean-and-test") { state => "clean" :: "compile" :: "run-all-tests" :: state },

  // Coverage does not need compile !
  Command.command("pre-commit") { state => "clean" :: "scalafmtAll" :: "scalafixAll" :: "coverage" :: "run-all-tests" :: "coverageOff" :: "coverageAggregate" :: state }
)


Global / bloopAggregateSourceDependencies := true
