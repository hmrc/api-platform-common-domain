import sbt._

object LibraryDependencies {
    def commonDomain(scalaVersion: String) = compileDependencies ++ testDependencies(scalaVersion).map(_ % "test")

    def root(scalaVersion: String) = compileDependencies ++ testDependencies(scalaVersion)
    
    lazy val compileDependencies = Seq(
      "org.playframework"       %% "play-json"                      % "3.0.6",
      "uk.gov.hmrc"             %% "play-json-union-formatter"      % "1.24.0",
      "org.typelevel"           %% "cats-core"                      % "2.13.0"
    )

    def testDependencies(scalaVersion: String) = Seq(
      "com.vladsch.flexmark"     % "flexmark-all"                   % "0.64.8",
      "org.scalatest"           %% "scalatest"                      % "3.2.19",
    ) ++ (
      CrossVersion.partialVersion(scalaVersion) match {
        case Some((2,_)) => Seq("org.mockito" %% "mockito-scala-scalatest" % "2.2.1")
        case _           => Seq.empty
      }
    )
}
