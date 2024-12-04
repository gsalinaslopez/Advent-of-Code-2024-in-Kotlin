
fun main() {
    fun multiply(instruction: String): Int =
        instruction
            .removePrefix("mul(")
            .removeSuffix(")")
            .split(",")
            .fold(1) { acc, entry -> acc * entry.toInt() }

    fun part1(input: List<String>): Int {
        var result = 0

        for (line in input) {
            val reg = Regex("""mul\([0-9]{1,3},[0-9]{1,3}\)""")
            val matches  = reg.findAll(line)

            matches.forEach {
                val instruction = it.groupValues[0]
                result += multiply(instruction)
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        var doMul = true

        for (line in input) {
            val reg = Regex("""mul\([0-9]{1,3},[0-9]{1,3}\)|do\(\)|don't\(\)""")
            val matches  = reg.findAll(line)

            matches.forEach {
                val instruction = it.groupValues[0]

                if (instruction == "do()") {
                    doMul = true
                } else if (instruction == "don't()") {
                    doMul = false
                } else {
                    if (doMul) {
                        result += multiply(instruction)
                    }
                }
            }
        }
        return result
    }

    var testInput = readInput("Day03_test_part1")
    check(part1(testInput) == 161)

    testInput = readInput("Day03_test_part2")
    check(part2(testInput) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
