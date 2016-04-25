/* 
 * Copyright (c) 2015 Jan Bessai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

lazy val commonSettings = Seq(
  organization := "de.tu_dortmund.cs.ls14",
  releaseUseGlobalVersion := false,
  releaseVersionBump := sbtrelease.Version.Bump.Major,
  releaseIgnoreUntrackedFiles := true,

  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),

  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-language:implicitConversions"
   )
 ) ++ publishSettings

lazy val core = (Project(id = "shapeless-feat", base = file("core"))).
  settings(commonSettings: _*).
  settings(
    moduleName := "shapeless-feat",
    libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
  )

lazy val examples = (Project(id = "shapeless-feat-examples", base = file("examples"))).
  settings(commonSettings: _*).
  settings(noPublishSettings: _*).
  dependsOn(core).
  settings(
    moduleName := "shapeless-feat-examples"
  )

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(noPublishSettings: _*).
  aggregate(core, examples).
  settings(
    moduleName := "shapeless-feat-root"
  )

lazy val publishSettings = Seq(
  publishMavenStyle := true,
	publishArtifact in Test := false,
	pomIncludeRepository := { _ => false },
	publishTo := {
  	val nexus = "https://oss.sonatype.org/"
	  if (isSnapshot.value)
  	  Some("snapshots" at nexus + "content/repositories/snapshots")
	  else
  	  Some("releases" at nexus + "service/local/staging/deploy/maven2")
	},
	homepage := Some(url("https://www.github.com/JanBessai/shapeless-feat")),
	licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
	scmInfo := Some(ScmInfo(url("https://github.com/JanBessai/shapeless-feat"), "scm:git:git@github.com:JanBessai/shapeless-feat.git")),
	pomExtra := (
  	<developers>
    	<developer>
      	<id>JanBessai</id>
	      <name>Jan Bessai</name>
  	    <url>http://janbessai.github.io/</url>
    	</developer>
	  </developers>
		)
	)

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
  )

