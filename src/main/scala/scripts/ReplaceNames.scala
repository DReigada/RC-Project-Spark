package scripts

import java.io.{BufferedOutputStream, FileOutputStream, OutputStream}

import scala.io.Source

object ReplaceNames extends App {

  val file = "/Users/dreigada/IST/RC/proj/part2/testspark/sparkOut/0.01.out"
  val mapFile = "/Users/dreigada/IST/RC/proj/part2/testspark/grep0.01.out"
  val outFile = "/Users/dreigada/IST/RC/proj/part2/testspark/sparkOut/withNames/0.01WithNames.out"

  val regex = """\[(.*)\], (.*)""".r


  val output = outputStreamFromFile(outFile)


  val lines = Source.fromFile(file).getLines().toStream
  val mapLines = Source.fromFile(mapFile).getLines().toStream


  val namesMap = mapLines.foldLeft(Map.empty[Int, String]) {
    case (map, line) =>
      val bla = line.split(",")

      val id = bla(0).toInt

      if (bla.length > 1) {
        map + (id -> bla(1))
      } else if (id == 1822) {
        map + (id -> "null")
      } else {
        map
      }
  }


  val t = lines
    .map {
      case regex(subs, count) => (subs, count)
    }
    .map { case (subsArr, count) =>
      val subs = subsArr.split(",")

      val names =
        subs
          .map(_.toInt)
          .map(namesMap)
          .mkString("[", ", ", "]")


      (names, count.toInt, subs.length)
    }
    .sortBy(_._2)
    .map { case (names, count, _) => s"$names, $count\n" }

  t.foreach(a => output.write(a.getBytes))

  output.close()

  def outputStreamFromFile(file: String): OutputStream = {
    new BufferedOutputStream(new FileOutputStream(file))
  }
}
