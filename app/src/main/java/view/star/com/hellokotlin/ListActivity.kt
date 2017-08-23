package view.star.com.hellokotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class ListActivity : AppCompatActivity() {
    var listFragment: ListFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listFragment = ListFragment.newInstance() as ListFragment
        listFragment?.onClickListener = {
            parent, view, position, id ->
            Toast.makeText(this, "Hello 来自ListActivity", Toast.LENGTH_SHORT).show()
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, listFragment).commit()
    }
}