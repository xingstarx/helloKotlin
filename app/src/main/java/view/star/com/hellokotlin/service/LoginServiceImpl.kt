package view.star.com.hellokotlin.service

class LoginServiceImpl: LoginService() {
    var address: String? = null
    lateinit var zipCode: String

    fun show() {
        val str = javaClass.fields.map { it.name + " " }.toString()
        println("str == $str")
    }
}