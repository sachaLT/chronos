package slu.appli.chronos.timer

import org.scalatest.{FlatSpec, Matchers}

class TimerItemSpec  extends FlatSpec with Matchers {

  "IntervalTimerItem" should "be processed" in {
    IntervalTimerItem(10000, () => ()).next() should be (true)
  }

  "IntervalTimerItem" should "be not processed" in {
    val timer = IntervalTimerItem(10000, () => ())
    timer.next() should be (true)
    timer.next() should be (false)
  }

  "IntervalTimerItem" should "be processed with previous ended by 60000" in {
    val timer = IntervalTimerItem(60000, () => (), TimerItem.firstInstanceForMinute)
    timer.next()
    timer.nextValue % 60000L should be (0L)
  }

  "SingleTimerItem" should "be processed once" in {
    val timer = SingleTimerItem(() => ())
    timer.next() should be (true)
    timer.next() should be (false)
    timer.nextValue should be (-1L)
  }
}
