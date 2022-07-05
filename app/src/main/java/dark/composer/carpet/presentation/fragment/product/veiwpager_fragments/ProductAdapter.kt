package dark.composer.carpet.presentation.fragment.product.veiwpager_fragments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import dark.composer.carpet.data.retrofit.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.databinding.ItemProductBinding

class ProductAdapter(private var context: Context) : RecyclerView.Adapter<ProductAdapter.SaleViewHolder>() {
    private val listFactory = mutableListOf<ProductPaginationResponse>()

    inner class SaleViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: ProductPaginationResponse) {
            binding.name.text = list.name
            if (list.imageUrlList.isNotEmpty()){
                Glide.with(context).load(list.imageUrlList[0]).into(binding.image)
                Log.d("RRRRR", "bind: ${list.imageUrlList[0]}")
            }else{
                binding.image.setImageResource(R.drawable.ic_image_null)
            }
            binding.date.text = list.createdDate
            binding.rate.text = list.factoryName

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
        this.listFactory.addAll(list)
        notifyDataSetChanged()
    }

    fun clear(){
        this.listFactory.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SaleViewHolder(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) =
        holder.bind(listFactory[position])

    override fun getItemCount() = listFactory.size
}
