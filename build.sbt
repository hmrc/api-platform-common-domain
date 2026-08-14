import scoverage.ScoverageKeys
import sbt._
import sbt.Keys._
import uk.gov.hmrc.DefaultBuildSettings.targetJvm
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
import bloop.integrations.sbt.BloopDefaults

val libName = "api-platform-common-domain"

val scala2_13 = "2.13.18"
val scala3 = "3.3.7"

ThisBuild / majorVersion     := 1
ThisBuild / isPublicArtefact := true
ThisBuild / scalaVersion     := scala3
ThisBuild / crossScalaVersions := Seq(scala3, scala2_13)

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

lazy val commonSettings = Seq(
  scalafixConfig := {
    val base = (ThisBuild / baseDirectory).value
    val file =
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((3, _)) => base / ".scalafix-scala3.conf"
        case _            => base / ".scalafix-scala2.conf"
      }
    Some(file)
  },

  scalafmtConfig := {
    val base = (ThisBuild / baseDirectory).value
    val file =
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((3, _)) => base / ".scalafmt-scala3.conf"
        case _            => base / ".scalafmt-scala2.conf"
      }
    file
  }
)


lazy val library = (project in file("."))
  .settings(
    commonSettings,
    crossScalaVersions := Nil,
    publish / skip := true
  )
  .aggregate(
    apiPlatformCommonDomain, apiPlatformCommonDomainFixtures, apiPlatformCommonDomainTest
  )

lazy val apiPlatformCommonDomain = Project(s"$libName", file(s"$libName"))
  .settings(
    commonSettings,
    crossScalaVersions := Seq(scala3, scala2_13),
    libraryDependencies ++= LibraryDependencies.domain,
    ScoverageSettings(),
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-eT"),
    Compile / unmanagedSourceDirectories += (
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2,_)) => baseDirectory.value / ".." / "common" / "src" / "main" / "scala-2.13"
        case _           => baseDirectory.value / ".." / "common" / "src" / "main" / "scala-3"
      })
  )
  .disablePlugins(JUnitXmlReportPlugin)


lazy val apiPlatformCommonDomainFixtures = Project(s"$libName-fixtures", file(s"$libName-fixtures"))
  .dependsOn(
    apiPlatformCommonDomain,
  )
  .settings(
    commonSettings,
    crossScalaVersions := Seq(scala3, scala2_13),
    libraryDependencies ++= LibraryDependencies.fixtures,
    ScoverageKeys.coverageEnabled := false,
    Compile / unmanagedSourceDirectories += (
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2,_)) => baseDirectory.value / ".." / "test-common" / "src" / "main" / "scala-2.13"
        case _           => baseDirectory.value / ".." / "test-common" / "src" / "main" / "scala-3"
      })
  )
  .disablePlugins(JUnitXmlReportPlugin)


lazy val apiPlatformCommonDomainTest = Project(s"$libName-test", file(s"$libName-test"))
  .dependsOn(
    apiPlatformCommonDomain,
    apiPlatformCommonDomainFixtures
  )
  .settings(
    commonSettings,
    crossScalaVersions := Seq(scala3, scala2_13),
    publish / skip := true,
    libraryDependencies ++= LibraryDependencies.tests,
    ScoverageSettings(),
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-eT"),
    Test / unmanagedSourceDirectories ++= (
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2,_)) => 
          Seq(
            baseDirectory.value / ".." / "common" / "src" / "test" / "scala-2.13",
            baseDirectory.value / ".." / "test-common" / "src" / "main" / "scala-2.13"
          )
        case _           => 
          Seq(
            baseDirectory.value / ".." / "common" / "src" / "test" / "scala-3",
            baseDirectory.value / ".." / "test-common" / "src" / "main" / "scala-3"
          )
      })
  )
  .disablePlugins(JUnitXmlReportPlugin)

commands ++= Seq(
  Command.command("run-all-tests") { state => "test" :: state },
  Command.command("coverage-test") { state => "coverage" :: "run-all-tests" :: "coverageOff" :: "coverageAggregate" :: state },
  Command.command("check") { state => "clean" :: "coverage-test" :: state },
  Command.command("all") { state => "clean" :: "scalafmtAll" :: "scalafixAll" :: "coverage-test" :: state },

  Command.command("clean-and-test") { state => "clean" :: "run-all-tests" :: state },

  // Coverage does not need compile !
  Command.command("pre-commit") { state => "clean" :: "scalafmtAll" :: "scalafixAll" :: "coverage-test" :: state }
)


Global / bloopAggregateSourceDependencies := true
