package dark.composer.carpet.presentation.fragment.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.widget.addTextChangedListener
import dark.composer.carpet.databinding.DialogUpdateBasketBinding

class UpdateBasket(content: Context) : AlertDialog(content) {
    var binding: DialogUpdateBasketBinding = DialogUpdateBasketBinding.inflate(layoutInflater)
    private var updateListener: ((amount: Int) -> Unit)? = null

    fun setOnUpdateListener(f: (amount: Int) -> Unit) {
        updateListener = f
    }

    fun setAmount(amount: Int) {
        binding.amount.setText(amount.toString())
    }

    init {
        setTitle("Add Student.")

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        binding.amountInput.isHelperTextEnabled = true
        binding.amount.addTextChangedListener {
            validAmount(it.toString())
        }

        binding.acceptFB.setOnClickListener {
            if (validAmount(binding.amount.text.toString())) {
                updateListener?.invoke(binding.amount.text.toString().toInt())
            } else {
                validAmount(binding.amount.text.toString())
            }
        }

        binding.cancelFB.setOnClickListener {
            dismiss()
        }

        setView(binding.root)
    }

    private fun validAmount(amount: String): Boolean {
        return if (amount.isNotEmpty() && amount.toInt() > 0) {
            binding.amountInput.isHelperTextEnabled = false
            true
        } else {
            binding.amountInput.isHelperTextEnabled = true
            binding.amountInput.helperText = "Can not be 0"
            false
        }
    }
}