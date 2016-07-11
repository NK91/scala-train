package spec2.train.services

import org.specs2.mutable.Specification
import org.train.cats.ExtendedXorSomeService
import org.train.rx.extention.SomeDate
import org.train.utlis.IndexedExtractor

/**
  * Created by nk91 on 11.07.16.
  */
object ExtendedXorSomeServiceSpecification extends Specification {

  "ExtendedXorSomeService-specification" should {

    val CAPACITY = 10
    val mockSomeDate = SomeDate(101, "mock of SomeDate")

    s"getSomeDataList with capacity $CAPACITY" in {
      val service = new ExtendedXorSomeService
      val someDateOr = service.getSomeDataList(CAPACITY)
      someDateOr.isRight should beTrue
      someDateOr.getOrElse(List.empty).size should_== CAPACITY
    }

    s"getSomeDataIndex mockSomeDate=$mockSomeDate" in {
      val service = new ExtendedXorSomeService
      val mockedIndexedExtractor: IndexedExtractor = service
      val someIndexOr = service.getSomeDataIndex(mockSomeDate)
      someIndexOr.isRight should beTrue
      someIndexOr.getOrElse(0) should_== mockedIndexedExtractor.indexExtractor(mockSomeDate.id)
    }
  }

}
