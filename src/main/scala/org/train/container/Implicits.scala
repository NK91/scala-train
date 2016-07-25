package org.train.container

import cats.{Functor, Monad}
import io.getclump.Clump

/**
  * Created by kisilnazar on 24.07.16.
  */
object Implicits {

  implicit def functor: Monad[Clump] = new Monad[Clump] {
    override def map[A, B](fa: Clump[A])(f: (A) => B): Clump[B] = fa.map(f)

    override def flatMap[A, B](fa: Clump[A])(f: (A) => Clump[B]): Clump[B] = fa.flatMap(f)

    override def pure[A](x: A): Clump[A] = Clump.empty[A]
  }

}
