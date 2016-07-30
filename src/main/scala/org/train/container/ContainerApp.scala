package org.train.container

import cats.data.{Xor, XorT}
import org.train.cats.{XorTFutureSomeService, XorTSomeService}
import org.train.container.Container._
import org.train.container.ContainerExtensions._
import org.train.rx.extention.SomeData

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by kisilnazar on 24.07.16.
  */
object ContainerApp extends App {


  val someContainerService = new SomeContainerService


  val result: XorT[Container, String, SomeData] = for {
    data1 <- someContainerService.getRemoteDate()
    data2 <- someContainerService.getSomeDate()
  } yield {
    mergeResult(data1, data2)
  }


  val someDateResult: Xor[String, SomeData] = Await.result(result.value, 2 seconds)
  println("service with container:" + someDateResult)


  val xorTOptionService = new XorTSomeService
  val xorTFutureService = new XorTFutureSomeService

  val secondResult = for {
    data1 <- xorTOptionService.getSomeDate().toContainer
    data2 <- xorTFutureService.getRemoteDate().toContainer
    data3 <- SomeData(24324, "Some TestedData created in for-comprehension").toContainerWithLeftType[Exception]
  } yield mergeResult(data1, mergeResult(data2, data3))

  val someSecondDateResult: Xor[Exception, SomeData] = Await.result(secondResult.value, 2 seconds)
  println("service with casting to container:" + someSecondDateResult)


  def mergeResult(d: SomeData, optData: SomeData): SomeData = SomeData(d.id, s"Merged ${d.data} with ${optData.data}")

}
