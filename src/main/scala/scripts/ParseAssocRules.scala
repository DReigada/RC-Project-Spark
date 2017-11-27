package scripts


import rc.FindSubredditsItemSet.{outputStreamFromFile, writeToStream}

import scala.io.Source

object ParseAssocRules extends App {

  val input = "0.6.out"


  val inputFile = s"/Users/dreigada/IST/RC/proj/part2/testspark/sparkOut/assoc/0.001.out/$input"
  val outputFile = s"/Users/dreigada/IST/RC/proj/part2/testspark/sparkOut/assoc/0.001.out/withNames/$input"

  val regex = """\[(.*)\] => \[(.*)\], (.*)""".r

  val names = new SubsNamesMap()

  val outputStream = outputStreamFromFile(outputFile)
  val write = writeToStream(outputStream)(_)


  Source.fromFile(inputFile).getLines().toStream
    .map {
      case regex(anteStr, conseqStr, confidenceStr) => (anteStr, conseqStr, confidenceStr)
    }
    .map {
      case (anteStr, conseqStr, confidenceStr) =>
        val antecedents =
          names
            .replaceByNames(anteStr.split(","))

        val consequents =
          names
            .replaceByNames(conseqStr.split(","))

        (antecedents, consequents, confidenceStr)
    }
    .sortBy(_._1.size)
    .foreach {
      case (antecedents, consequents, confidenceStr) =>
        val antecedentsStr = antecedents.mkString("[", ", ", "]")
        val consequentsStr = consequents.mkString("[", ", ", "]")

        write(s"$antecedentsStr => $consequentsStr, $confidenceStr\n")
    }

  outputStream.close()

}
