package dark.composer.carpet.presentation.fragment.dialog

import android.content.Context
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import dark.composer.carpet.R

class PopupMenu(context: Context, imageView: ImageView) : PopupMenu(context, imageView) {
    private var editClickListener: ((id:Int) -> Unit)? = null

    fun setEditClickListener(f: (id:Int) -> Unit) {
        editClickListener = f
    }

    private var deleteClickListener: ((id:Int) -> Unit)? = null

    fun setDeleteClickListener(f: (id:Int) -> Unit) {
        deleteClickListener = f
    }

    private var shareClickListener: ((id:Int) -> Unit)? = null

    fun setShareClickListener(f: (id:Int) -> Unit) {
        shareClickListener = f
    }

    init {
        menuInflater.inflate(R.menu.pop_up_menu,menu)
        setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener, OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.delete -> {
                        deleteClickListener?.invoke(item.itemId)
                    }
                    R.id.share -> {
                        shareClickListener?.invoke(item.itemId)
                    }
                    R.id.edit -> {
                        editClickListener?.invoke(item.itemId)
                    }
                    else -> {
                        return false
                    }
                }
                return true
            }
        })
    }
}