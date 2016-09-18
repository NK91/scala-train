package org.train.cats

import cats.data.Xor
import cats.implicits._
import cats.{Foldable, Monoid}

/**
  * Created by nk91 on 15.07.16.
  */
object CatsApplication extends App {

  val xorR = Xor.right(5)
  println(xorR.map(_ + 1))

  val xorL: String Xor Int = Xor.left("string")

  println("recover:" + xorL.recover {
    case value => value.length
  }.map(_ + 1))
  //
  //
  //  val someService = new XorTSomeService
  //  val xorTFutureSomeService = new XorTFutureSomeService
  val extendedSomeService = new ExtendedXorSomeService

  //
  //  val result = for {
  //    data1 <- someService.getRemoteDate()
  //    data2 <- someService.getSomeDate()
  //  } yield {
  //    mergeResult(data1, data2)
  //  }
  //
  //  result.map(println)
  //
  //  def mergeResult(d: SomeData, optData: SomeData): SomeData = SomeData(d.id, s"Merged ${d.data} with ${optData.data}")

  val monoid1: Monoid[Xor[Exception, List[Int]]] =
    new Monoid[Xor[Exception, List[Int]]] {
      override def empty: Xor[Exception, List[Int]] =
        Xor.right(List.empty[Int])

      override def combine(
                            x: Xor[Exception, List[Int]],
                            y: Xor[Exception, List[Int]]): Xor[Exception, List[Int]] =
        for {
          a <- x
          b <- y
        } yield a ++ b
    }

  val resultList0 = for {
    someDatas <- extendedSomeService
      .getSomeDataList(10)
      .map(_.map(extendedSomeService.getSomeDataIndex))
  } yield someDatas

  resultList0.fold(println, println)

  println(resultList0)

  val resultList = for {
    someDatas <- extendedSomeService.getSomeDataList(10)
    indexs <- Foldable[List].fold(
      someDatas
        .map(extendedSomeService.getSomeDataIndex)
        .map(a => a.map(List(_))))(monoid1)
  } yield indexs

  println(resultList)

}
