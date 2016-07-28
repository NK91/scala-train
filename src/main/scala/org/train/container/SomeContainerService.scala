package org.train.container

import cats.data.XorT
import org.train.container.FutureContainer._
import org.train.container.OptionContainer._
import org.train.rx.extention.{SomeData, SomeService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by kisilnazar on 24.07.16.
  */
class SomeContainerService extends SomeService[XorT[Container, String, SomeData]] {
  override def getSomeDate(): XorT[Container, String, SomeData] =
    XorT.right[Container, String, SomeData](Option(SomeData()).toContainer)

  override def getRemoteDate(): XorT[Container, String, SomeData] =
    XorT.right[Container, String, SomeData](Future(SomeData()).toContainer)
}
