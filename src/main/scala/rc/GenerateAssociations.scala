package rc

import java.io.File

import org.apache.spark.mllib.fpm.FPGrowth.FreqItemset
import org.apache.spark.mllib.fpm.FPGrowthModel
import org.apache.spark.{SparkConf, SparkContext}
import rc.FindSubredditsItemSet.{outputStreamFromFile, writeToStream}

object GenerateAssociations extends App {

  val inputFile = args(0)
  val outputFile = args(1)
  val confidence = args(2).toDouble
  val partitionsNumber = args(3).toInt


  if (new File(outputFile).exists()) {
    println(s"\n\n\n\n\n\nOutput file exists: $outputFile\n\n\n\n\n")
    System.out.print(1)
  }

  val regex = """\[(.*)\], (.*)""".r

  val conf = new SparkConf().setAppName("GenerateAssociations")
  val sc = new SparkContext(conf)


  val outputStream = outputStreamFromFile(outputFile)
  val write = writeToStream(outputStream)(_)


  val data = sc.textFile(inputFile, partitionsNumber)

  val t = data
    .map {
      case regex(idsStr, freq) =>
        val ids = idsStr.split(",").map(_.toInt)

        new FreqItemset(ids, freq.toLong)
    }


  new FPGrowthModel(t).generateAssociationRules(confidence)
    .collect
    .foreach { rule =>
      val antecedentStr = rule.antecedent.mkString("[", ",", "]")
      val consequentStr = rule.consequent.mkString("[", ",", "]")

      write(s"$antecedentStr => $consequentStr, ${rule.confidence}\n")
    }

}
