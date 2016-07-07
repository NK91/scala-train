package spec2.train

import org.specs2.mutable.Specification
import org.train.cats.XorSomeService

/**
  * Created by kisilnazar on 07.07.16.
  */
object XorSomeServiceSpecification extends Specification {

  "XorSomeService-specification" should {

    "service getSomeDate must be Right" in {
      val service = new XorSomeService
      service.getSomeDate().isRight must beTrue
    }

    "service getRemoteDate must be Right" in {
      val service = new XorSomeService
      service.getRemoteDate().isRight must beTrue
    }

  }

}
