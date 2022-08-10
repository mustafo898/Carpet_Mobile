package dark.composer.carpet.presentation.fragment.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.data.remote.models.response.basket.BasketPaginationResponse
import dark.composer.carpet.databinding.ItemBasketBinding

class BasketAdapter : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    private val listPagination = mutableListOf<BasketPaginationResponse>()

    inner class BasketViewHolder(val binding: ItemBasketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(basketPaginationResponse: BasketPaginationResponse) {
            if (basketPaginationResponse.product.imageUrlList.isNotEmpty()) {
                Glide.with(binding.root).load(basketPaginationResponse.product.imageUrlList[0])
                    .into(binding.image)
            }
            binding.date.text = "${basketPaginationResponse.createdDate.substring(0, 10)} ${basketPaginationResponse.createdDate.toString().substring(11, 16)}"
            binding.productName.text = basketPaginationResponse.product.name

            binding.edit.setOnClickListener {
                editClickListener?.invoke(basketPaginationResponse.id)
            }
            binding.delete.setOnClickListener {
                deleteClickListener?.invoke(basketPaginationResponse.id)
            }
        }
    }

    private var editClickListener: ((id:Int) -> Unit)? = null

    fun setEditClickListener(f: (id:Int) -> Unit) {
        editClickListener = f
    }

    private var deleteClickListener: ((id:Int) -> Unit)? = null

    fun setDeleteClickListener(f: (id:Int) -> Unit) {
        deleteClickListener = f
    }

    fun setList(list: List<BasketPaginationResponse>){
        listPagination.clear()
        listPagination.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BasketViewHolder(
        ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) =
        holder.bind(listPagination[position])

    override fun getItemCount() = listPagination.size
}