import kotlin.math.abs

fun main() {
    fun checkIfSafe(row: List<Int>): Boolean {
        if (row[0] == row[1]) return false

        var increasing = row[0] < row[1]
        var safe = true
        for (i in 1 ..< row.size) {
            if (abs(row[i] - row[i - 1]) > 3) {
                safe = false
                break
            }
            if (increasing) {
                if (row[i] <= row[i - 1]) {
                    safe = false
                    break
                }
            } else if (row[i] >= row[i - 1]) {
                safe = false
                break
            }
        }
        return safe
    }

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { line ->
            val row = line.split(' ').map { it.toInt() }
            if (checkIfSafe(row)) result++
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.forEach { line ->
            val dummy = line.split(' ').map { it.toInt() }
            for (i in dummy.indices){
                val row = dummy.toList().toMutableList()
                row.removeAt(i)

                if (checkIfSafe(row)) {
                    result++
                    break
                }
            }
        }
        return result
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
