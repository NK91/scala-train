package spec2.train.services

import org.specs2.mutable.Specification
import org.train.rx.extention.SomeRxService

/**
  * Created by kisilnazar on 03.07.16.
  */
object RxServiceSpecification extends Specification {

  val TAG = getClass.getSimpleName

  tag(TAG)

  "RxService-specification" should {
    "SomeRxService.getRemoteDate must contains SomeData" in {
      val someService = new SomeRxService
      someService.getRemoteDate().toBlocking.headOption.isDefined must beTrue
    }
  }

}
