val numbers = (1..100).toList()

fun main(args: Array<String>) {
    val mod7 = numbers.filter { num -> num % 7 == 0 }
    println(mod7)

    val tmp = numbers.asSequence()
        .filter { num -> num % 3 == 0 }
        .filterNot { num -> num % 6 == 0 }
    tmp.forEach{ print( "$it, " ) }
}