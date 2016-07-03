package spec2.train

import org.specs2.mutable.Specification
import org.train.rx.ServiceWithExceptionEvaluation
import org.train.rx.extention.SomeEitherService

/**
  * Created by kisilnazar on 03.07.16.
  */
object EitherServiceSpecification extends Specification {

  val TAG = getClass.getSimpleName

  tag(TAG)

  "EitherService-specification" should {
    "SomeEitherService.getRemouteData must be right" in {
      val someService = new SomeEitherService
      someService.getRemoteDate().isRight must beTrue
    }

    "SomeEitherService.getRemouteData with error must be left" in {
      val someService = new SomeEitherService with ServiceWithExceptionEvaluation
      someService.getSomeDate().isLeft must beTrue
    }
  }

}
