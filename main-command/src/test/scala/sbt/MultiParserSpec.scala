/*
 * sbt
 * Copyright 2011 - 2018, Lightbend, Inc.
 * Copyright 2008 - 2010, Mark Harrah
 * Licensed under Apache License 2.0 (see LICENSE)
 */

package sbt

import org.scalatest.{ FlatSpec, Matchers }
import sbt.internal.util.complete.Parser

object MultiParserSpec {
  val parser: Parser[Seq[String]] = BasicCommands.multiParserImpl(None)
  implicit class StringOps(val s: String) {
    def parse: Seq[String] = Parser.parse(s, parser).right.get
  }
}
import MultiParserSpec._
class MultiParserSpec extends FlatSpec with Matchers {
  "parsing" should "parse single commands" in {
    ";foo".parse shouldBe Seq("foo")
    ";   foo".parse shouldBe Seq("foo")
  }
  it should "parse multiple commands" in {
    ";foo;bar".parse shouldBe Seq("foo", "bar")
  }
  it should "parse single command with leading spaces" in {
    ";     foo".parse shouldBe Seq("foo")
  }
  it should "parse multiple commands with leading spaces" in {
    ";     foo;bar".parse shouldBe Seq("foo", "bar")
    ";     foo;    bar".parse shouldBe Seq("foo", "bar")
    ";foo; bar".parse shouldBe Seq("foo", "bar")
  }
}
