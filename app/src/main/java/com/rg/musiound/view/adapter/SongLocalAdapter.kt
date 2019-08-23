package com.rg.musiound.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.bean.Track
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.SongLocal
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/24
 */
class SongLocalAdapter(val ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val ITEM_COMMON = 1
    private var mOnItemClickListener: OnItemClickListener? = null
    var isLoadingMore: Boolean = true
    var list: MutableList<SongLocal> = mutableListOf()

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_COMMON -> CommonViewHolder.from(parent)

            else -> throw Exception("unknown type of viewholder")
        }

    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_COMMON
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommonViewHolder -> {
                holder.numberTextView.text = position.plus(1).toString()
                holder.title.text = list[position].name
                holder.itemView.setOnClickListener{
                    mOnItemClickListener?.onItemClick(position)
                }

                holder.description.text = list[position].singer
            }
        }
    }
    class CommonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numberTextView: TextView = view.find(R.id.tv_list_detail_number)
        val description: TextView = view.find(R.id.tv_list_detail_description)
        val title: TextView = view.find(R.id.tv_list_detail_title)
        val playIV: ImageView = view.find(R.id.iv_list_detail_play)
        val info: ImageView = view.find(R.id.iv_list_detail_info)

        companion object {
            fun from(parent: ViewGroup): CommonViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_activity_list_detail_common, parent, false)
                return CommonViewHolder(view)
            }
        }

    }
    fun getCount(): Int {
        return list.size
    }
}