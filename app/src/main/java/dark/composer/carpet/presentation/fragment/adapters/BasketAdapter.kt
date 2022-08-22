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

    private var addClickListener: ((id: Int,amount:Int) -> Unit)? = null

    fun setAddClickListener(f: (id: Int,amount:Int) -> Unit) {
        addClickListener = f
    }

    private var deleteClickListener: ((id: Int) -> Unit)? = null

    fun setDeleteClickListener(f: (id: Int) -> Unit) {
        deleteClickListener = f
    }

    private var removeClickListener: ((id: Int,amount:Int) -> Unit)? = null

    fun setRemoveClickListener(f: (id: Int,amount:Int) -> Unit) {
        removeClickListener = f
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
            binding.price.text = data.product.price.toString()

            if (data.type == "COUNTABLE"){
                binding.linear.visibility = View.VISIBLE
                binding.heightLinear.visibility = View.GONE
            }else{
                binding.linear.visibility = View.GONE
                binding.heightLinear.visibility = View.VISIBLE
            }
            var d = 1
            binding.amount.text = d.toString()
            binding.add.setOnClickListener {
                addClickListener?.invoke(data.id,++d)
                binding.amount.text = d.toString()
            }

            binding.delete.setOnClickListener {
                deleteClickListener?.invoke(data.id)
            }

            binding.remove.setOnClickListener {
                if (d != 1) {
                    removeClickListener?.invoke(data.id,--d)
                    binding.amount.text = d.toString()
                }
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