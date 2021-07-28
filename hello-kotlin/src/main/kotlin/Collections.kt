val numbers = listOf(1, 2, 3)

val people = mapOf("Mike" to 54, "John" to 34)

fun main(args: Array<String>) {
    for (n in numbers) println(n)
    numbers.forEach(::println)
    for ((name, age) in people) {
        println("$name is $age years old")
    }
}