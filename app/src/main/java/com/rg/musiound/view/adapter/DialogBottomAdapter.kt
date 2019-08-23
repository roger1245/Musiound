package com.rg.musiound.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.bean.Song
import com.rg.musiound.service.PlayManager
import com.rg.musiound.service.ruler.LIST_LOOP
import com.rg.musiound.util.OnItemClickListener
import org.jetbrains.anko.find
import java.lang.StringBuilder

/**
 * Create by roger
 * on 2019/8/20
 */
class DialogBottomAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_COMMON = 0
    private val ITEM_PLAYING = 1
    private var mOnItemClickListener: OnBottomClickListener? = null
    var list: List<Song> = PlayManager.instance.getSongs()

    fun setBottomClickListener(mOnItemClickListener: OnBottomClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_COMMON -> CommonViewHolder.from(parent)
            ITEM_PLAYING -> PlayingHolder.from(parent)
            else -> throw Exception("unknown type of viewholder")
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position] == PlayManager.instance.currentSong) {
            Log.d("roger", "is playing hoder")
            return ITEM_PLAYING
        } else {
            return ITEM_COMMON
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommonViewHolder -> {
                holder.title.text = list[position].name
                val ar = list[position].singer.map { it.name }
                val str = StringBuilder()
                for (x in ar.withIndex()) {
                    str.append(x.value)
                    if (x.index != ar.size - 1) {
                        str.append(",")
                    }
                }
//                holder.delete.setOnClickListener {
//                    mOnItemClickListener?.onDeleteClick(position)
//                }
                holder.artist.text = str.toString()
                holder.itemView.setOnClickListener{
                    mOnItemClickListener?.onItemClick(position)
                }

            }
            is PlayingHolder -> {
                holder.title.text = list[position].name
                val ar = list[position].singer.map { it.name }
                val str = StringBuilder()
                for (x in ar.withIndex()) {
                    str.append(x.value)
                    if (x.index != ar.size - 1) {
                        str.append(",")
                    }
                }
                holder.artist.text = str.toString()
            }
        }

    }
    class CommonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.find(R.id.tv_dialog_bottom_title)
        val artist: TextView = view.find(R.id.tv_dialog_bottom_artist)
//        val delete: ImageView = view.find(R.id.iv_dialog_bottom_delete)
        companion object {
            fun from(parent: ViewGroup): CommonViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_dialog_bottom_common, parent, false)
                return CommonViewHolder(view)
            }
        }
    }
    class PlayingHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.find(R.id.tv_dialog_bottom_title)
        val artist: TextView = view.find(R.id.tv_dialog_bottom_artist)
//        val delete: ImageView = view.find(R.id.iv_dialog_bottom_delete)
        companion object {
            fun from(parent: ViewGroup): PlayingHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_dialog_bottom_playing, parent, false)
                return PlayingHolder(view)
            }
        }
    }
}
interface OnBottomClickListener {
    fun onItemClick(position: Int)
    fun onDeleteClick(position: Int)
}