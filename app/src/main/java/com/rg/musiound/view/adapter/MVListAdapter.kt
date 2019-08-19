package com.rg.musiound.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.bean.MV
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.setImageFromUrl
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/18
 */
class MVListAdapter(val list: List<MV>, val ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_HEADER = 0
    private val ITEM_COMMON = 1
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.find(R.id.iv_mv_cover)
        val title: TextView = view.find(R.id.tv_mv_title)
        val artist: TextView = view.find(R.id.tv_mv_artist)

        companion object {
            fun from(parent: ViewGroup): CardViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_fragment_mv_rank, parent, false)
                return CardViewHolder(view)
            }
        }
    }

//    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        companion object {
//            fun from(parent: ViewGroup): HeaderViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater.inflate(R.layout.recycle_item_activity_song_list_header, parent, false)
//                return HeaderViewHolder(view)
//            }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_COMMON -> CardViewHolder.from(
                parent
            )
//            ITEM_HEADER -> HeaderViewHolder.from(
//                parent
//            )
            else -> throw ClassCastException("unknown type of viewholder")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardViewHolder -> {
                val item = list[position]
                holder.imageView.setImageFromUrl(item.cover)
                holder.title.text = list[position].name
                holder.artist.text = list[position].artistName
                holder.itemView.setOnClickListener{
                    mOnItemClickListener?.onItemClick(position)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_COMMON
    }
}