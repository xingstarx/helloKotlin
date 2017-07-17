package view.star.com.hellokotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import view.star.com.hellokotlin.service.LoginServiceImpl

class MainActivity : AppCompatActivity() {
    val loginService: LoginServiceImpl = LoginServiceImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView.text = "Android开发艺术探索"
        loginService.show()
    }
}
