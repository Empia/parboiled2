/*
 * Copyright (C) 2009-2013 Mathias Doenitz, Alexander Myltsev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.parboiled2

import shapeless._

class ActionSpec extends TestParserSpec {

  "The Parser should correctly handle" >> {

    "`capture`" in new TestParser1[String] {
      def targetRule = rule { 'a' ~ capture(zeroOrMore('b')) ~ EOI }
      "a" must beMatchedWith("")
      "b" must beMismatched
      "ab" must beMatchedWith("b")
      "abb" must beMatchedWith("bb")
    }

    "`test`" in new TestParser0 {
      var flag = true
      def targetRule = rule { test(flag) }
      "x" must beMatched
      flag = false
      "x" must beMismatched
    }

    "`run(nonRuleExpr)`" in new TestParser0 {
      var flag = false
      def targetRule = rule { 'a' ~ run(flag = true) ~ EOI }
      "a" must beMatched
      flag must beTrue
    }

    "`run(ruleBlockWithRuleCall)`" in new TestParser0 {
      var flag = false
      def targetRule = rule { 'a' ~ run { flag = true; b } ~ EOI }
      def b = rule { 'b' }
      "a" must beMismatched
      flag must beTrue
      "ab" must beMatched
    }

    "`run(ruleBlockWithNestedRuleDef)`" in new TestParser0 {
      var flag = false
      def targetRule = rule { 'a' ~ run { flag = true; ch('b') } ~ EOI }
      "a" must beMismatched
      flag must beTrue
      "ab" must beMatched
    }

    "`push` simple value" in new TestParser1[String] {
      def targetRule = rule { 'x' ~ push(()) ~ push(HNil) ~ 'y' ~ push("yeah") ~ EOI }
      "xy" must beMatchedWith("yeah")
    }

    "`push` HList" in new TestParserN[Int :: Double :: Long :: String :: HNil] {
      def targetRule = rule { 'x' ~ push(42 :: 3.14 :: HNil) ~ push(0L :: "yeah" :: HNil) ~ EOI }
      "x" must beMatchedWith(42 :: 3.14 :: 0L :: "yeah" :: HNil)
      "y" must beMismatched
    }

    case class Foo(i: Int, s: String)

    "`~>` producing `Unit`" in new TestParser1[Int] {
      def testRule = rule { push(1 :: "X" :: HNil) ~> (_ ⇒ ()) }
      def targetRule = testRule
      "" must beMatchedWith(1)
    }

    "`~>` producing case class (simple notation)" in new TestParser1[Foo] {
      def targetRule = rule { push(1 :: "X" :: HNil) ~> Foo }
      "" must beMatchedWith(Foo(1, "X"))
    }

    "`~>` full take" in new TestParser1[Foo] {
      def testRule = rule { push(1 :: "X" :: HNil) ~> (Foo(_, _)) }
      def targetRule = testRule
      "" must beMatchedWith(Foo(1, "X"))
    }

    "`~>` partial take" in new TestParser1[Foo] {
      def testRule = rule { push(1) ~> (Foo(_, "X")) }
      def targetRule = testRule
      "" must beMatchedWith(Foo(1, "X"))
    }

    "`~>` producing HList" in new TestParserN[String :: Int :: Double :: HNil] {
      def testRule = rule { capture("x") ~> (_ :: 1 :: 3.0 :: HNil) }
      def targetRule = testRule
      "x" must beMatchedWith("x" :: 1 :: 3.0 :: HNil)
    }

    "`~>` with a statement block" in new TestParser1[Char] {
      var captured = ' '
      def testRule = rule { capture("x") ~> { x ⇒ captured = x.head; cursorChar } }
      def targetRule = testRule
      "xy" must beMatchedWith('y')
      captured === 'x'
    }

    "`~>` producing a Rule0" in new TestParser0 {
      def testRule = rule { capture("x") ~> (str(_)) ~ EOI }
      def targetRule = testRule
      "x" must beMismatched
      "xx" must beMatched
    }

    "`~>` producing a Rule1" in new TestParser1[String] {
      def testRule = rule { capture("x") ~> (capture(_)) ~ EOI }
      def targetRule = testRule
      "x" must beMismatched
      "xx" must beMatchedWith("x")
    }

    "`~>` producing an expression evaluating to a rule" in new TestParser0 {
      def testRule = rule { capture(anyOf("ab")) ~> (s ⇒ if (s == "a") rule('b') else rule('a')) ~ EOI }
      def targetRule = testRule
      "ab" must beMatched
      "ba" must beMatched
      "a" must beMismatched
      "b" must beMismatched
    }
  }
}