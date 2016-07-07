package spec2.train

import org.specs2.mutable.Specification
import org.train.cats.XorSomeService
import org.train.rx.ServiceWithExceptionEvaluation

/**
  * Created by kisilnazar on 07.07.16.
  */
object XorSomeServiceSpecification extends Specification {

  "XorSomeService-specification" should {

    "getSomeDate must be Right" in {
      val service = new XorSomeService
      service.getSomeDate().isRight must beTrue
    }

    "getSomeDate with evaluation error must be Left" in {
      val service = new XorSomeService with ServiceWithExceptionEvaluation
      service.getSomeDate().isLeft must beTrue
    }

    "getRemoteDate must be Right" in {
      val service = new XorSomeService
      service.getRemoteDate().isRight must beTrue
    }

    "getRemoteDate with evaluation error must be Right" in {
      val service = new XorSomeService with ServiceWithExceptionEvaluation
      service.getRemoteDate().isRight must beTrue
    }

  }

}
