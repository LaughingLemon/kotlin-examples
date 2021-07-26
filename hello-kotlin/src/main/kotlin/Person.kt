interface Greeter {
    fun hello(name: String?): String = "Hello there $name!"
}

abstract class Person(val name: String, val age: Int): Greeter {
    override fun hello(name: String?) = "Hello $name, this is ${this.name}"
    abstract fun farewell(): String
}

class Customer(val id: Int, name: String, age: Int): Person(name, age) {
    override fun farewell() = "Bye"
}

fun main(args: Array<String>) {
    val person : Person = Customer(1,"John", 34)
    println(person.hello("Mark"))
    println(person.farewell())
}