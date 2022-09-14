package dark.composer.carpet.presentation.fragment.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import dark.composer.carpet.data.remote.models.request.sale.SaleRequest
import dark.composer.carpet.data.remote.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.remote.models.response.basket.BasketPaginationResponse
import dark.composer.carpet.databinding.DialogBuyBasketBinding
import dark.composer.carpet.databinding.DialogUpdateBasketBinding

class SaleDialog(content: Context) : AlertDialog(content) {
    var binding: DialogBuyBasketBinding = DialogBuyBasketBinding.inflate(layoutInflater)
    private var clickListener: ((sale:SaleRequest) -> Unit)? = null

    fun setOnClickListener(f: (sale:SaleRequest) -> Unit) {
        clickListener = f
    }

    private lateinit var s : SaleRequest

    fun setSaleFields(sale: BasketCreateResponse) {
        binding.amount.text = sale.amount.toString()
        binding.type.text = sale.type
        if (sale.type == "COUNTABLE"){
            binding.lin1.visibility = View.GONE
            binding.lin2.visibility = View.VISIBLE
        }else{
            binding.lin1.visibility = View.VISIBLE
            binding.lin2.visibility = View.GONE
        }
//        if (sale.product.height != null){
//            b inding.height.text = sale.product.height.toString()
//        }
//        binding.price.text = sale.product.price.toString()
//        binding.name.text = sale.product.name
        Log.d("OOOOO", "setSaleFields: ${sale.type}")
        s = SaleRequest(type = sale.type, productId = sale.product.uuid, price = sale.product.price, amount = sale.amount, height = sale.product.height)
    }

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        s = SaleRequest()
        binding.acceptFB.setOnClickListener {
            clickListener?.invoke(s)
        }

        binding.cancelFB.setOnClickListener {
            dismiss()
        }

        setView(binding.root)
    }
}