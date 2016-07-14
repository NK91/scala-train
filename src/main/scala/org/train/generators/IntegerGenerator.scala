package org.train.generators

import scala.util.Random

/**
  * Created by kisilnazar on 13.07.16.
  */
class IntegerGenerator extends Generator[Int] {
  val randomizer = new Random()
  def generate = randomizer.nextInt()
}

object Generators {

  val integers = new IntegerGenerator

  val integersPairs = for {
    x <- integers
    y <- integers
  } yield (x, y)

  val booleans = for {
     integer <- integers
  } yield integer > 0

  def single[T](x: T) = new Generator[T] {
    def generate = x
  }

  def choose(lower: Int, upper: Int) = for {
    integer <- integers
  } yield lower + integer % (upper - lower)

  def oneOf[T](xs: T*) = for(idx <- choose(0, xs.length)) yield xs(idx)

  def lists: Generator[List[Int]] = for {
    isEmpty <- booleans
    list <- if(isEmpty) emptyLists else nonEmptyLists
  } yield list

  def emptyLists: Generator[List[Int]] = single(Nil)

  def nonEmptyLists: Generator[List[Int]] = for {
    head <- integers
    tail <- lists
  } yield head :: tail

}
