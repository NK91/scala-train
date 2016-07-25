package org.train.cats

/**
  * Created by nk91 on 22.07.16.
  */
object TestApp extends App {

  val phrase = "Роскомнадзор запретил букву"
  val deniedList: List[Char] = "АБВДЕЗИКЛМНОПРСТУ".toList

  def denyingChar(phrase: String, filteredUpperChar: Char): String = {
    println(phrase + " " + filteredUpperChar)
    phrase.filter(char => char.toUpper != filteredUpperChar)
  }

  def denying(phrase: String, deniedList: List[Char]): String =
    deniedList match {
      case char :: Nil => denyingChar(phrase, char)
      case head :: tail => denying(denyingChar(phrase, head), tail)
    }

  denying(phrase, deniedList)

}
