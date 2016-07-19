package org.train.cats

import cats.data.Xor.Right
import cats.data.{Xor, XorT}
import cats.std.all._
import org.train.rx.extention.SomeData

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by nk91 on 15.07.16.
  */
object CatsApplication extends App {

  val xorR = Xor.right(5)
  println(xorR.map(_ + 1))

  val xorL: String Xor Int = Xor.left("string")
  println(xorL.map(_ + 1))


  val someService = new XorTSomeService

  val result: XorT[Option, Exception, SomeData] = for {
    data1 <- someService.getRemoteDate()
    data2 <- someService.getSomeDate()
  } yield {
    mergeResult(data1, data2)
  }

  result.map(println)

  def mergeResult(d: SomeData, optData: SomeData): SomeData = SomeData(d.id, s"Merged ${d.data} with ${optData.data}")

}
