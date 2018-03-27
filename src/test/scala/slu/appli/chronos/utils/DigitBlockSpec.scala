package slu.appli.chronos.utils

import org.scalatest.{FlatSpec, Matchers}

class DigitBlockSpec extends FlatSpec with Matchers {
  "DigitBox" should "be OK for 8" in {
    val max = 8
    DigitBlock(max) should be (DigitBlock(max, Option.empty[Int], Range(0,9).map(Left.apply)))
  }
  "DigitBox" should "be OK for 24" in {
    val max = 24
    DigitBlock(max) should be (DigitBlock(max, Option.empty[Int], Range(0,10).map {
      case leaf if leaf == 0 => Left.apply(leaf)
      case leaf if leaf > 2 => Left.apply(leaf)
      case block => Right.apply(block)
    }))
  }
  "DigitBox next(2)" should "be OK for 22" in {
    val max = 22
    DigitBlock.nextBlock(Right.apply(2), max) should be (Some(DigitBlock(max, Some(2), Vector(
      Left.apply(2),
      Left.apply(20),
      Left.apply(21),
      Left.apply(22)
    ))))
  }
}
