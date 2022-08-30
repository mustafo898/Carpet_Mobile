package dark.composer.carpet.presentation.fragment.dialog

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import dark.composer.carpet.R

class MenuBasket (context: Context, imageView: View) : PopupMenu(context, imageView) {
    private var updateClickListener: ((id:Int) -> Unit)? = null

    fun setUpdateClickListener(f: (id:Int) -> Unit) {
        updateClickListener = f
    }

    private var deleteClickListener: ((id:Int) -> Unit)? = null

    fun setDeleteClickListener(f: (id:Int) -> Unit) {
        deleteClickListener = f
    }

    init {
        menuInflater.inflate(R.menu.menu_basket,menu)
        setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
            OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.edit_basket_menu -> {
                        updateClickListener?.invoke(item.itemId)
                    }
                    R.id.delete_basket_menu -> {
                        deleteClickListener?.invoke(item.itemId)
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