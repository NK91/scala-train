package org.train.cats

import cats.{Foldable, Monoid}
import cats.data.XorT
import cats.std.all._
import org.train.utlis.CatsUtils._

/**
  * Created by kisilnazar on 17.07.16.
  */
object FoldingXorTOptionList {

  def foldTest[A](list: List[A]): XorT[Option, Exception, List[A]] = {
    val xorTList: List[XorT[Option, Exception, List[A]]] = transformToListXorT(list)
    fold(xorTList)
  }

  def fold[A](xorTList: List[XorT[Option, Exception, List[A]]]): XorT[Option, Exception, List[A]] = {
    Foldable[List].fold(xorTList)(xortMonoid[A])
  }

  private def xortMonoid[T]: Monoid[XorT[Option, Exception, List[T]]] = new Monoid[XorT[Option, Exception, List[T]]] {
    override def empty: XorT[Option, Exception, List[T]] = List.empty[T].toOptionXorT

    override def combine(x: XorT[Option, Exception, List[T]], y: XorT[Option, Exception, List[T]]): XorT[Option, Exception, List[T]] = for {
      v1 <- x
      v2 <- y
    } yield v1 ++ v2
  }

  def transformToListXorT[A](list: List[A]): List[XorT[Option, Exception, List[A]]] = {
    val xorTList = list.map(_.toOptionXorT).map(x => x.map(List(_)))
    xorTList
  }
}
