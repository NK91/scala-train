package org.train.container

import cats.data.XorT
import io.getclump.Clump
import io.getclump._
import org.train.rx.extention.{SomeData, SomeService}
import org.train.container.Implicits._
import com.twitter.util.Future


/**
  * Created by kisilnazar on 24.07.16.
  */
class SomeClumpService extends SomeService[XorT[Clump,String, SomeData]]{

  type Exception = String

  override def getSomeDate(): XorT[Clump, Exception, SomeData] = {
    XorT.right[Clump, Exception, SomeData](Clump.apply(Option(SomeData(123,"SomeDate from option to clump")).get))
  }

  override def getRemoteDate(): XorT[Clump, Exception, SomeData] = {
    XorT.right[Clump, Exception, SomeData](Clump.future(futureSomeData))
  }

  def futureSomeData = Future.successful(
      SomeData(123, "SomeDate from option to clump")
  )


}
