package spec2.train.cats

import cats.std.all._
import org.specs2.Specification
import org.specs2.specification.core.SpecStructure
import org.train.cats.FoldingXorTOptionList
import org.train.generators.Generators
import org.train.utlis.CatsUtils._

/**
  * Created by kisilnazar on 17.07.16.
  */
class FoldingListXorTSpecification extends Specification {
  override def is: SpecStructure = "Folding List of XorTs to XorT of List".header ^
    s2"""
        1. integers list test:        ${integersList}
        2. strings lists test:        ${stringsList}
        3. empty list test:           ${emptyList}
        4. random integers list test: ${integersList(Generators.lists.generate)}
      """

  def integersList() = {
    val testList = List(1, 2, 3, 5, 6)
    val listOfXorTs = testList.map(_.toOptionXorT).map(x => x.map(List(_)))
    val result = FoldingXorTOptionList.fold(listOfXorTs)
    result.getOrElse(List.empty).get must_== testList
  }

  def stringsList() = {
    val testList = List("1", "2", "3", "5", "6")
    val listOfXorTs = testList.map(_.toOptionXorT).map(x => x.map(List(_)))
    val result = FoldingXorTOptionList.fold(listOfXorTs)
    result.getOrElse(List.empty).get must_== testList
  }

  def emptyList() = {
    val testList = List.empty[AnyVal]
    val listOfXorTs = testList.map(_.toOptionXorT).map(x => x.map(List(_)))
    val result = FoldingXorTOptionList.fold(listOfXorTs)
    result.getOrElse(List.empty).get must_== testList
  }

  def integersList(testList: List[Int]) = {
    val listOfXorTs = testList.map(_.toOptionXorT).map(x => x.map(List(_)))
    val result = FoldingXorTOptionList.fold(listOfXorTs)
    result.getOrElse(List.empty).get must_== testList
  }


}
