package spec2.train.cats

import cats.std.all._
import org.specs2.Specification
import org.specs2.specification.core.SpecStructure
import org.train.cats.{FoldingXorTFutureList, FoldingXorTOptionList}
import org.train.generators.Generators
import org.train.utlis.CatsUtils._
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by nk91 on 18.07.16.
  */
class FoldingXorTFutureSpecification extends Specification {

  override def is: SpecStructure = "Folding List of XorTs to XorT of List".header ^
    s2"""

        Fold:

        1. integers list test:        ${integersList}
        2. strings lists test:        ${stringsList}
        3. empty list test:           ${emptyList}
        4. random integers list test: ${integersList(Generators.lists.generate)}

        FoldMap:

        1. integers list test:        ${integersListFoldMap}
        2. strings lists test:        ${stringsListFoldMap}
        3. empty list test:           ${emptyListFoldMap}
        4. random integers list test: ${integersListFoldMap(Generators.lists.generate)}

      """

  val duration: Duration = 2 seconds

  def integersList() = {
    val testList = List(1, 2, 3, 5, 6)
    val listOfXorTs = testList.map(_.toFutureXorT).map(x => x.map(List(_)))
    val result = FoldingXorTFutureList.fold(listOfXorTs)
    Await.result(result.getOrElse(List.empty), duration) must_== testList
  }

  def stringsList() = {
    val testList = List("1", "2", "3", "5", "6")
    val listOfXorTs = testList.map(_.toFutureXorT).map(x => x.map(List(_)))
    val result = FoldingXorTFutureList.fold(listOfXorTs)
    Await.result(result.getOrElse(List.empty), duration) must_== testList
  }

  def emptyList() = {
    val testList = List.empty[AnyVal]
    val listOfXorTs = testList.map(_.toFutureXorT).map(x => x.map(List(_)))
    val result = FoldingXorTFutureList.fold(listOfXorTs)
    Await.result(result.getOrElse(List.empty), duration) must_== testList
  }

  def integersList(testList: List[Int]) = {
    val listOfXorTs = testList.map(_.toFutureXorT).map(x => x.map(List(_)))
    val result = FoldingXorTFutureList.fold(listOfXorTs)
    Await.result(result.getOrElse(List.empty), duration) must_== testList
  }



  def integersListFoldMap(testList: List[Int]) = {
    val result = FoldingXorTFutureList.foldMap(FoldingXorTFutureList.toListOfXorTOption(testList))
    Await.result(result.getOrElse(List.empty), duration) must_== testList
  }


  def integersListFoldMap() = {
    val testList = List(1, 2, 3, 5, 6)
    val result = FoldingXorTFutureList.foldMap(FoldingXorTFutureList.toListOfXorTOption(testList))
    Await.result(result.getOrElse(List.empty), duration) must_== testList
  }

  def stringsListFoldMap = {
    val testList = List("1", "2", "3", "5", "6")
    val result = FoldingXorTFutureList.foldMap(FoldingXorTFutureList.toListOfXorTOption(testList))
    Await.result(result.getOrElse(List.empty), duration) must_== testList
  }

  def emptyListFoldMap = {
    val testList = List.empty[AnyVal]
    val result = FoldingXorTFutureList.foldMap(FoldingXorTFutureList.toListOfXorTOption(testList))
    Await.result(result.getOrElse(List.empty), duration) must_== testList
  }
}
