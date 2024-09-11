import sbt._

object LibraryDependencies {
  lazy val commonDomain = compileDependencies ++ testDependencies.map(_ % "test")

  lazy val root = compileDependencies ++ testDependencies
  
  lazy val compileDependencies = Seq(
    "org.playframework"       %% "play-json"                      % "3.0.4",
    "uk.gov.hmrc"             %% "play-json-union-formatter"      % "1.23.0-SNAPSHOT",
    "org.typelevel"           %% "cats-core"                      % "2.10.0"
  )

  lazy val testDependencies = Seq(
    "com.vladsch.flexmark"     % "flexmark-all"                   % "0.62.2",
    "org.scalatest"           %% "scalatest"                      % "3.2.17"
  )
}
