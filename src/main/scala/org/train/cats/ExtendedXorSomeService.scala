package org.train.cats

import cats.data.Xor
import org.train.rx.extention.SomeDate
import org.train.utlis.CatsUtils._
import org.train.utlis.IndexedExtractor

import scala.util.Try

/**
  * Created by nk91 on 11.07.16.
  */
class ExtendedXorSomeService extends XorSomeService with IndexedExtractor {

  def getSomeDataList(capacity: Int): Or[List[SomeDate]] =
    Try {
      Xor.right((1 to capacity map (i => someEvaluating(i))).toList)
    } recoverWithDefault() get

  def getSomeDataIndex(someDate: SomeDate) : Or[Int] = {
    Try {
      Xor.right(indexExtractor(someDate.id))
    } recoverWithDefault() get
  }


}
