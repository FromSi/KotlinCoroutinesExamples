package kz.osmiumt.testapicoroutines

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_post.view.*

class RecyclerAdapterPost :
    RecyclerView.Adapter<RecyclerAdapterPost.ViewHoled>() {

    var list: List<Post> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int):
            ViewHoled = ViewHoled(LayoutInflater.from(p0.context).inflate(R.layout.card_post, p0, false))

    override fun onBindViewHolder(p0: ViewHoled, p1: Int) {
        p0.itemView.title.text = list[p1].title
        p0.itemView.body.text = list[p1].body
    }

    override fun getItemCount(): Int = list.size

    class ViewHoled(itemView: View) : RecyclerView.ViewHolder(itemView)
}