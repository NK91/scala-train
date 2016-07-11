package spec2.train.services

import org.specs2.Specification

/**
  * Created by kisilnazar on 03.07.16.
  */
object FullServicesSpecification extends Specification {

  def is =
    s2"""
       ${"EitherSpec" ~ EitherServiceSpecification}
       ${"RxSpec" ~ RxServiceSpecification}
       ${"ExtendedRxSpec" ~ ExtendedEitherServiceSpecification}
       ${"XorSpec" ~ XorSomeServiceSpecification}
       ${"ExtendedXorSpec" ~ ExtendedXorSomeServiceSpecification}
     """

}