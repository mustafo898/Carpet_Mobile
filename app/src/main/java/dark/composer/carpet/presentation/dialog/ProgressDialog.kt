package dark.composer.carpet.presentation.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import dark.composer.carpet.R
import dark.composer.carpet.databinding.ProgressDialogBinding

class ProgressDialog(context:Context):AlertDialog(context) {
    private val binding = ProgressDialogBinding.inflate(layoutInflater)

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setWindowAnimations(R.style.AnimationForDialog)

        setView(binding.root)
        setCancelable(false)
    }
}