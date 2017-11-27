package scripts

import scala.io.Source

object RipGrepHelper extends App {

  bla("/Users/dreigada/IST/RC/proj/part2/testspark/sparkOut/0.01.out")

  def bla(file: String): Unit = {
    val lines = Source.fromFile(file).getLines().toStream
    val uniques = getUniques(lines)
    val cmd = getCommand(uniques)

    println(cmd)
  }

  private def getUniques(lines: Stream[String]): Stream[String] = {
    val regex = """\[(.*)\].*""".r

    lines
      .map {
        case regex(a) => a
      }
      .flatMap(_.split(","))
      .distinct
  }

  private def getCommand(ids: Stream[String]) = {
    val pattern =
      ids
        .map(_.trim)
        .map(id => s"^$id,")
        .mkString("(", "|", ")")

    s"""/usr/local/bin/rg -e "$pattern" /Users/dreigada/Desktop/RC/fullReddit/subsWithIdForReal.csv"""
  }
}
