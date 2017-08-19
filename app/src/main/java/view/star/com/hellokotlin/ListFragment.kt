package view.star.com.hellokotlin

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list.*
import view.star.com.hellokotlin.model.Book

class ListFragment : Fragment(), ActionModeHandler.Callback<Book> {
    override fun onAction(actionId: Int, pendingItems: MutableList<Book>?): Boolean {
        if (pendingItems == null || pendingItems.isEmpty()) {
            Toast.makeText(context, "NO Selected Books", Toast.LENGTH_LONG).show()
            return false
        }
        val waitToDelete = pendingItems.indices.map { pendingItems[it] }
        AlertDialog.Builder(activity)
                .setTitle("删除书籍")
                .setMessage("你要删除选中的书籍嘛?")
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, { dialog, which ->
                    dialog.dismiss()
                    actionModeHandler?.pendingItems?.clear()
                    actionModeHandler?.dismiss()
                    bookAdapter?.deleteNotes(waitToDelete)
                })
                .setNegativeButton(android.R.string.no, { dialog, which -> dialog.dismiss() })
                .show()
        return true
    }

    override fun onDestroy(pendingItems: MutableList<Book>?) {
        if (pendingItems != null && pendingItems.isNotEmpty()) {
            bookAdapter?.invalidateAllSelected()
        }
    }

    var bookAdapter: BookAdapter? = null
    var actionModeHandler: ActionModeHandler<Book>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionModeHandler = ActionModeHandler(activity, this, R.menu.delete)
    }

    fun generateBooks(count: Int): MutableList<Book> {
        val list = mutableListOf<Book>()
        var index = 0
        while (index < count) {
            val book: Book = when (index % 3) {
                0 -> Book("Android开发艺术探索$index", "任玉刚$index")
                1 -> Book("Android群英传$index", "徐宜生$index")
                else -> Book("Android群英传-神兵利器$index", "徐宜生$index")
            }
            list.add(book)
            index++
        }
        return list
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        bookAdapter = BookAdapter().apply {
            books = generateBooks(100)
            listView.adapter = this
            listView.setOnItemLongClickListener { _, _, position, _ ->
                val isSelected: Boolean = actionModeHandler?.chooseItem(books[position]) ?: false
                setSelected(books[position], isSelected)
                return@setOnItemLongClickListener (true)
            }
        }
    }

    class BookAdapter : BaseAdapter() {
        var books: MutableList<Book> = mutableListOf()
        var selectedBooks: MutableList<Book> = mutableListOf()

        fun setSelected(book: Book, isSelected: Boolean) {
            if (!isSelected) {
                selectedBooks.remove(book)
                notifyDataSetChanged()
            } else if (!selectedBooks.contains(book)) {
                selectedBooks.add(book)
                notifyDataSetChanged()
            }
        }

        override fun getView(position: Int, convertView: View?, container: ViewGroup): View {
            val data = getItem(position) as Book
            return (convertView ?: LayoutInflater.from(container.context).inflate(R.layout.view_book_list_item, container, false).apply {
                tag = ViewHolder(this)
            }).apply {
                (tag as ViewHolder).apply {
                    name.text = data.name
                    author.text = data.author
                    this@apply.container.isSelected = selectedBooks.contains(data)
                }
            }
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

        fun invalidateAllSelected() {
            selectedBooks.clear()
            notifyDataSetChanged()
        }

        fun deleteNotes(notes: List<Book>) {
            books.removeAll(notes)
            notifyDataSetChanged()
        }

    }

    class ViewHolder(itemView: View) {
        val author: TextView = itemView.findViewById(R.id.author)
        val name: TextView = itemView.findViewById(R.id.name)
        val container: LinearLayout = itemView.findViewById(R.id.container)
    }

    companion object {
        fun newInstance(): Fragment {
            return ListFragment()
        }
    }

}