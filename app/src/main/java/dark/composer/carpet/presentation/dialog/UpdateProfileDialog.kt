package dark.composer.carpet.presentation.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import dark.composer.carpet.R
import dark.composer.carpet.databinding.DialogUpdateProfileBinding

class UpdateProfileDialog(content: Context, type: String, name:String, lastname:String) : AlertDialog(content) {
    var binding = DialogUpdateProfileBinding.inflate(layoutInflater)

    private var updateListener: ((name: String,surname: String,password: String) -> Unit)? = null

    fun setOnAddListener(f: (name: String,surname: String,password: String) -> Unit) {
        updateListener = f
    }

    init {
        setTitle("Update Profile")
        binding.name.setText(name)
        binding.lastName.setText(lastname)

        if (binding.name.length() <= 4  && binding.lastName.length() <= 4){
            if (binding.passwordDialog.length() <= 6){
                setButton(BUTTON_POSITIVE, "Ok") { p0, p1 ->
                    updateListener?.invoke(binding.name.text.toString(),binding.lastName.text.toString(),binding.passwordDialog.text.toString())
                    dismiss()
                }
            }
        }

        setButton(BUTTON_NEGATIVE, "No") { p0, p1 ->
            dismiss()
        }
    }
}