package org.train.rx

import scala.util.Try

/**
  * Created by kisilnazar on 22.06.16.
  */
package extention {

  import rx.lang.scala.Observable

  trait Evaluation {
    def someEvaluating(): SomeData = SomeData(0, "SomeData from someEvaluating")
    def someEvaluating(index: Int): SomeData = SomeData(index, s"SomeData from someEvaluating with index=$index")
  }

  trait SomeService[T] extends Evaluation {

    def getSomeDate(): T

    def getRemoteDate(): T
  }

  class SomeEitherService extends SomeService[Either[Exception, SomeData]] {

    override def getSomeDate(): Either[Exception, SomeData] = {
      Try {
        Right(someEvaluating())
      } recover {
        case e: Exception => Left(e)
      } get
    }

    override def getRemoteDate(): Either[Exception, SomeData] = {
      Try {
        Right(SomeData(1, "RemoteDate"))
      } recover {
        case e: Exception => Left(e)
      } get
    }
  }

  class SomeRxService extends SomeService[Observable[SomeData]] {

    override def getSomeDate(): Observable[SomeData] = {
      Observable(subscription =>
        Try {
          subscription.onNext(someEvaluating())
        } recover {
          case e: Throwable => subscription.onError(e)
      })
    }

    override def getRemoteDate(): Observable[SomeData] = {
      Observable(subscription =>
        Try {
          subscription.onNext(SomeData(1, "RemoteDate"))
        } recover {
          case e: Throwable => subscription.onError(e)
      })
    }
  }

  case class ServiceException(message: String = "Some exception happen") extends Exception(message)

  case class SomeData(id: Long = 0, data: String = "default value")

}
