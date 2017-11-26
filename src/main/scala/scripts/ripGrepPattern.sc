val str = "304160, 304163, 304155, 304158, 304161, 304162, 304156, 304165, 304166"

val pattern =
  str
    .split(",")
    .map(_.trim)
    .map(id => s"^$id,")
    .mkString("(", "|", ")")

s"""rg -e "$pattern" /Users/dreigada/Desktop/RC/fullReddit/subsWithIdForReal.csv"""