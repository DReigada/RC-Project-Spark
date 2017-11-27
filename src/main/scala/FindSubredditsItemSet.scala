import java.io.{BufferedOutputStream, File, FileOutputStream, OutputStream}

import org.apache.spark.mllib.fpm.FPGrowth
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object FindSubredditsItemSet {

  def main(args: Array[String]) {

    val inputFile = args(0)
    val outputFile = args(1)
    val minSupport = args(2).toDouble
    val minSetSize = args(3).toInt
    val partitionsNumber = args(4).toInt

    if (new File(outputFile).exists()) {
      println(s"\n\n\n\n\n\nOutput file exists: $outputFile\n\n\n\n\n")
      System.out.print(1)
    }

    val outputStream = outputStreamFromFile(outputFile)
    val write = writeToStream(outputStream)(_)

    val conf = new SparkConf().setAppName("FindSubredditsItemSet")
    val sc = new SparkContext(conf)

    val data = sc.textFile(inputFile, partitionsNumber)

    val transactions: RDD[Array[String]] = data.map { line =>
      line
        .dropWhile(_ != ':')
        .drop(1) // remove the colon
        .trim
        .split(" ")
    }

    val fpg = new FPGrowth()
      .setMinSupport(minSupport)
      .setNumPartitions(partitionsNumber)
    val model = fpg.run(transactions)

    model.freqItemsets
      .filter(_.items.length >= minSetSize)
      //      .toLocalIterator
      .collect
      .foreach { itemset =>
        write(itemset.items.mkString("[", ",", "]") + ", " + itemset.freq + "\n")
      }

    outputStream.close()

    sc.stop()
  }


  def outputStreamFromFile(file: String): OutputStream = {
    new BufferedOutputStream(new FileOutputStream(file))
  }

  def writeToStream(output: OutputStream)(line: String) = {
    output.write(line.getBytes())
  }
}
