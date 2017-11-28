# RC-Project-Spark

This repository contains scripts to analyse the network of Users-Subreddits of [reddit](reddit.com), using [Apache Spark](https://spark.apache.org/).

## Scripts

- [`FindSubredditsItemSet`](https://github.com/DReigada/RC-Project-Spark/blob/master/src/main/scala/rc/FindSubredditsItemSet.scala) 
    * Finds frequent itemsets in a file whose lines contain the items of each bucket (in our case: Users = buckets and Subreddits = items)
    * Arguments: `<inputFile> <outputFile> <minSupport> <minSetSize> <partitionsNumber>`

- [`GenerateAssociations`](https://github.com/DReigada/RC-Project-Spark/blob/master/src/main/scala/rc/GenerateAssociations.scala)
    * Generates associations rules based on the found frequent itemsets
    * Arguments: `<inputFile> <outputFile> <minConfidence> <partitionsNumber>`
    
## Requirements
- JDK
- scala (https://www.scala-lang.org/)
- sbt (http://www.scala-sbt.org/)
- spark distribution (https://spark.apache.org/downloads.html)
    
## Running

1. Generate the jar with `sbt package` (it should be created in `target/scala-2.11/rc-project-spark_2.11-0.1.0-SNAPSHOT.jar`)
2. Set the environment variable `SPARK_HOME` to your spark distribution directory
3. Run the desired script in `bin/`, using the path to the jar as the first argument followed by the arguments for the script

## Extras

The directory [`sparkOut/`](https://github.com/DReigada/RC-Project-Spark/tree/master/sparkOut) contains the data we generated from our dataset.