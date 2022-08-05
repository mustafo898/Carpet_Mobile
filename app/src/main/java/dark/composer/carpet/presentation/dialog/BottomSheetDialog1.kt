package dark.composer.carpet.presentation.dialog

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import dark.composer.carpet.databinding.SheetAddBasketBinding

class BottomSheetDialog1(context: Context) : BottomSheetDialog(context) {
    private val binding = SheetAddBasketBinding.inflate(layoutInflater)

    private var addListener: ((amount: Int?, info: String?, height: Double?, price: Double?) -> Unit)? =
        null

    fun setOnAddListener(f: (amount: Int?, info: String?, height: Double?, price: Double?) -> Unit) {
        addListener = f
    }

    var d:Int = 1
    fun setAmountVisibility(visibility: Boolean) {
        if (visibility) {
            d = 1
            binding.linear.visibility = View.VISIBLE
        } else {
            d = 0
            binding.linear.visibility = View.GONE
        }
    }

    fun setPriceVisibility(visibility: Boolean) {
        if (visibility) {
            binding.priceInput.visibility = View.VISIBLE
        } else {
            binding.priceInput.visibility = View.GONE
        }
    }

    fun setHeightVisibility(visibility: Boolean) {
        if (visibility) {
            binding.heightInput.visibility = View.VISIBLE
        } else {
            binding.heightInput.visibility = View.GONE
        }
    }

    init {

        binding.add.setOnClickListener {
            d++
            binding.amount.text = d.toString()
        }

        binding.minus.setOnClickListener {
            if (d == 0) {
                binding.amount.text = 0.toString()
                Toast.makeText(binding.root.context, "You can not add basket", Toast.LENGTH_SHORT)
                    .show()
            } else if (d > 0) {
                d--
                binding.amount.text = d.toString()
            }
        }

        binding.addBasket.setOnClickListener {
            addListener?.invoke(
                if (d == 0) null else d,
                binding.info.text.toString().trim(),
                if (binding.height.text.toString().isEmpty()) null else 0.0,
                if (binding.price.text.toString().isEmpty()) null else 0.0,
            )
        }
        setContentView(binding.root)
    }
}