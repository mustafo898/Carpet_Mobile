package dark.composer.carpet.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.widget.ImageButton
import android.widget.Toast
import dark.composer.carpet.R

class CustomDialog(context: Context) : Dialog(context) {

    init {
        setContentView(R.layout.dialog_update_factory)

        window?.setBackgroundDrawable(ColorDrawable(0))

        window?.setWindowAnimations(R.style.AnimationForDialog)

        val cancel: ImageButton = findViewById(R.id.cancelFB)
        val accept: ImageButton = findViewById(R.id.acceptFB)
        setCancelable(false)
        cancel.setOnClickListener {
            Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        accept.setOnClickListener {
            Toast.makeText(context, "data is changed", Toast.LENGTH_SHORT).show()
        }

        show()
    }
}