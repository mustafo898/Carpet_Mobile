package dark.composer.carpet.presentation.fragment.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.response.basket.BasketPaginationResponse
import dark.composer.carpet.data.remote.models.response.sale.SaleListByDate
import dark.composer.carpet.data.remote.models.response.sale.SalePaginationResponse
import dark.composer.carpet.databinding.ItemHistoryNewBinding

class HistoryAdapter1 : RecyclerView.Adapter<HistoryAdapter1.BasketViewHolder>() {
    private val basketList = mutableListOf<SaleListByDate>()

    private var moreClickListener: ((id: Int, type: String) -> Unit)? = null

    fun setMoreClickListener(f: (id: Int, type: String) -> Unit) {
        moreClickListener = f
    }

    inner class BasketViewHolder(private val binding: ItemHistoryNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SaleListByDate) {
            if (data.productDTO.urlImageList.isNullOrEmpty()) {
                binding.image.setImageResource(R.drawable.ic_shopping_basket)
            } else {
                Glide.with(binding.root).load(data.productDTO.urlImageList[0]).into(binding.image)
            }
            binding.productName.text = data.productDTO.name
            binding.price.text = data.amount.toString()
            binding.type.text = data.productDTO.type
            if (data.productDTO.type == "COUNTABLE") {
                binding.height.text = "Amount : ${data.amount}"
                binding.height.visibility = View.GONE
            } else {
                binding.height.text = "Height : ${data.height}"
                binding.height.visibility = View.VISIBLE
            }

            binding.visible.text = data.status.toString()

            if (data.status == "ACTIVE") {
                binding.visible.setTextColor(Color.GREEN)
            } else {
                binding.visible.setTextColor(Color.RED)
            }

            binding.remove.setOnClickListener {
                moreClickListener?.invoke(data.id, data.productDTO.type)
            }

//            binding.more.setOnClickListener {
//                moreClickListener?.invoke(it,data.id)
//            }
//
//            binding.buy.setOnClickListener {
//                buyClickListener?.invoke(data.product.uuid,layoutPosition)
//            }
        }
    }

    fun setList(list: List<SaleListByDate>) {
        basketList.clear()
        basketList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BasketViewHolder(
        ItemHistoryNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) =
        holder.bind(basketList[position])

    override fun getItemCount() = basketList.size
}