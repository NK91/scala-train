package spec2.train.services

import org.specs2.mutable.Specification
import org.train.rx.ExtendedEitherService
import org.train.rx.extention.SomeDate

/**
  * Created by kisilnazar on 04.07.16.
  */
object ExtendedEitherServiceSpecification extends Specification {

  "ExtendedEitherService-specification" should {

    "return optional SomeData" in {
      val service = new ExtendedEitherService
      service.getOptionalSomeData(isWrongData = true).isLeft must beTrue
      val someDataOptEither = service.getOptionalSomeData(isWrongData = false)
      someDataOptEither.isRight must beTrue
      someDataOptEither.right.get must beSome
    }

    "return list of SomeData" in {
      val service = new ExtendedEitherService
      val someDataListEither = service.getSomeDataList(10)
      someDataListEither.isRight must beTrue
      someDataListEither.right.get must size(10)

    }

    "open either from different sides" in {
      val service = new ExtendedEitherService

      val result: Either[Exception, List[SomeDate]] = service.getSomeDataListWithServiceAndDataFilters(filterConditions, filterServiceConditions, mergeResult)
      println(result)
      result.isRight must beTrue
      result.right.get.size must_== 2
    }
  }

  def filterConditions(d: SomeDate): Boolean = d.id % 2 == 1

  def filterServiceConditions(d: SomeDate): Boolean = d.id % 3 == 1

  def mergeResult(d: SomeDate, optData: SomeDate): SomeDate = SomeDate(d.id, s"Merged ${d.data} with ${optData.data}")

}
