package org.train.rx

import org.train.rx.extention.{SomeDate, SomeEitherService}

import scala.util.Try

/**
  * Created by kisilnazar on 04.07.16.
  */
class ExtendedEitherService extends SomeEitherService {

  def getOptionalSomeData(isWrongData: Boolean): Either[Exception, Option[SomeDate]] =
    if (isWrongData) {
      Left(new Exception("Wrong date"))
    } else {
      Right(Some(someEvaluating()))
    }

  def someEvaluating(i: Int): SomeDate = SomeDate(i, s"SomeDate with index $i")

  def getSomeDataList(capacity: Int): Either[Exception, List[SomeDate]] =
    Try {
      Right((1 to capacity map (i => someEvaluating(i))).toList)
    } recover {
      case e: Exception => Left(e)
      case _ => Left(new Exception("Uknown exception"))
    } get

}
