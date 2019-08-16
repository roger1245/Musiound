package com.rg.musiound.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.bean.SongList
import com.rg.musiound.util.extensions.setImageFromUrl
import kotlinx.android.synthetic.main.recycler_item_activity_song_list_common.view.*
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/16
 */
class SongListAdapter(val list: List<SongList>, val ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_HEADER = 0
    private val ITEM_COMMON = 1
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.find(R.id.iv_recycler_item_song_list)
        val title: TextView = view.find(R.id.tv_recycler_item_song_list)

        companion object {
            fun from(parent: ViewGroup): CardViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycler_item_activity_song_list_common, parent, false)
                return CardViewHolder(view)
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycler_item_activity_song_list_header, parent, false)
                return HeaderViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_COMMON -> CardViewHolder.from(
                parent
            )
            ITEM_HEADER -> HeaderViewHolder.from(
                parent
            )
            else -> throw ClassCastException("unknown type of viewholder")
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardViewHolder -> {
                val item = list[position - 1]
                holder.imageView.setImageFromUrl(item.subscribers[0].backgroundUrl)
                holder.title.text = StringBuilder().append(item.tag).append("||").append(item.copywriter)
                holder.itemView.setOnClickListener {

                    mOnItemClickListener?.onItemClick(position)
                }
            }
            is HeaderViewHolder -> {}
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return ITEM_HEADER
        } else return ITEM_COMMON
    }
}

interface OnItemClickListener {
    fun onItemClick(position: Int)
}