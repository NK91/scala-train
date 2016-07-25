package org.train.container
import cats.data.XorT
import org.train.container.Implicits._
import org.train.rx.extention.SomeData
import cats.std.all
import io.getclump.Clump

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import com.twitter.util.Await

/**
  * Created by kisilnazar on 24.07.16.
  */
object ClumpApp extends App {

  val service = new SomeClumpService

  val result: XorT[Clump, _root_.org.train.container.ClumpApp.service.Exception, SomeData] = for {
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
