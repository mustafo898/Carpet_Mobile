package dark.composer.carpet.presentation.fragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.response.basket.BasketPaginationResponse
import dark.composer.carpet.databinding.ItemBasketNewBinding

class BasketAdapter : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    private val basketList = mutableListOf<BasketPaginationResponse>()

    private var buyClickListener: ((id: String, amount:Int) -> Unit)? = null

    fun setBuyClickListener(f: (id: String, amount:Int) -> Unit) {
        buyClickListener = f
    }

    private var moreClickListener: ((view:View,id:Int) -> Unit)? = null

    fun setMoreClickListener(f: (view:View,id:Int) -> Unit) {
        moreClickListener = f
    }

    inner class BasketViewHolder(private val binding: ItemBasketNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BasketPaginationResponse) {
            if (data.product.imageUrlList.isNullOrEmpty()) {
                binding.image.setImageResource(R.drawable.ic_shopping_basket)
            }else{
                Glide.with(binding.root).load(data.product.imageUrlList).into(binding.image)
            }
            binding.productName.text = data.product.name
            binding.price.text = data.amount.toString()

            if (data.type == "COUNTABLE"){
                binding.heightLinear.visibility = View.GONE
            }else{
                binding.heightLinear.visibility = View.VISIBLE
            }

            binding.more.setOnClickListener {
                moreClickListener?.invoke(it,data.id)
            }

            binding.buy.setOnClickListener {
                buyClickListener?.invoke(data.product.uuid,layoutPosition)
            }
        }
    }

    fun setList(list: List<BasketPaginationResponse>) {
        basketList.clear()
        basketList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BasketViewHolder(
        ItemBasketNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) =
        holder.bind(basketList[position])

    override fun getItemCount() = basketList.size
}