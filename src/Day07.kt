import java.math.BigInteger

fun main() {
    fun evaluateEquation(expectedTotal: BigInteger, constants: List<BigInteger>, operators: Array<Char>):Boolean {
        var operatorsIt = 0
        val equationStack = mutableListOf<Any>(constants[operatorsIt])
        for (operator in operators) {
            equationStack.addLast(operator).also { equationStack.addLast(constants[++operatorsIt]) }
        }

        var result = equationStack.first() as BigInteger
        for (i in 2..<equationStack.size step 2) {
            if (equationStack[i - 1] == '+') {
                result += equationStack[i] as BigInteger
            } else if (equationStack[i - 1] == '*') {
                result *= equationStack[i] as BigInteger
            } else if (equationStack[i - 1] == '|') {
                result = "$result${equationStack[i]}".toBigInteger()
            }
        }
        return expectedTotal == result
    }

    fun day7(input: List<String>, availableOperators: List<Char>): BigInteger {
        var result: BigInteger = "0".toBigInteger()
        for (line in input) {
            val equationInput = line.split(": ")
            val total = equationInput.first().toBigInteger()
            val constants = equationInput.last().split(' ').map { it.toBigInteger() }

            val operators = Array(constants.size - 1) { ' ' }

            fun fillOperators(it: Int): Boolean {
                if (it >= operators.size) return evaluateEquation(total, constants, operators)

                for (op in availableOperators) {
                    operators[it] = op
                    if (fillOperators(it + 1)) return true
                }

                return false
            }
            if (fillOperators(0)) result += total
        }
        return result
    }

    val part1AvailableOperators = listOf('+', '*')
    val part2AvailableOperators = listOf('+', '*', '|')

    val testInput = readInput("Day07_test")
    check(day7(testInput, part1AvailableOperators) == "3749".toBigInteger())
    check(day7(testInput, part2AvailableOperators) == "11387".toBigInteger())

    val input = readInput("Day07")
    day7(input, part1AvailableOperators).println()
    day7(input, part2AvailableOperators).println()
}
