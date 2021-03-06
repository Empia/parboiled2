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

package org.parboiled2.support

import shapeless._
import org.parboiled2.Rule
import shapeless.ops.hlist.ReversePrepend

// Provides the supported `~>` "overloads" for rule of type `Rule[I, O]` as `Out`
sealed trait ActionOps[I <: HList, O <: HList] { type Out }
object ActionOps {
  private type SJoin[I <: HList, O <: HList, R] = Join[I, HNil, O, R]

  implicit def ops0[I <: HList, O <: HNil] = new ActionOps[I, O] { type Out = Ops0[I] }
  sealed trait Ops0[I <: HList] {
    def apply[R](f: () ⇒ R)(implicit j: SJoin[I, HNil, R], c: FCapture[() ⇒ R]): Rule[j.In, j.Out]
    def apply[Z, R](f: Z ⇒ R)(implicit j: SJoin[Z :: I, HNil, R], c: FCapture[Z ⇒ R]): Rule[j.In, j.Out]
    def apply[Y, Z, R](f: (Y, Z) ⇒ R)(implicit j: SJoin[Y :: Z :: I, HNil, R], c: FCapture[(Y, Z) ⇒ R]): Rule[j.In, j.Out]
    def apply[X, Y, Z, R](f: (X, Y, Z) ⇒ R)(implicit j: SJoin[X :: Y :: Z :: I, HNil, R], c: FCapture[(X, Y, Z) ⇒ R]): Rule[j.In, j.Out]
    def apply[W, X, Y, Z, R](f: (W, X, Y, Z) ⇒ R)(implicit j: SJoin[W :: X :: Y :: Z :: I, HNil, R], c: FCapture[(W, X, Y, Z) ⇒ R]): Rule[j.In, j.Out]
    def apply[V, W, X, Y, Z, R](f: (V, W, X, Y, Z) ⇒ R)(implicit j: SJoin[V :: W :: X :: Y :: Z :: I, HNil, R], c: FCapture[(V, W, X, Y, Z) ⇒ R]): Rule[j.In, j.Out]
  }
  implicit def ops1[I <: HList, A] = new ActionOps[I, A :: HNil] { type Out = Ops1[I, A] }
  sealed trait Ops1[I <: HList, A] {
    def apply[R](f: () ⇒ R)(implicit j: SJoin[I, A :: HNil, R], c: FCapture[() ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: A ⇒ R)(implicit j: SJoin[I, HNil, R], c: FCapture[A ⇒ R]): Rule[j.In, j.Out]
    def apply[Z, R](f: (Z, A) ⇒ R)(implicit j: SJoin[Z :: I, HNil, R], c: FCapture[(Z, A) ⇒ R]): Rule[j.In, j.Out]
    def apply[Y, Z, R](f: (Y, Z, A) ⇒ R)(implicit j: SJoin[Y :: Z :: I, HNil, R], c: FCapture[(Y, Z, A) ⇒ R]): Rule[j.In, j.Out]
    def apply[X, Y, Z, R](f: (X, Y, Z, A) ⇒ R)(implicit j: SJoin[X :: Y :: Z :: I, HNil, R], c: FCapture[(X, Y, Z, A) ⇒ R]): Rule[j.In, j.Out]
    def apply[W, X, Y, Z, R](f: (W, X, Y, Z, A) ⇒ R)(implicit j: SJoin[W :: X :: Y :: Z :: I, HNil, R], c: FCapture[(W, X, Y, Z, A) ⇒ R]): Rule[j.In, j.Out]
  }
  implicit def ops2[I <: HList, A, B] = new ActionOps[I, A :: B :: HNil] { type Out = Ops2[I, A, B] }
  sealed trait Ops2[I <: HList, A, B] {
    def apply[R](f: () ⇒ R)(implicit j: SJoin[I, A :: B :: HNil, R], c: FCapture[() ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: B ⇒ R)(implicit j: SJoin[I, A :: HNil, R], c: FCapture[B ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: (A, B) ⇒ R)(implicit j: SJoin[I, HNil, R], c: FCapture[(A, B) ⇒ R]): Rule[j.In, j.Out]
    def apply[Z, R](f: (Z, A, B) ⇒ R)(implicit j: SJoin[Z :: I, HNil, R], c: FCapture[(Z, A, B) ⇒ R]): Rule[j.In, j.Out]
    def apply[Y, Z, R](f: (Y, Z, A, B) ⇒ R)(implicit j: SJoin[Y :: Z :: I, HNil, R], c: FCapture[(Y, Z, A, B) ⇒ R]): Rule[j.In, j.Out]
    def apply[X, Y, Z, R](f: (X, Y, Z, A, B) ⇒ R)(implicit j: SJoin[X :: Y :: Z :: I, HNil, R], c: FCapture[(X, Y, Z, A, B) ⇒ R]): Rule[j.In, j.Out]
  }
  implicit def ops3[I <: HList, A, B, C] = new ActionOps[I, A :: B :: C :: HNil] { type Out = Ops3[I, A, B, C] }
  sealed trait Ops3[I <: HList, A, B, C] {
    def apply[R](f: () ⇒ R)(implicit j: SJoin[I, A :: B :: C :: HNil, R], c: FCapture[() ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: C ⇒ R)(implicit j: SJoin[I, A :: B :: HNil, R], c: FCapture[C ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: (B, C) ⇒ R)(implicit j: SJoin[I, A :: HNil, R], c: FCapture[(B, C) ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: (A, B, C) ⇒ R)(implicit j: SJoin[I, HNil, R], c: FCapture[(A, B, C) ⇒ R]): Rule[j.In, j.Out]
    def apply[Z, R](f: (Z, A, B, C) ⇒ R)(implicit j: SJoin[Z :: I, HNil, R], c: FCapture[(Z, A, B, C) ⇒ R]): Rule[j.In, j.Out]
    def apply[Y, Z, R](f: (Y, Z, A, B, C) ⇒ R)(implicit j: SJoin[Y :: Z :: I, HNil, R], c: FCapture[(Y, Z, A, B, C) ⇒ R]): Rule[j.In, j.Out]
  }
  implicit def ops4[I <: HList, A, B, C, D] = new ActionOps[I, A :: B :: C :: D :: HNil] { type Out = Ops4[I, A, B, C, D] }
  sealed trait Ops4[I <: HList, A, B, C, D] {
    def apply[R](f: () ⇒ R)(implicit j: SJoin[I, A :: B :: C :: D :: HNil, R], c: FCapture[() ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: D ⇒ R)(implicit j: SJoin[I, A :: B :: C :: HNil, R], c: FCapture[D ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: (C, D) ⇒ R)(implicit j: SJoin[I, A :: B :: HNil, R], c: FCapture[(C, D) ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: (B, C, D) ⇒ R)(implicit j: SJoin[I, A :: HNil, R], c: FCapture[(B, C, D) ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: (A, B, C, D) ⇒ R)(implicit j: SJoin[I, HNil, R], c: FCapture[(A, B, C, D) ⇒ R]): Rule[j.In, j.Out]
    def apply[Z, R](f: (Z, A, B, C, D) ⇒ R)(implicit j: SJoin[Z :: I, HNil, R], c: FCapture[(Z, A, B, C, D) ⇒ R]): Rule[j.In, j.Out]
  }
  implicit def ops[I <: HList, O <: HList, OI <: HList, A, B, C, D, E](implicit x: TakeRight5[O, OI, A, B, C, D, E]) = new ActionOps[I, O] { type Out = Ops[I, OI, A, B, C, D, E] }
  sealed trait Ops[I <: HList, OI <: HList, A, B, C, D, E] {
    def apply[R](f: () ⇒ R)(implicit j: Join[I, OI, A :: B :: C :: D :: E :: HNil, R], c: FCapture[() ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: E ⇒ R)(implicit j: Join[I, OI, A :: B :: C :: D :: HNil, R], c: FCapture[E ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: (D, E) ⇒ R)(implicit j: Join[I, OI, A :: B :: C :: HNil, R], c: FCapture[(D, E) ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: (C, D, E) ⇒ R)(implicit j: Join[I, OI, A :: B :: HNil, R], c: FCapture[(C, D, E) ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: (B, C, D, E) ⇒ R)(implicit j: Join[I, OI, A :: HNil, R], c: FCapture[(B, C, D, E) ⇒ R]): Rule[j.In, j.Out]
    def apply[R](f: (A, B, C, D, E) ⇒ R)(implicit j: SJoin[I, OI, R], c: FCapture[(A, B, C, D, E) ⇒ R]): Rule[j.In, j.Out]
  }
}

// we want to support the "short case class notation" `... ~> Foo`
// unfortunately the Tree for the function argument to the `apply` overloads above does *not* allow us to inspect the
// function type which is why we capture it separately with this helper type
sealed trait FCapture[T]
object FCapture {
  implicit def apply[T]: FCapture[T] = `n/a`
}

// format: OFF

// builds `In` and `Out` types according to this logic:
//  if (R == Unit)
//    In = I, Out = L1 ::: L2
//  else if (R <: HList)
//    In = I, Out = L1 ::: L2 ::: R
//  else if (R <: Rule[I2, O2])
//    In = TailSwitch[I2, L1 ::: L2, I], Out = TailSwitch[L1 ::: L2, I2, O2]
//  else
//    In = I, Out = L1 ::: L2 ::: R :: HNil
sealed trait Join[I <: HList, L1 <: HList, L2 <: HList, R] {
  type In <: HList
  type Out <: HList
}
object Join {
  implicit def join[I <: HList, L1 <: HList, L2 <: HList, R, In0 <: HList, Out0 <: HList]
  (implicit x: Join0[I, L1, L2, R, HNil, In0, Out0]) =
    new Join[I, L1, L2, R] {
      type In = In0
      type Out = Out0
    }
}
sealed trait Join0[I <: HList, L1 <: HList, L2 <: HList, R, Acc <: HList, In <: HList, Out <: HList]
object Join0 extends LowerPriorityJoin0 {
  // if R == Unit convert to HNil
  implicit def forUnit[I <: HList, L1 <: HList, L2 <: HList, Acc <: HList, Out <: HList]
  (implicit x: Join0[I, L1, L2, HNil, Acc, I, Out]): Join0[I, L1, L2, Unit, Acc, I, Out] = `n/a`

  // if R <: HList and L1 non-empty move head of L1 to Acc
  implicit def iter1[I <: HList, H, T <: HList, L2 <: HList, R <: HList, Acc <: HList, Out <: HList]
  (implicit x: Join0[I, T, L2, R, H :: Acc, I, Out]): Join0[I, H :: T, L2, R, Acc, I, Out] = `n/a`

  // if R <: HList and L1 empty and L2 non-empty move head of L2 to Acc
  implicit def iter2[I <: HList, H, T <: HList, R <: HList, Acc <: HList, Out <: HList]
  (implicit x: Join0[I, HNil, T, R, H :: Acc, I, Out]): Join0[I, HNil, H :: T, R, Acc, I, Out] = `n/a`

  // if R <: HList and L1 and L2 empty set Out = reversePrepend Acc before R
  implicit def terminate[I <: HList, R <: HList, Acc <: HList, Out <: HList]
  (implicit x: ReversePrepend.Aux[Acc, R, Out]): Join0[I, HNil, HNil, R, Acc, I, Out] = `n/a`

  // if R <: Rule and L1 non-empty move head of L1 to Acc
  implicit def iterRule1[I <: HList, L2 <: HList, I2 <: HList, O2 <: HList, In0 <: HList, Acc <: HList, Out0 <: HList, H, T <: HList]
  (implicit x: Join0[I, T, L2, Rule[I2, O2], H :: Acc, In0, Out0]): Join0[I, H :: T, L2, Rule[I2, O2], HNil, In0, Out0] = `n/a`

  // if R <: Rule and L1 empty and Acc non-empty move head of Acc to L2
  implicit def iterRule2[I <: HList, L2 <: HList, I2 <: HList, O2 <: HList, In0 <: HList, Out0 <: HList, H, T <: HList]
  (implicit x: Join0[I, HNil, H :: L2, Rule[I2, O2], T, In0, Out0]): Join0[I, HNil, L2, Rule[I2, O2], H :: T, In0, Out0] = `n/a`

  // if R <: Rule and L1 and Acc empty set In and Out to tailswitches result
  implicit def terminateRule[I <: HList, O <: HList, I2 <: HList, O2 <: HList, In <: HList, Out <: HList]
  (implicit i: TailSwitch0[I2, I2, O, O, I, HNil, In], o: TailSwitch0[O, O, I2, I2, O2, HNil, Out]): Join0[I, HNil, O, Rule[I2, O2], HNil, In, Out] = `n/a`
}
private[parboiled2] abstract class LowerPriorityJoin0 {
  // convert R to R :: HNil
  implicit def forAny[I <: HList, L1 <: HList, L2 <: HList, R, Acc <: HList, Out <: HList](implicit x: Join0[I, L1, L2, R :: HNil, Acc, I, Out]): Join0[I, L1, L2, R, Acc, I, Out] = `n/a`
}

// format: ON

sealed trait TakeRight5[L <: HList, Init <: HList, A, B, C, D, E]
object TakeRight5 extends LowerPriorityMatchRight5 {
  implicit def forHList5[A, B, C, D, E]: TakeRight5[A :: B :: C :: D :: E :: HNil, HNil, A, B, C, D, E] = null
}
private[parboiled2] abstract class LowerPriorityMatchRight5 {
  implicit def forHList[H, T <: HList, Init <: HList, A, B, C, D, E](implicit x: TakeRight5[T, Init, A, B, C, D, E]): TakeRight5[H :: T, H :: Init, A, B, C, D, E] = null
}

