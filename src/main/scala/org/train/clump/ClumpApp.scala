package org.train.clump

import cats.data.XorT
import com.twitter.util.Await
import io.getclump.Clump
import org.train.clump.Implicits._
import org.train.rx.extention.SomeData

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by kisilnazar on 24.07.16.
  */
object ClumpApp extends App {

  val service = new SomeClumpService

  val result: XorT[Clump, ClumpApp.service.Exception, SomeData] = for {
    data1 <- service.getSomeDate()
    data2 <- service.getRemoteDate()
  } yield {
    println(data1 + " " + data2)
    mergeResult(data1, data2)
  }

  for {
    r <- result
  } yield println("result: " + r)

  val r = Await.result(result.value.get)
  println(r.get)

  def mergeResult(d: SomeData, optData: SomeData): SomeData = SomeData(d.id, s"Merged ${d.data} with ${optData.data}")

}
