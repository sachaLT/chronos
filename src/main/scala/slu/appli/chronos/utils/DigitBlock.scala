package slu.appli.chronos.utils

case class DigitBlock (
  maxValue: Int,
  parentValue: Option[Int],
  values: Seq[Either[Int, Int]] // Right si value > 0 && value * 10 < maxValue, Left si <= maxValue
)





object DigitBlock {

  def apply(maxValue: Int): DigitBlock = {
    DigitBlock(maxValue, Option.empty[Int], generateValues(0, maxValue))
  }

  def nextBlock(value: Either[Int, Int], maxValue: Int): Option[DigitBlock] = {
    value.right.toOption.map { parentValue =>
      DigitBlock(maxValue, Some(parentValue), Left.apply(parentValue) +: generateValues(parentValue, maxValue))
    }
  }

  private def generateValues(parentValue: Int, maxValue: Int): Seq[Either[Int, Int]] = {
    val startValue = parentValue * 10
    val endValue = startValue + 9
    val top = if (endValue > maxValue) maxValue else endValue
    Range(startValue, top + 1).map {
      case leaf if isLeaf(leaf, maxValue) => Left(leaf)
      case other => Right(other)
    }
  }

  private def isLeaf(value: Int, maxValue: Int):Boolean = value == 0 || (value * 10) > maxValue

}
