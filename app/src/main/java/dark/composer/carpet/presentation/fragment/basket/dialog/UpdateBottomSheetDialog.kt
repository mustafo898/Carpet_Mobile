package dark.composer.carpet.presentation.fragment.basket.dialog

import android.content.Context
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import dark.composer.carpet.databinding.SheetAddBasketBinding
import dark.composer.carpet.databinding.SheetUpdateBasketBinding

class UpdateBottomSheetDialog(context: Context) : BottomSheetDialog(context) {
    private val binding = SheetUpdateBasketBinding.inflate(layoutInflater)

    private var updateListener: ((amount: Int, price: Double, height: Double) -> Unit)? = null

    fun setOnUpdateListener(f: (amount: Int, price: Double, height: Double) -> Unit) {
        updateListener = f
    }

    var amount = 1
    var price = 1.0
    var height = 1.0

    //    fun setAmount1(s:Int){
//        amount = s
//    }
//
//    fun setPrice1(s:Double){
//
//    }
//
//    fun setHeight1(){
//
//    }
//
    init {
        binding.add.setOnClickListener {
            amount++
            binding.amount.text = amount.toString()
        }
        binding.minus.setOnClickListener {
            if (amount == 0) {
                binding.amount.text = 0.toString()
                Toast.makeText(binding.root.context, "You can not add basket", Toast.LENGTH_SHORT)
                    .show()
            } else if (amount > 0) {
                amount--
                binding.amount.text = amount.toString()
            }
        }

        binding.price.setText(price.toString())
        binding.amount.text = amount.toString()
        binding.height.setText(height.toString())

        binding.updateBasket.setOnClickListener {
//            if (binding.price.text.toString().length >= )
            updateListener?.invoke(amount,
                if (binding.price.text.toString().isNotEmpty()) binding.price.text.toString()
                    .toDouble() else 0.0,
                if (binding.height.text.toString().isNotEmpty()) binding.height.text.toString()
                    .toDouble() else 0.0
            )
        }
        setContentView(binding.root)
    }
}