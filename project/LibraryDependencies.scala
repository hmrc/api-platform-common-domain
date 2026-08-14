import sbt._

object LibraryDependencies {

  lazy val domain =
    compileDependencies

  lazy val fixtures =
    commonTestDependencies
  
  lazy val tests =
    compileDependencies ++
    commonTestDependencies.map(_ % "test")

  private val compileDependencies = Seq(
    "org.playframework"       %% "play-json"                      % "3.0.6",
    "uk.gov.hmrc"             %% "play-json-union-formatter"      % "1.24.0",
    "org.typelevel"           %% "cats-core"                      % "2.13.0"
  )

  private val commonTestDependencies = Seq(
    "com.vladsch.flexmark"     % "flexmark-all"                   % "0.64.8",
    "org.scalatest"           %% "scalatest"                      % "3.2.19",
    "org.mockito"             %% "mockito-scala-scalatest"        % "2.2.1"
  )
}
