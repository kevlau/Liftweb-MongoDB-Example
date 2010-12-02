import sbt._

class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) /*with stax.StaxPlugin with IdeaProject */ {
  val liftVersion = "2.2-M1"

  val slf4jVersion = "1.6.0"
  val sfl4japi = "org.slf4j" % "slf4j-api" % slf4jVersion
  val sfl4jlog4j = "org.slf4j" % "slf4j-log4j12" % slf4jVersion

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mongodb-record" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-testkit" % liftVersion % "compile->default",
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default",
    "junit" % "junit" % "4.5" % "test->default",
    "org.scala-tools.testing" %% "specs" % "1.6.5" % "test->default"
  ) ++ super.libraryDependencies

  //override def scanDirectories = ( temporaryWarPath / "WEB-INF" / "classes" ).get.toSeq
  override def scanDirectories = Nil

  /*
  val liftVersion = "2.2-SNAPSHOT"
  //val liftVersion = "2.2-M1"

  val scalatoolsSnapshot = ScalaToolsSnapshots
  //val scalatoolsSnapshot = "Scala Tools Snapshot" at "http://scala-tools.org/repo-snapshots/"

//
  //val lift_mongodb_record = "net.liftweb" % "lift-mongodb-record" % "2.2-M1"
  //val lift_mongo = "net.liftweb" % "lift-mongodb" % "2.2-M1"

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mongodb" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mongodb-record" % liftVersion % "compile->default",
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default",
    "junit" % "junit" % "4.5" % "test->default",
    //"org.scala-tools.testing" %% "specs" % "1.6.5" % "test->default",
    "com.h2database" % "h2" % "1.2.138"
  ) ++ super.libraryDependencies
  */
}


