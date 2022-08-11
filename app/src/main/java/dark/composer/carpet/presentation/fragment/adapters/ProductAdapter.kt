package dark.composer.carpet.presentation.fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.databinding.ItemProductNewBinding

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val listProduct = mutableListOf<ProductPaginationResponse>()

    private var clickListener: ((id:String) -> Unit)? = null

    fun setClickListener(f: (id:String) -> Unit) {
        clickListener = f
    }

    inner class ProductViewHolder(private val binding: ItemProductNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductPaginationResponse) {
            binding.factoryName.text = data.factoryName
            binding.productName.text = data.name


            Glide.with(binding.root).load(data.factoryAttachUrl.ifEmpty {
                binding.factoryImage.setImageResource(
                    R.drawable.ic_factory
                )
            }).into(binding.factoryImage)
            if (data.imageUrlList.isEmpty()){
                binding.image.setImageResource(
                    R.drawable.image
                )
            }else{
                Glide.with(binding.root).load(data.imageUrlList[0]).into(binding.image)
            }
            itemView.setOnClickListener {
                clickListener?.invoke(data.uuid)
            }
            binding.date.text = data.createdDate.substring(0,10)
            binding.time.text = data.createdDate.substring(11,16)
        }
    }

    fun setList(list:List<ProductPaginationResponse>){
        listProduct.clear()
        listProduct.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        ItemProductNewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(listProduct[position])

    override fun getItemCount() = listProduct.size
}