package org.train.container

import cats.data.{Xor, XorT}

import scala.concurrent.Future

/**
  * Created by kisilnazar on 30.07.16.
  */
object ContainerExtensions {

  implicit class FutureContainerExtension[A, B](val futureXorT: XorT[Future, A, B]) extends AnyVal {
    def toContainer: XorT[Container, A, B] = {
      XorT(FutureContainer(futureXorT.value))
    }
  }

  implicit class OptionContainerExtension[A, B](val optionXorT: XorT[Option, A, B]) extends AnyVal {
    def toContainer: XorT[Container, A, B] = {
      XorT(OptionContainer(optionXorT.value))
    }
  }

  implicit class XorContainerExtension[A, B](val xor: Xor[A, B]) extends AnyVal {
    def toContainer: XorT[Container, A, B] = {
      XorT(new EmptyContainer(xor))
    }
  }

  implicit class ValueContainerExtension[A, B](val value: B) extends AnyVal {
    def toContainer: XorT[Container, A, B] = {
      XorT.right(new EmptyContainer(value))
    }

    def toContainerWithLeftType[C]: XorT[Container, C, B] = {
      XorT.right(new EmptyContainer(value))
    }
  }

}
