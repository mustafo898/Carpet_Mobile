package dark.composer.carpet.presentation.fragment.profile.list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import dark.composer.carpet.data.retrofit.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import dark.composer.carpet.databinding.ItemListCustomersBinding
import dark.composer.carpet.databinding.ItemProductBinding

class ListAdapter(private var context: Context) : RecyclerView.Adapter<ListAdapter.SaleViewHolder>() {
    private val listFactory = mutableListOf<ProfileResponse>()

    inner class SaleViewHolder(val binding: ItemListCustomersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: ProfileResponse) {
            binding.name.text = list.name
            if (!list.url.isNullOrEmpty()){
                Glide.with(context).load(list.url).into(binding.image)
                Log.d("RRRRR", "bind: ${list.url}")
            }else{
                binding.image.setImageResource(R.drawable.ic_image_null)
            }
            binding.name.text = "${list.name} ${list.surname}"
            binding.phoneNumber.text = list.phoneNumber

            itemView.setOnClickListener {
                clickListener?.invoke(list.id)
            }
        }
    }

    private var clickListener: ((position: Int) -> Unit)? = null

    fun setClickListener(f: (position: Int) -> Unit) {
        clickListener = f
    }

    fun setProductListProduct(list: List<ProfileResponse>) {
        this.listFactory.clear()
        this.listFactory.addAll(list)
        notifyDataSetChanged()
    }

    fun clear(){
        this.listFactory.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SaleViewHolder(
        ItemListCustomersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) =
        holder.bind(listFactory[position])

    override fun getItemCount() = listFactory.size
}
