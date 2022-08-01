package dark.composer.carpet.presentation.fragment.product

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.databinding.ItemProductDetailsBinding

class ProductDetailsAdapter : RecyclerView.Adapter<ProductDetailsAdapter.DetailsViewHolder>() {
    private val listProduct = mutableListOf<ProductPaginationResponse>()

    inner class DetailsViewHolder(val binding:ItemProductDetailsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(pagination:ProductPaginationResponse){
            binding.name.text = pagination.name
            if (pagination.imageUrlList.isNotEmpty()){
                Glide.with(binding.root.context).load(pagination.imageUrlList[pagination.imageUrlList.size-1]).into(binding.image)
                Log.d("RRRRR", "bind: ${pagination.imageUrlList[pagination.imageUrlList.size-1]}")
            }else{
                binding.image.setImageResource(R.drawable.ic_image_null)
            }
            binding.time.text =pagination.createdDate.substring(11,16)
            binding.date.text = pagination.createdDate.substring(0,10)
            binding.factoryName.text = pagination.factoryName

            itemView.setOnClickListener {
                clickListener?.invoke(pagination.uuid)
            }
        }
    }

    private var clickListener: ((position: String) -> Unit)? = null

    fun setClickListener(f: (position: String) -> Unit) {
        clickListener = f
    }

    fun setListProduct(list:List<ProductPaginationResponse>){
        listProduct.clear()
        listProduct.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DetailsViewHolder(
        ItemProductDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) = holder.bind(listProduct[position])

    override fun getItemCount() = listProduct.size
}