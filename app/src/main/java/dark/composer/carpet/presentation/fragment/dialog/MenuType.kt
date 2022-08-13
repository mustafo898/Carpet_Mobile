package dark.composer.carpet.presentation.fragment.dialog

import android.content.Context
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import dark.composer.carpet.R

class MenuType(context: Context, imageView: ImageView) : PopupMenu(context, imageView) {
    private var uncountableClickListener: ((id: Int) -> Unit)? = null

    fun setUncountableClickListener(f: (id: Int) -> Unit) {
        uncountableClickListener = f
    }

    private var countableClickListener: ((id: Int) -> Unit)? = null

    fun setCountableClickListener(f: (id: Int) -> Unit) {
        countableClickListener = f
    }

    init {
        menuInflater.inflate(R.menu.type_menu, menu)
        setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
            OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.uncountable_menu -> {
                        uncountableClickListener?.invoke(item.itemId)
                    }
                    R.id.countable_menu -> {
                        countableClickListener?.invoke(item.itemId)
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