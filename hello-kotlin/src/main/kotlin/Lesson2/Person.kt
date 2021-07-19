package Lesson2

data class Person(val name: String, val age: Int)

fun main(args: Array<String>) {
    val person = Person("John", 34)
    println(person)
    val (_, age) = person
    println(age)
}