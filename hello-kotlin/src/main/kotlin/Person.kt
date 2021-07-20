interface Greeter {
    fun hello(name: String?): String = "Hello there $name!"
}

class Person(val name: String, val age: Int): Greeter {
    override fun hello(name: String?): String {
        return super.hello(this.name)
    }
}

fun main(args: Array<String>) {
    val person = Person("John", 34)
    print(person.hello(null));
}