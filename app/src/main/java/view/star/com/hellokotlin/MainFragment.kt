package view.star.com.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
//        textView.text = "Android开发艺术探索"
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        textView.text = "Android开发艺术探索"
        button.setOnClickListener {
            startActivity(Intent(context, ListActivity::class.java))
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return MainFragment()
        }
    }
}