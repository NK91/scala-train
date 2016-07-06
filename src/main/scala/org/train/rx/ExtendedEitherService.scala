package org.train.rx

import org.train.rx.extention.{SomeDate, SomeEitherService}

import scala.util.Try

/**
  * Created by kisilnazar on 04.07.16.
  */
class ExtendedEitherService extends SomeEitherService {

  def getSomeDataListWithServiceAndDataFilters(filterConditions: SomeDate => Boolean, filterServiceConditions: SomeDate => Boolean, mergeResult: (SomeDate, SomeDate) => SomeDate): Product with Serializable with Either[Exception, List[SomeDate]] = {
    def extractingSomeData(someDate: SomeDate): Option[SomeDate] = {
      getOptionalSomeData(filterServiceConditions(someDate)).right.toOption.flatten.map {
        optionalData => mergeResult(someDate, optionalData)
      }
    }


    val result = for {
      data <- getSomeDataList(6).right
    } yield for {
      d <- data.filter(filterConditions(_))
      optData <- extractingSomeData(d)
    } yield optData
    result
  }

  def getOptionalSomeData(isWrongData: Boolean): Either[Exception, Option[SomeDate]] =
    if (isWrongData) {
      Left(new Exception("Wrong date"))
    } else {
      Right(Some(someEvaluating()))
    }

  def getSomeDataList(capacity: Int): Either[Exception, List[SomeDate]] =
    Try {
      Right((1 to capacity map (i => someEvaluating(i))).toList)
    } recover {
      case e: Exception => Left(e)
      case _ => Left(new Exception("Uknown exception"))
    } get

  private def someEvaluating(i: Int): SomeDate = SomeDate(i, s"SomeDate with index $i")

}
