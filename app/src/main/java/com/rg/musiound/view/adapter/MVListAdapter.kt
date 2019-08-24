package com.rg.musiound.view.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.BaseApp
import com.rg.musiound.R
import com.rg.musiound.bean.MV
import com.rg.musiound.bean.SongList
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.setImageFromUrl
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import org.jetbrains.anko.textColor

/**
 * Create by roger
 * on 2019/8/18
 */
class MVListAdapter(val ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_HEADER = 0
    private val ITEM_COMMON = 1
    private val ITEM_LOADING_MORE_FOOTER = 2
    private val ITEM_LOADED_END_FOOTER = 3
    private var mOnItemClickListener: OnItemClickListener? = null
    var page: Int = 0
    var list: MutableList<MV> = mutableListOf()
    var isLoadingMore = true

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.find(R.id.iv_mv_cover)
        val title: TextView = view.find(R.id.tv_mv_title)
        val artist: TextView = view.find(R.id.tv_mv_artist)
        val number: TextView = view.find(R.id.tv_number)

        companion object {
            fun from(parent: ViewGroup): CardViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_fragment_mv_rank, parent, false)
                return CardViewHolder(view)
            }
        }
    }

    class LoadingMoreFootHolder(view: View) : RecyclerView.ViewHolder(view) {
        val loadImage: ImageView = view.find(R.id.iv_anim_image)
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
            ITEM_COMMON -> MVListAdapter.CardViewHolder.from(
                parent
            )
            ITEM_LOADED_END_FOOTER -> MVListAdapter.LoadEndFooter.from(
                parent
            )
            ITEM_LOADING_MORE_FOOTER -> MVListAdapter.LoadingMoreFootHolder.from(
                parent
            )
            else -> throw ClassCastException("unknown type of viewholder")
        }
    }

    override fun getItemCount(): Int {
        return getCount()
    }

    fun getCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardViewHolder -> {
                val item = list[position]
                holder.imageView.setImageFromUrl(item.cover)
                holder.title.text = list[position].name
                holder.artist.text = list[position].artistName
                holder.number.let {
                    it.text = (position + 1).toString()
                    if (position < 3) {
                        it.textColor = Color.parseColor(BaseApp.context.getString(R.color.fragment_discovery_red as Int))
                    } else {
                        it.textColor = Color.parseColor(BaseApp.context.getString(R.color.fragment_mv_rank_number_grey as Int))
                    }
                }

                holder.itemView.setOnClickListener{
                    mOnItemClickListener?.onItemClick(position)
                }
            }
            is LoadingMoreFootHolder -> {
                (holder.loadImage.drawable as AnimationDrawable).start()
            }
            is LoadEndFooter -> {}
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == getCount() - 1) {
            if (isLoadingMore) {
                return ITEM_LOADING_MORE_FOOTER
            } else {
                return ITEM_LOADED_END_FOOTER
            }
        }  else {
            return ITEM_COMMON
        }
    }
}