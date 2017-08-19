package view.star.com.hellokotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_list.*
import view.star.com.hellokotlin.model.Book

class ListFragment : Fragment() {
    var bookAdapter: BookAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        return view
    }

    fun generateBooks(count: Int): MutableList<Book> {
        val list = mutableListOf<Book>()
        var index = 0
        while (index < count) {
            var book: Book
            if (index % 3 == 0) {
                book = Book("Android开发艺术探索$index", "任玉刚$index")
            } else if (index % 3 == 1) {
                book = Book("Android群英传$index", "徐宜生$index")
            } else {
                book = Book("Android群英传-神兵利器$index", "徐宜生$index")
            }
            list.add(book)
            index++
        }
        return list
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        bookAdapter = BookAdapter().apply {
            books = generateBooks(10)
            listView.adapter = this
        }
    }

    class BookAdapter : BaseAdapter() {
        var books: MutableList<Book> = mutableListOf()

        override fun getView(position: Int, convertView: View?, container: ViewGroup): View {
            val data = getItem(position) as Book
            val view: View = convertView ?: LayoutInflater.from(container.context).inflate(R.layout.view_book_list_item, container, false).apply {
                tag = ViewHolder(this)
            }
            (view.tag as ViewHolder).apply {
                name.text = data.name
                author.text = data.author
            }
            return view
        }

        override fun getItem(position: Int): Any {
            return books[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return books.size
        }

    }

    class ViewHolder(itemView: View) {
        val author: TextView = itemView.findViewById(R.id.author)
        val name: TextView = itemView.findViewById(R.id.name)
    }

    companion object {
        fun newInstance(): Fragment {
            return ListFragment()
        }
    }

}