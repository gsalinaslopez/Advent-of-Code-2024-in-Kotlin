import kotlin.math.abs

fun getColumns(lines: List<String>): Pair<List<Int>, List<Int>> =
    lines.map {
        it.split(" +".toRegex()).run {
            Pair(first().toInt(), last().toInt())
        }
    }.unzip()

fun main() {
    fun part1(input: List<String>): Int {
        val columns = getColumns(input)
        val leftColumn = columns.first.sorted()
        val rightColumn = columns.second.sorted()

        return leftColumn.zip(rightColumn) { l, r -> abs(l - r) }.sum()
    }

    fun part2(input: List<String>): Int {
        val columns = getColumns(input)
        val leftColumn = columns.first
        val rightColumn = columns.second

        val rightColumnCount = hashMapOf<Int, Int>()
        rightColumn.forEach { entry ->
            rightColumnCount[entry] = rightColumnCount.getOrDefault(entry, 0) + 1
        }

        return leftColumn.map { rightColumnCount.getOrDefault(it, 0) * it }.sum()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
