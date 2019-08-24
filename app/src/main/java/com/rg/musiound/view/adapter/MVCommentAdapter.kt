package com.rg.musiound.view.adapter

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.bean.MVComment
import com.rg.musiound.bean.MVDetail
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.setImageFromUrl
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/18
 */
class MVCommentAdapter(val ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var isExpanded: Boolean = false
    val pageSize: Int = 30
    var page = 0
    var isLoadingMore = true
    private val ITEM_HEADER = 0
    private val ITEM_COMMON = 1
    private val ITEM_LOADING_MORE_FOOTER = 2
    private val ITEM_LOAD_END_FOOTER = 3
    private var mOnItemClickListener: OnItemClickListener? = null
    var detail: MVDetail? = null
    var comments: MutableList<MVComment> = mutableListOf()

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    class CommonHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.find(R.id.iv_avatar)
        val userId: TextView = view.find(R.id.tv_user_id)
        val time: TextView = view.find(R.id.tv_time)
        val comment: TextView = view.find(R.id.tv_comment)

        companion object {
            fun from(parent: ViewGroup): CommonHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_activity_mv_play_comment_common, parent, false)
                return CommonHolder(view)
            }
        }
    }

    class LoadingMoreFootHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.find(R.id.iv_anim_image)
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

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.find(R.id.tv_mv_title)
        val viewNumber: TextView = view.find(R.id.tv_mv_view_number)
        val time: TextView = view.find(R.id.tv_publish_time)
        val detail: TextView = view.find(R.id.tv_mv_detail)
        val avatar: ImageView = view.find(R.id.iv_mv_artist_avatar)
        val artist: TextView = view.find(R.id.tv_mv_artist_name)
        val arrow: ImageView = view.find(R.id.iv_mv_arrow)
        val expandable: LinearLayout = view.find(R.id.ll_mv_expandable)
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_activity_mv_play_detail, parent, false)
                return HeaderViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_COMMON -> CommonHolder.from(
                parent
            )
            ITEM_LOADING_MORE_FOOTER -> LoadingMoreFootHolder.from(parent)
            ITEM_LOAD_END_FOOTER -> LoadEndFooter.from(parent)
            ITEM_HEADER -> HeaderViewHolder.from(
                parent
            )
            else -> throw ClassCastException("unknown type of viewholder")
        }
    }

    override fun getItemCount(): Int {
        return getCount()
    }

    fun getCount(): Int {
        return comments.size + if (detail == null) 1 else 0 + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommonHolder -> {
                comments.let {
                    val item = it[position]
                    holder.avatar.setImageFromUrl(item.user.avatarUrl)
                    holder.comment.text = item.content
                    holder.userId.text = item.user.nickname
                }
                holder.itemView.setOnClickListener {
                    mOnItemClickListener?.onItemClick(position)
                }
            }
            is HeaderViewHolder -> {
                detail?.let {
                    holder.title.text = it.name
                    holder.artist.text = it.artistName
                    holder.avatar.setImageFromUrl(it.cover)
                    holder.detail.text = it.desc
                    holder.time.text = "${it.publishTime} 发布"
                    holder.viewNumber.text = "${it.playCount}次观看"
                    holder.expandable.visibility = if (isExpanded) View.VISIBLE else View.GONE
                    if (isExpanded) {
                        holder.arrow.setImageResource(R.drawable.activity_mv_play_arrow_up)
                    } else {
                        holder.arrow.setImageResource(R.drawable.activity_mv_play_arrow_down)
                    }
                    holder.arrow.setOnClickListener {
                        isExpanded = !isExpanded
                        notifyItemChanged(position)
                    }
                }
            }
            is LoadingMoreFootHolder -> {
                (holder.image.drawable as AnimationDrawable).start()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == getCount() - 1) {
            if (isLoadingMore) {
                return ITEM_LOADING_MORE_FOOTER
            } else {
                return ITEM_LOAD_END_FOOTER
            }
        } else if (position == 0 && detail != null) {
            return ITEM_HEADER
        } else {
            return ITEM_COMMON
        }

    }
}