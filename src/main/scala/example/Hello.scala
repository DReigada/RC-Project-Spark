package example

import scala.util.Try

object Hello extends App {

  def f(str: String): Option[Int] = {
    Try(str.toInt).toOption
  }

  def f2(i: Int): Option[Int] = {
    Try(2 / i).toOption
  }


  def opt: Option[String] = Some("1")

  val t = for {
    str <- opt
    fres <- f(str)
    f2res <- f2(fres)
  } yield {
    f2res + 1
  }

  println(t)


}
