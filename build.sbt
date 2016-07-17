name := "scala-train"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "io.reactivex" %% "rxscala" % "0.26.2"

libraryDependencies += "org.typelevel" %% "cats" % "0.6.1"

libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.8.4" % "test")

scalacOptions in Test ++= Seq("-Yrangepos")
    