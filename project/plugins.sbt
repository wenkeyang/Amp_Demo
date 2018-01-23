/** better ivy / maven jar downloading,  updateClassifiers/ updateSbtClassifiers */
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-M14")

/** code checking, scalastyleGenerateConfig / scalastyle */
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.9.0")

/** for assembly to jar*/
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")