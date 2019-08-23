package com.rg.musiound.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.bean.SongList
import com.rg.musiound.db.CSList
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.setImageFromUrl
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/23
 */
class CSLAdapter(val ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_COMMON = 1
    private var mOnItemClickListener: OnItemClickListener? = null
    var list: MutableList<CSList> = mutableListOf()

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.find(R.id.iv_csl_song_list)
        val title: TextView = view.find(R.id.tv_csl_song_list_title)

        companion object {
            fun from(parent: ViewGroup): CardViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_activity_csl_common, parent, false)
                return CardViewHolder(view)
            }
        }
    }

//    class SearchSongListHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val iv: ImageView = view.find(R.id.iv_search_song_list)
//        val tv_title: TextView = view.find(R.id.tv_search_song_list_title)
//        val tv_artist: TextView = view.find(R.id.tv_search_song_list_artist)
//
//        companion object {
//            fun from(parent: ViewGroup): SearchSongListHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater.inflate(R.layout.recycle_item_fragment_search_song_list, parent, false)
//                return SearchSongListHolder(view)
//            }
//        }
//    }

//    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        companion object {
//            fun from(parent: ViewGroup): HeaderViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater.inflate(R.layout.recycle_item_activity_song_list_header, parent, false)
//                return HeaderViewHolder(view)
//            }
//        }
//    }
//
//    class LoadingMoreFootHolder(view: View) : RecyclerView.ViewHolder(view) {
//        companion object {
//            fun from(parent: ViewGroup): LoadingMoreFootHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val view =
//                    layoutInflater.inflate(R.layout.recycle_item_activity_mv_play_loading_more_footer, parent, false)
//                return LoadingMoreFootHolder(view)
//            }
//        }
//    }
//
//    class LoadEndFooter(view: View) : RecyclerView.ViewHolder(view) {
//        companion object {
//            fun from(parent: ViewGroup): LoadEndFooter {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater.inflate(R.layout.recycle_item_activity_mv_play_load_end_footer, parent, false)
//                return LoadEndFooter(view)
//            }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_COMMON -> CardViewHolder.from(
                parent
            )
            else -> throw ClassCastException("unknown type of viewholder")
        }
    }

    override fun getItemCount(): Int {
        return getCount()
    }

    fun getCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardViewHolder -> {
                Log.d("roger", "go to this" + list[position].toString())
                holder.imageView.setImageFromUrl(list[position].pic)
                holder.title.text = list[position].title
                holder.itemView.setOnClickListener {
                    mOnItemClickListener?.onItemClick(position)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_COMMON
    }
}