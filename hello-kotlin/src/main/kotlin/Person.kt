class Person(val name: String, val age: Int) {
    override fun toString(): String {
        return "Person(name: $name, age: $age)"
    }
}

fun main(args: Array<String>) {
    val james = Person("James", 35)

    println(james)
}