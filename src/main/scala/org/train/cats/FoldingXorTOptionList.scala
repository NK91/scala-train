package org.train.cats

import cats.data.XorT
import cats.std.all._
import cats.{Foldable, Monoid}
import org.train.utlis.CatsUtils._

/**
  * Created by kisilnazar on 17.07.16.
  */
object FoldingXorTOptionList extends FoldingList[Option] {

  def foldTest[A](list: List[A]): XorT[Option, Exception, List[A]] = {
    val xorTList: List[XorT[Option, Exception, List[A]]] = transformToListXorT(list)
    fold(xorTList)
  }

  def fold[A](xorTList: List[XorT[Option, Exception, List[A]]]): XorT[Option, Exception, List[A]] = {
    Foldable[List].fold(xorTList)(xorTFMonoid[A])
  }

  def xorTFMonoid[T]: Monoid[XorT[Option, Exception, List[T]]] =
    new Monoid[XorT[Option, Exception, List[T]]] {
      override def empty: XorT[Option, Exception, List[T]] =
        List.empty[T].toOptionXorT

      override def combine(x: XorT[Option, Exception, List[T]],
                           y: XorT[Option, Exception, List[T]]): XorT[Option, Exception, List[T]] =
        for {
          v1 <- x
          v2 <- y
        } yield v1 ++ v2
    }

  def transformToListXorT[A](list: List[A]): List[XorT[Option, Exception, List[A]]] = {
    val xorTList = list.map(_.toOptionXorT).map(x => x.map(List(_)))
    xorTList
  }

  def toListOfXorTOption[A](list: List[A]): List[XorT[Option, Exception, A]] = {
    list.map(_.toOptionXorT)
  }

  def foldMap[A](xorTList: List[XorT[Option, Exception, A]]): XorT[Option, Exception, List[A]] = {
    Foldable[List].foldMap(xorTList)(a => a.map(List(_)))(xorTFMonoid[A])
  }
}
