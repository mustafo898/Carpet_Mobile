package dark.composer.carpet.presentation.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import dark.composer.carpet.R
import dark.composer.carpet.databinding.DialogUpdateFactoryBinding

class AddDialog(content: Context) : AlertDialog(content) {
    var binding = DialogUpdateFactoryBinding.inflate(layoutInflater)

    private var addListener: ((name: String, status: String, visible: Boolean) -> Unit)? = null

    fun setOnAddListener(f: (name: String, status: String, visible: Boolean) -> Unit) {
        addListener = f
    }

    private var visible = false
    private var status = false

    fun setVisible(visible: Boolean) {
        this.visible = visible
        if (this.visible) {
            binding.visibleImage.setImageResource(R.drawable.ic_checked_box)
        } else {
            binding.visibleImage.setImageResource(R.drawable.ic_check_box)
        }
    }

    fun setStatus(status: Boolean) {
        this.status = status
        if (this.status) {
            binding.statusImage.setImageResource(R.drawable.ic_checked_box)
        } else {
            binding.statusImage.setImageResource(R.drawable.ic_check_box)
        }
    }

    fun setTitle(title:String){
        binding.title.text = title
    }

    init {
        window?.setBackgroundDrawable(ColorDrawable(0))
        window?.setWindowAnimations(R.style.AnimationForDialog)
        var statusTxt = ""

        binding.visibleImage.setOnClickListener {
            if (visible) {
                visible = false
                binding.visibleImage.setImageResource(R.drawable.ic_checked_box)
            } else {
                visible = true
                binding.visibleImage.setImageResource(R.drawable.ic_check_box)
            }
        }
        binding.statusImage.setOnClickListener {
            if (status) {
                status = false
                binding.statusImage.setImageResource(R.drawable.ic_checked_box)
                statusTxt = "ACTIVE"
            } else {
                status = true
                binding.statusImage.setImageResource(R.drawable.ic_check_box)
                statusTxt = "BLOCKED"
            }
        }

        binding.acceptFB.setOnClickListener {
            addListener?.invoke(binding.name.text.toString(), statusTxt, visible)
            dismiss()
        }

        binding.cancelFB.setOnClickListener {
            dismiss()
        }
        setView(binding.root)
    }
}