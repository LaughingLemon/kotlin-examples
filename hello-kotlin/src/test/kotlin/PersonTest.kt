import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import strikt.api.expect
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

data class Person(val name: String, val age: Int)

object PersonTest: Spek( {
    Feature("Person tests") {
        Scenario("check age when Person created") {
            var james: Person? = null
            var george: Person? = null
            Given("James is a Person aged 35") {
                james = Person("James",35)
            }
            When("George is cloned from James") {
                george = james?.copy("George")
            }
            Then("George should be 35 years of age") {
                expect {
                    that(george) {
                        isNotNull()
                        get { this?.age }.isEqualTo(35)
                    }
                }
            }
        }
    }
} )