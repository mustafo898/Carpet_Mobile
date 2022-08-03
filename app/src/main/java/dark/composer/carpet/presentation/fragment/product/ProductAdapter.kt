package dark.composer.carpet.presentation.fragment.product

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.data.retrofit.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.databinding.ItemProduct1Binding

class ProductAdapter(private var context: Context) : RecyclerView.Adapter<ProductAdapter.SaleViewHolder>() {
    private val listProduct = mutableListOf<ProductPaginationResponse>()

    inner class SaleViewHolder(val binding: ItemProduct1Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: ProductPaginationResponse) {
            binding.name.text = list.name
            if (list.imageUrlList.isNotEmpty()){
                Glide.with(context).load(list.imageUrlList[list.imageUrlList.size-1]).into(binding.image)
                Log.d("RRRRR", "bind: ${list.imageUrlList[list.imageUrlList.size-1]}")
            }
            binding.time.text =list.createdDate.substring(11,16)
            binding.date.text = list.createdDate.substring(0,10)
            binding.factoryName.text = list.factoryName

            Log.d("DDDDD", "bind: ${binding.time}")

            itemView.setOnClickListener {
                clickListener?.invoke(list.uuid)
            }
        }
    }

    private var clickListener: ((position: String) -> Unit)? = null

    fun setClickListener(f: (position: String) -> Unit) {
        clickListener = f
    }

    fun setProductListProduct(list: List<ProductPaginationResponse>) {
        this.listProduct.addAll(list)
        this.listProduct.shuffle()
        notifyDataSetChanged()
    }

    fun clear(){
        this.listProduct.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SaleViewHolder(
        ItemProduct1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) =
        holder.bind(listProduct[position])

    override fun getItemCount() = listProduct.size
}
