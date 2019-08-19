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
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.setImageFromUrl
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/16
 */
class SongListAdapter(val ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_HEADER = 0
    private val ITEM_COMMON = 1
    private val ITEM_LOADING_MORE_FOOTER = 2
    private val ITEM_LOADED_END_FOOTER = 3
    private var mOnItemClickListener: OnItemClickListener? = null
    var page: Int = 0
    var list: MutableList<SongList> = mutableListOf()
    var isLoadingMore = true

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.find(R.id.iv_recycler_item_song_list)
        val title: TextView = view.find(R.id.tv_recycler_item_song_list)

        companion object {
            fun from(parent: ViewGroup): CardViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_activity_song_list_common, parent, false)
                return CardViewHolder(view)
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_activity_song_list_header, parent, false)
                return HeaderViewHolder(view)
            }
        }
    }
    class LoadingMoreFootHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): LoadingMoreFootHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view =
                    layoutInflater.inflate(R.layout.recycle_item_activity_mv_play_loading_more_footer, parent, false)
                return LoadingMoreFootHolder(view)
            }
        }
    }

    class LoadEndFooter(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): LoadEndFooter {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_activity_mv_play_load_end_footer, parent, false)
                return LoadEndFooter(view)
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
            ITEM_LOADED_END_FOOTER -> LoadEndFooter.from(
                parent
            )
            ITEM_LOADING_MORE_FOOTER -> LoadingMoreFootHolder.from(
                parent
            )
            else -> throw ClassCastException("unknown type of viewholder")
        }
    }

    override fun getItemCount(): Int {
        return getCount()
    }
    fun getCount() : Int {
        return list.size + 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardViewHolder -> {
                val item = list[position - 1]
                holder.imageView.setImageFromUrl(item.coverImgUrl)
                if (item.tags.isNotEmpty()) {
                    holder.title.text = StringBuilder().append(item.tags[0]).append("||").append(item.name)
                } else if (item.tag != null) {
                    //此处由于为反射，所以tag可能为null，编译器无法检查
                    holder.title.text = StringBuilder().append(item.tag).append("||").append(item.name)
                }
                holder.itemView.setOnClickListener {

                    mOnItemClickListener?.onItemClick(position)
                }
            }
            is HeaderViewHolder -> {}
            is LoadEndFooter -> {}
            is LoadingMoreFootHolder -> {}
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == getCount() - 1) {
            if (isLoadingMore) {
                return ITEM_LOADING_MORE_FOOTER
            } else {
                return ITEM_LOADED_END_FOOTER
            }
        } else if (position == 0) {
            return ITEM_HEADER
        } else {
            return ITEM_COMMON
        }
    }
}
