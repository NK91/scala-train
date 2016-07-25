package org.train.container

import cats.data.XorT
import org.train.rx.extention.{SomeData, SomeService}
import org.train.container.OptionContainer._
import org.train.container.FutureContainer._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by kisilnazar on 24.07.16.
  */
class SomeContainerService extends SomeService[XorT[OwnContainer, String, SomeData]]{
  override def getSomeDate(): XorT[OwnContainer, String, SomeData] = XorT.right[OwnContainer, String, SomeData](Option(SomeData(33,"somedate-option-container")).toContainer)

  override def getRemoteDate(): XorT[OwnContainer, String, SomeData] = XorT.right[OwnContainer, String, SomeData](Future.successful(SomeData(32133,"somedate-future-container")).toContainer)
}
