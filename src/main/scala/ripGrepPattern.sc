val str = "304166,304162,304161,304158,304163"

val pattern =
  str
    .split(",")
    .map(id => s"^$id,")
    .mkString("(", "|", ")")

s"""rg -e "$pattern" /Users/dreigada/Desktop/RC/fullReddit/subsWithIdForReal.csv"""