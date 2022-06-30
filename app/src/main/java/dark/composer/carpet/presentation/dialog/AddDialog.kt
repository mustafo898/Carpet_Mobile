package dark.composer.carpet.presentation.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import dark.composer.carpet.R
import dark.composer.carpet.databinding.DialogUpdateFactoryBinding

class AddDialog (content: Context) : AlertDialog(content) {
    var binding = DialogUpdateFactoryBinding.inflate(layoutInflater)

    private var addListener: ((name: String, status: String, visible: Boolean) -> Unit)? = null

    fun setOnAddListener(f: (name: String, status: String, visible:Boolean) -> Unit) {
        addListener = f
    }

    private var visible = false
    private var status = false

    fun setVisible(visible:Boolean){
        this.visible = visible
    }

    fun setStatus(status:Boolean){
        this.status = status
    }

    init {
        setTitle("Add Factory!")
        var statusTxt = ""
        setButton(BUTTON_POSITIVE,"Ok"){p0,p1->
            binding.statusImage.setOnClickListener {
                statusTxt = if (status){
                    binding.statusImage.setImageResource(R.drawable.ic_checked_box)
                    "ACTIVE"
                }else{
                    binding.statusImage.setImageResource(R.drawable.ic_check_box)
                    "BLOCKED"
                }
            }

            binding.visibleImage.setOnClickListener {
                if (visible){
                    binding.visibleImage.setImageResource(R.drawable.ic_checked_box)
                }else{
                    binding.visibleImage.setImageResource(R.drawable.ic_check_box)
                }
            }
            addListener?.invoke(binding.name.text.toString(), statusTxt, visible)
            dismiss()
        }
        setButton(BUTTON_NEGATIVE,"No"){ p0, p1->
            dismiss()
        }
    }
}