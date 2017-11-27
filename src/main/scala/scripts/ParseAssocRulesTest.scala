package scripts

import rc.FindSubredditsItemSet.{outputStreamFromFile, writeToStream}

import scala.io.Source

object ParseAssocRulesTest extends App {
  val input = "0.6.out"


  val inputFile = s"/Users/dreigada/IST/RC/proj/part2/testspark/sparkOut/assoc/0.001.out/$input"
  val outputFile = s"/Users/dreigada/IST/RC/proj/part2/testspark/sparkOut/assoc/0.001.out/withNames/$input.grouped"

  val regex = """\[(.*)\] => \[(.*)\], (.*)""".r

  val names = new SubsNamesMap()

  val outputStream = outputStreamFromFile(outputFile)
  val write = writeToStream(outputStream)(_)


  Source.fromFile(inputFile).getLines().toStream
    .map {
      case regex(anteStr, conseqStr, confidenceStr) =>
        (anteStr, conseqStr, confidenceStr)
    }
    .groupBy(_._2)
    .map {
      case (conseq, stream) => (names.replaceByName(conseq), stream.size)
    }(collection.breakOut)
    .sortBy(_._2)
    .foreach {
      case (conseq, size) =>
        write(s"$conseq => $size\n")
    }

  outputStream.close()

}
