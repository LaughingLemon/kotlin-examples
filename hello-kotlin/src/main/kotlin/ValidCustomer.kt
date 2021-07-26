
data class ValidCustomer(val name: String)

interface CustomerEventListener {
    fun customerSaved(customer: ValidCustomer)
    fun customerDeleted(customer: ValidCustomer)
}

class CustomerService {
    companion object {
        val listeners = mutableListOf<CustomerEventListener>()
    }

    fun save(customer: ValidCustomer) {
        listeners.forEach { listener -> listener.customerSaved(customer) }
    }

    fun delete(customer: ValidCustomer) {
        listeners.forEach { listener -> listener.customerDeleted(customer) }
    }
}

fun main(args: Array<String>) {
    val service = CustomerService()
    CustomerService.listeners.add(object: CustomerEventListener {
        override fun customerSaved(customer: ValidCustomer) {
            println("$customer saved")
        }

        override fun customerDeleted(customer: ValidCustomer) {
            println("$customer deleted")
        }
    })

    service.save(ValidCustomer("John"))
}