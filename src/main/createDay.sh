#bash

echo  "package days.impl\n\n\nimport days.impl.AdventOfCodeDayImpl\n\nclass Day$1 : AdventOfCodeDayImpl($1, 0L, 0L) {\n\n override fun partOne(input: List<String>): Long { return 0L}\noverride fun partTwo(input: List<String>): Long {return 0L}  }" > ./kotlin/days/impl/Day$1.kt;

touch ./resources/input/day-$1.txt;
touch ./resources/test/day-$1-1.txt;
touch ./resources/test/day-$1-2.txt;

git add *
