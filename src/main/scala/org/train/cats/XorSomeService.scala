package org.train.cats

import cats.data.Xor
import org.train.rx.extention.{SomeDate, SomeService}

/**
  * Created by kisilnazar on 06.07.16.
  */
class XorSomeService extends SomeService[Xor[Exception, SomeDate]] {
  override def getSomeDate(): Xor[Exception, SomeDate] = ???

  override def getRemoteDate(): Xor[Exception, SomeDate] = ???
}
