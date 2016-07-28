package org.train.container

import cats.data.XorT
import org.train.container.Container._
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


  Await.result(result.value, 2 seconds)

  def mergeResult(d: SomeData, optData: SomeData): SomeData = SomeData(d.id, s"Merged ${d.data} with ${optData.data}")

}
