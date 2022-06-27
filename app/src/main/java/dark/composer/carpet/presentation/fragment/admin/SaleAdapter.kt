package dark.composer.carpet.presentation.fragment.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.data.retrofit.models.response.factory.paginable.Content
import dark.composer.carpet.databinding.FragmentSigUpBinding
import dark.composer.carpet.databinding.ItemSaleBinding

class SaleAdapter(val context: Context) : RecyclerView.Adapter<SaleAdapter.SaleViewHolder>() {
    private val listFactory = mutableListOf<FactoryResponse>()

    inner class SaleViewHolder(val binding: ItemSaleBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(list:FactoryResponse){
            binding.name.text = list.name
            if(list.photoUrl!!.isNotEmpty()){
                Glide.with(context).load(list.photoUrl).into(binding.image)
            }else{
                binding.image.setImageResource(R.drawable.ic_image_null)
            }
            binding.date.text = list.createdDate.substring(0,9)
            binding.time.text = list.createdDate.substring(11,15)
        }
    }

    fun setListFactory(list: List<FactoryResponse>){
        this.listFactory.clear()
        this.listFactory.addAll(listFactory)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= SaleViewHolder(
        ItemSaleBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) = holder.bind(listFactory[position])

    override fun getItemCount() = listFactory.size
}