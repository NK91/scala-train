package org.train.rx

import org.train.rx.extention.{SomeData, SomeEitherService}

import scala.util.Try

/**
  * Created by kisilnazar on 04.07.16.
  */
class ExtendedEitherService extends SomeEitherService {

  def getSomeDataListWithServiceAndDataFilters(filterConditions: SomeData => Boolean, filterServiceConditions: SomeData => Boolean, mergeResult: (SomeData, SomeData) => SomeData): Product with Serializable with Either[Exception, List[SomeData]] = {
    def extractingSomeData(someDate: SomeData): Option[SomeData] = {
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

  def getOptionalSomeData(isWrongData: Boolean): Either[Exception, Option[SomeData]] =
    if (isWrongData) {
      Left(new Exception("Wrong date"))
    } else {
      Right(Some(someEvaluating()))
    }

  def getSomeDataList(capacity: Int): Either[Exception, List[SomeData]] =
    Try {
      Right((1 to capacity map (i => someEvaluating(i))).toList)
    } recover {
      case e: Exception => Left(e)
      case _ => Left(new Exception("Uknown exception"))
    } get
}
