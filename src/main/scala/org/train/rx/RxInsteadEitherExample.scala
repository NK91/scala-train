package org.train.rx

import org.train.rx.extention.{SomeDate, SomeEitherService, SomeRxService}

/**
  * Created by kisilnazar on 22.06.16.
  */
object RxInsteadEitherExample extends App {

  // Either way
  val eitherService = new SomeEitherService()
  val eitherResult = for {localDate <- eitherService.getSomeDate().right
                          remoteDate <- eitherService.getRemoteDate().right
  } yield mergeResult(remoteDate, localDate)


  // RxScala way without ".right"!
  val rxService = new SomeRxService()
  val rxResult = for {localDate <- rxService.getSomeDate()
                      remoteDate <- rxService.getRemoteDate()
  } yield mergeResult(remoteDate, localDate)

  println(eitherResult.right.get)   // must be wrapped in future for async result
  println(rxResult.toBlocking.head) // already async result


  /**
    * Services with Exceptions
    */

  // Either way
  val eitherServiceWithException = new SomeEitherService() with ServiceWithExceptionEvaluation
  val eitherResultWithException = for {localDate <- eitherServiceWithException.getSomeDate().right
                                       remoteDate <- eitherServiceWithException.getRemoteDate().right
  } yield mergeResult(remoteDate, localDate)

  // RxScala way
  val rxServiceWithException = new SomeRxService() with ServiceWithExceptionEvaluation

  val rxResultWithException = for {localDate <- rxServiceWithException.getSomeDate()
                                   remoteDate <- rxServiceWithException.getRemoteDate()
  } yield mergeResult(remoteDate, localDate)



  println(eitherResultWithException.left.get)
  rxResultWithException.subscribe(println(_), println(_)) // onNext, onError


  def mergeResult(remoteDate: SomeDate, localDate: SomeDate): SomeDate = {
    SomeDate(3, remoteDate.data + " merged with " + localDate.data)
  }
}


