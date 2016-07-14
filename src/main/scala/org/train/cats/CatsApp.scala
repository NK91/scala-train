package org.train.cats

import cats.data.{Xor, XorT}
import org.train.rx.extention.SomeDate
import org.train.utlis.CatsUtils._

/**
  * Created by kisilnazar on 05.07.16.
  */
object CatsApp extends App {
  val xorR = Xor.right(5)
  println(xorR.map(_ + 1))

  val xorL: String Xor Int = Xor.left("string")
  println(xorL.map(_ + 1))


  val someService = new XorTSomeService

  val result: XorT[Option, Exception, SomeDate] = for {
    data1 <- someService.getRemoteDate()
    data2 <- someService.getSomeDate()
  } yield {
    mergeResult(data1, data2)
  }

  def mergeResult(d: SomeDate, optData: SomeDate): SomeDate = SomeDate(d.id, s"Merged ${d.data} with ${optData.data}")

}
