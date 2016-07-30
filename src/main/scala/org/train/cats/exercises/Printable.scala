package org.train.cats.exercises

import cats.Show
import cats.implicits._

/**
  * Created by kisilnazar on 28.07.16.
  */
trait Printable[A] {

  def print(value: A): String

}

object PrintableDefaults {

  implicit val integerPrintable = new Printable[Int] {
    override def print(value: Int): String = value.toString
  }

  implicit val stringPrintable = new Printable[String] {
    override def print(value: String): String = value
  }

  implicit val catPrintable = new Printable[Cat] {
    override def print(cat: Cat): String = {
      val name = Print.format(cat.name)
      val age = Print.format(cat.age)
      val color = Print.format(cat.color)
      s"$name is a $age year-old $color cat."
    }
  }

  implicit val catShow = Show.show[Cat] { cat =>
    val name = Show[String].show(cat.name)
    val age = Show[Int].show(cat.age)
    val color = Show[String].show(cat.color)
    s"$name is a $age year-old $color cat."
  }

}

object Print {
  def format[A](value: A)(implicit printer: Printable[A]): String = {
    printer.print(value)
  }

  def print[A](value: A)(implicit printable: Printable[A]): Unit = {
    println(printable.print(value))
  }
}


final case class Cat(name: String, age: Int, color: String)