data class Subscriber(val name: String, val email: String?, val subscriptions: List<String>)

val subscribers = listOf(
    Subscriber("John Jones", "jjones123@gmail.com", listOf("Linux Format", "3D World")),
    Subscriber("J Jones", "jjones123@gmail.com", listOf("Linux Format")),
    Subscriber("Arthur Grunthorpe", null, listOf("Architects Review", "3D World")),
    Subscriber("Keith Harrop", "kharrop@outlook.com", listOf("Linux Format")),
)

fun createSubscriptionEmail(magazineTitle: String, subs: Subscriber): String {
    return """
        email: ${subs.email}
        Dear ${subs.name},
        Your copy of $magazineTitle will be sent shortly.
        
    """.trimIndent()
}

fun main(args: Array<String>) {
    val magazinesSubscribed = subscribers.map { it.subscriptions }
        .fold(listOf<String>()) { list, item -> list.plus(item)}
        .distinct()
    val magazinesEmails =
        magazinesSubscribed.groupBy( keySelector = { it },
            valueTransform = { title ->
                //unfortunately this gives a list of list
                subscribers.filter { subs -> subs.subscriptions.contains(title) }
                    .filter { subs -> subs.email != null } } )
    for ((magazine, listOfSubs) in magazinesEmails) {
        for (sub in listOfSubs[0].distinctBy { sub -> sub.email }) {
            println(createSubscriptionEmail(magazine, sub))
        }
    }
}