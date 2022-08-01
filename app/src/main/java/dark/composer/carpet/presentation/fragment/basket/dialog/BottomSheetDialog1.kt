package dark.composer.carpet.presentation.fragment.basket.dialog

import android.content.Context
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import dark.composer.carpet.databinding.SheetAddBasketBinding

class BottomSheetDialog1(context: Context) : BottomSheetDialog(context) {
    private val binding = SheetAddBasketBinding.inflate(layoutInflater)

    private var addListener: ((amount: Int, info: String) -> Unit)? = null

    fun setOnAddListener(f: (amount: Int, info: String) -> Unit) {
        addListener = f
    }

    var d = 1

    init {
        binding.add.setOnClickListener {
            d++
            binding.amount.text = d.toString()
        }
        binding.minus.setOnClickListener {
            if (d == 0){
                binding.amount.text = 0.toString()
                Toast.makeText(binding.root.context, "You can not add basket", Toast.LENGTH_SHORT).show()
            }else if (d > 0){
                d--
                binding.amount.text = d.toString()
            }
        }
        binding.info.addTextChangedListener {
            if (it.toString().length <= 8 && d != 0) {
                binding.infoInput.isHelperTextEnabled = true
                binding.infoInput.helperText = "You must add some Information"
            } else {
                binding.infoInput.isHelperTextEnabled = false
            }
        }

        binding.addBasket.setOnClickListener {
            if (binding.info.text.toString().length >= 8)
                addListener?.invoke(d, binding.info.text.toString())
        }
        setContentView(binding.root)
    }
}