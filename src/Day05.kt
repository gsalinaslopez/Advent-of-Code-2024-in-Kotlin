
fun main() {
    fun getPageInstructions(input: List<String>): Pair<List<String>, List<String>> =
        input.indexOfFirst { it.isEmpty() }.let { emptyLineIndex ->
            val pageOrder = input.slice(0..<emptyLineIndex)
            val printOrder = input.slice(emptyLineIndex + 1..<input.size)
            Pair(pageOrder, printOrder)
        }

    fun getPageOrderGraph(pageOrder: List<String>): HashMap<String, MutableSet<String>> {
        val pageOrderGraph = HashMap<String, MutableSet<String>>()
        for (line in pageOrder) {
            val pages = line.split('|')
            val nextPages = pageOrderGraph.getOrDefault(pages.first(), mutableSetOf())
            nextPages.add(pages.last())
            pageOrderGraph[pages.first()] = nextPages
        }
        return pageOrderGraph
    }

    fun isValidPrintOrder(printOrder: String, pageOrderGraph: HashMap<String, MutableSet<String>>): Boolean {
        val pages = printOrder.split(',')
        var validLine = true
        for (i in 1..<pages.size) {
            if (pageOrderGraph.containsKey(pages[i - 1])) {
                if (pageOrderGraph[pages[i - 1]]!!.contains(pages[i])) {
                    continue
                }
            }
            validLine = false
            break
        }
        return validLine
    }

    fun part1(input: List<String>): Int {
        val (pageOrder, printOrder) = getPageInstructions(input)
        val pageOrderGraph = getPageOrderGraph(pageOrder)

        return printOrder.filter { s: String -> isValidPrintOrder(s, pageOrderGraph) }.sumOf {
            it.split(',').run { get(size / 2).toInt() }
        }
    }

    fun part2(input: List<String>): Int {
        val (pageOrder, printOrder) = getPageInstructions(input)
        val pageOrderGraph = getPageOrderGraph(pageOrder)

        return printOrder.filter { s: String ->  !isValidPrintOrder(s, pageOrderGraph) }.sumOf { instruction ->
            val pages = instruction.split(',')
            val pageList = HashMap<String, Set<String>>()
            val pagesInInstruction = pages.toSet()
            for (page in pages) {
                pageList[page] = pageOrderGraph.getOrDefault(page, mutableSetOf()).intersect(pagesInInstruction)
            }
            pageList.entries.sortedBy { it.value.size }.run { get(size / 2).key.toInt() }
        }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
