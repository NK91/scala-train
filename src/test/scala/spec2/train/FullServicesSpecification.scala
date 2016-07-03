package spec2.train

import org.specs2.Specification

/**
  * Created by kisilnazar on 03.07.16.
  */
object FullServicesSpecification extends Specification {

  def is =
    s2"""
       ${"EitherSpec" ~ EitherServiceSpecification}
       ${"RxSpec" ~ RxServiceSpecification}
     """

}