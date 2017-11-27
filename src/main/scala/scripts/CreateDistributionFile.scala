package scripts


import scala.io.Source

object CreateDistributionFile extends App {

  val regex = """\[(.*)\], (.*)""".r

  val inputFile = "/Users/dreigada/IST/RC/proj/part2/testspark/sparkOut/0.001.out"
  val outputFile = "/Users/dreigada/IST/RC/proj/part2/testspark/sparkOut/distribution/0.001.count.dist.out"

  val output = ReplaceNames.outputStreamFromFile(outputFile)

  Source.fromFile(inputFile)
    .getLines()
    .toStream
    .map {
      case regex(idsStr, countStr) =>
        val ids = idsStr.split(",")
        val count = countStr.toInt

        count
    }
    .sorted
    .map(a => s"$a\n")
    .foreach(a => output.write(a.getBytes))

  output.close()

}
