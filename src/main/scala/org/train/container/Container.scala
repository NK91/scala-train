package org.train.container

import cats.Monad

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by kisilnazar on 24.07.16.
  */
trait Container[+A] extends Awaitable[A] {

  def map[B](f: A => B): Container[B]

  def flatMap[B](f: A => Container[B]): Container[B]

}

class EmptyContainer[+A](value: A) extends Container[A] {
  override def map[B](f: (A) => B): Container[B] = new EmptyContainer[B](f(value))

  override def flatMap[B](f: (A) => Container[B]): Container[B] = f(value)

  @scala.throws[InterruptedException](classOf[InterruptedException])
  @scala.throws[TimeoutException](classOf[TimeoutException])
  override def ready(atMost: Duration)(implicit permit: CanAwait): EmptyContainer.this.type = this

  @scala.throws[Exception](classOf[Exception])
  override def result(atMost: Duration)(implicit permit: CanAwait): A = value
}

class FutureContainer[+A](future: Future[A]) extends Container[A] {
  override def map[B](f: (A) => B): Container[B] = new FutureContainer[B](future.map(f))

  // TODO: can be better!!! think more!!!!
  override def flatMap[B](f: (A) => Container[B]): Container[B] = {
    val promise = Promise[B]()
    future.onComplete {
      case Success(a) => f(a).map(b => promise.success(b))
      case Failure(exception) => promise.failure(exception)
    }
    new FutureContainer[B](promise.future)
  }

  @scala.throws[InterruptedException](classOf[InterruptedException])
  @scala.throws[TimeoutException](classOf[TimeoutException])
  override def ready(atMost: Duration)(implicit permit: CanAwait): FutureContainer.this.type = this

  @scala.throws[Exception](classOf[Exception])
  override def result(atMost: Duration)(implicit permit: CanAwait): A = Await.result(future, atMost)
}

object FutureContainer {
  def apply[A](future: Future[A]): FutureContainer[A] = new FutureContainer(future)

  implicit class FutureExtension[+A](val future: Future[A]) extends AnyVal {
    def toContainer = FutureContainer(future)
  }

}

class OptionContainer[+A](option: Option[A]) extends Container[A] {
  override def map[B](f: (A) => B): Container[B] = new OptionContainer[B](option.map(f))

  override def flatMap[B](f: (A) => Container[B]): Container[B] = option match {
    case Some(value) => f(value)
    case None => new OptionContainer[B](None)
  }

  @scala.throws[InterruptedException](classOf[InterruptedException])
  @scala.throws[TimeoutException](classOf[TimeoutException])
  override def ready(timeout: Duration)(implicit permit: CanAwait): OptionContainer.this.type = this

  @scala.throws[Exception](classOf[Exception])
  override def result(timeout: Duration)(implicit permit: CanAwait): A = option.get
}

object OptionContainer {
  def apply[A](option: Option[A]): OptionContainer[A] = new OptionContainer(option)

  implicit class OptionExtension[+A](val option: Option[A]) extends AnyVal {
    def toContainer = OptionContainer(option)
  }

}

object Container {

  implicit def monad = new Monad[Container] {
    def flatMap[A, B](fa: Container[A])(f: (A) => Container[B]): Container[B] = fa.flatMap(f)

    def pure[A](x: A): Container[A] = new EmptyContainer[A](x)
  }

}
