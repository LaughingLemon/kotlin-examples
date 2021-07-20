class Person(val name: String, val age: Int) {
    override fun toString(): String {
        return "Person(name: $name, age: $age)"
    }

    override fun equals(other: Any?): Boolean {
        return (other is Person && other.name == name)
    }
}

fun main(args: Array<String>) {
    val jamesJones = Person("James", 35)
    val jamesDavid = Person("James", 35)

    println(jamesJones)
    println(jamesDavid)
    println(jamesJones == jamesDavid)
}