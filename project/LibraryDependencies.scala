import sbt._

object LibraryDependencies {
  lazy val commonDomain = compileDependencies ++ testDependencies.map(_ % "test")

  lazy val root = compileDependencies ++ testDependencies
  
  lazy val compileDependencies = Seq(
    "com.typesafe.play"       %% "play-json"                      % "2.9.3",
    "uk.gov.hmrc"             %% "play-json-union-formatter"      % "1.18.0-play-28",
    "org.typelevel"           %% "cats-core"                      % "2.10.0"
  )

  lazy val testDependencies = Seq(
    "com.vladsch.flexmark"     % "flexmark-all"                   % "0.62.2",
    "org.mockito"             %% "mockito-scala-scalatest"        % "1.17.29",
    "org.scalatest"           %% "scalatest"                      % "3.2.17"
  )
}
