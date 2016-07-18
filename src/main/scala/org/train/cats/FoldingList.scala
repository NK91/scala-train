package org.train.cats

import cats._
import cats.data.XorT

/**
  * Created by nk91 on 18.07.16.
  */
trait FoldingList[F[_]] {

  def foldTest[A](list: List[A]): XorT[F, Exception, List[A]]

  def fold[A](xorTList: List[XorT[F, Exception, List[A]]]): XorT[F, Exception, List[A]]

  def toListOfXorTOption[A](list: List[A]): List[XorT[F, Exception, A]]

  def foldMap[A](xorTList: List[XorT[F, Exception, A]]): XorT[F, Exception, List[A]]

  def transformToListXorT[A](list: List[A]): List[XorT[F, Exception, List[A]]]

  def xorTFMonoid[T]: Monoid[XorT[F, Exception, List[T]]]

}
