package dark.composer.carpet.presentation.fragment.dialog

import android.content.Context
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import dark.composer.carpet.R

class MenuProfile (context: Context, imageView: ImageView) : PopupMenu(context, imageView) {
    private var editClickListener: ((id:Int) -> Unit)? = null

    fun setEditClickListener(f: (id:Int) -> Unit) {
        editClickListener = f
    }

    private var deleteClickListener: ((id:Int) -> Unit)? = null

    fun setDeleteClickListener(f: (id:Int) -> Unit) {
        deleteClickListener = f
    }

    private var changeClickListener: ((id:Int) -> Unit)? = null

    fun setChangeClickListener(f: (id:Int) -> Unit) {
        changeClickListener = f
    }

    init {
        menuInflater.inflate(R.menu.pop_up_profile,menu)
        setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
            OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.logout_profile -> {
                        deleteClickListener?.invoke(item.itemId)
                    }
                    R.id.change_profile -> {
                        changeClickListener?.invoke(item.itemId)
                    }
                    R.id.edit_profile -> {
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