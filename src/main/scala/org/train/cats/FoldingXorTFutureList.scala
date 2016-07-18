package org.train.cats

import cats._
import cats.data.XorT
import org.train.utlis.CatsUtils._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import cats.std.all._

/**
  * Created by nk91 on 18.07.16.
  */
object FoldingXorTFutureList extends FoldingList[Future] {

  override def foldTest[A](list: List[A]): XorT[Future, Exception, List[A]] = {
    val xorTList: List[XorT[Future, Exception, List[A]]] = transformToListXorT(
        list)
    fold(xorTList)
  }

  override def fold[A](xorTList: List[XorT[Future, Exception, List[A]]])
    : XorT[Future, Exception, List[A]] = {
    Foldable[List].fold(xorTList)(xorTFMonoid[A])
  }

  override def xorTFMonoid[T]: Monoid[XorT[Future, Exception, List[T]]] =
    new Monoid[XorT[Future, Exception, List[T]]] {
      override def empty: XorT[Future, Exception, List[T]] =
        List.empty[T].toFutureXorT

      override def combine(x: XorT[Future, Exception, List[T]],
                           y: XorT[Future, Exception, List[T]])
        : XorT[Future, Exception, List[T]] =
        for {
          v1 <- x
          v2 <- y
        } yield v1 ++ v2
    }

  override def transformToListXorT[A](
      list: List[A]): List[XorT[Future, Exception, List[A]]] = {
    val xorTList = list.map(_.toFutureXorT).map(x => x.map(List(_)))
    xorTList
  }

  override def toListOfXorTOption[A](
      list: List[A]): List[XorT[Future, Exception, A]] = {
    list.map(_.toFutureXorT)
  }

  override def foldMap[A](xorTList: List[XorT[Future, Exception, A]])
    : XorT[Future, Exception, List[A]] = {
    Foldable[List].foldMap(xorTList)(a => a.map(List(_)))(xorTFMonoid[A])
  }

}
