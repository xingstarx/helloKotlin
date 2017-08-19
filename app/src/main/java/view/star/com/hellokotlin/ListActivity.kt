package view.star.com.hellokotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.container, ListFragment.newInstance()).commit()
    }


}