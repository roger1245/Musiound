//package com.rg.musiound.view.dialog
//
//import android.app.Dialog
//import android.content.Context
//import android.content.DialogInterface
//import android.os.Bundle
//import android.view.Gravity
//import android.view.View
//import androidx.fragment.app.DialogFragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.rg.musiound.BaseApp
//import com.rg.musiound.R
//import com.rg.musiound.view.adapter.DialogBottomAdapter
//import kotlinx.android.synthetic.main.activity_mvplay.*
//import org.jetbrains.anko.find
//
///**
// * Create by roger
// * on 2019/8/20
// */
//class BottomSongListDialog : DialogFragment {
////    private var ctx: Context
////
////    constructor(ctx: Context) : super(ctx) {
////        this.ctx = ctx
////    }
////
////    constructor(ctx: Context, theme: Int) : super(ctx, theme) {
////        this.ctx = ctx
////    }
////
////    constructor(ctx: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener) : super(
////        ctx,
////        cancelable,
////        cancelListener
////    ) {
////        this.ctx = ctx
////    }
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: DialogBottomAdapter
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val view = View.inflate(ctx, R.layout.dialog_bottom_song_list, null)
//        setContentView(view)
//        recyclerView = view.find(R.id.rv_fragment_song_list)
//
//        val lp = view.layoutParams
//        lp.width = ctx.resources.displayMetrics.widthPixels
//        view.layoutParams = lp
//        window?.let {
//            it.setGravity(Gravity.BOTTOM)
//            it.setWindowAnimations(R.style.DialogBottom_Animation)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val view = View.inflate(ctx, R.layout.dialog_bottom_song_list, null)
//        setContentView(view)
//        recyclerView = view.find(R.id.rv_fragment_song_list)
//
//        val lp = view.layoutParams
//        lp.width = ctx.resources.displayMetrics.widthPixels
//        view.layoutParams = lp
//        window?.let {
//            it.setGravity(Gravity.BOTTOM)
//            it.setWindowAnimations(R.style.DialogBottom_Animation)
//        }
//        init()
//    }
//    private fun init() {

//    }
//
//
//}