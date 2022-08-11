package dark.composer.carpet.presentation.fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.databinding.ItemFactoryGridNewBinding

class FactoryAdapter : RecyclerView.Adapter<FactoryAdapter.FactoryViewHolder>(){
    private val factoryList = mutableListOf<FactoryResponse>()

    private var clickListener: ((id:Int) -> Unit)? = null

    fun setClickListener(f: (id:Int) -> Unit) {
        clickListener = f
    }

    inner class FactoryViewHolder(private val binding: ItemFactoryGridNewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:FactoryResponse){
            if (data.photoUrl.isNullOrEmpty()){
                binding.image.setImageResource(R.drawable.image)
            }
            Glide.with(binding.root).load(data.photoUrl).into(binding.image)
            binding.factoryName.text = data.name
            binding.date.text = data.createdDate.substring(0,10)
            binding.time.text = data.createdDate.substring(11,16)
            itemView.setOnClickListener {
                clickListener?.invoke(data.id)
            }
        }
    }

    fun setList(list:List<FactoryResponse>){
        factoryList.clear()
        factoryList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FactoryViewHolder (
        ItemFactoryGridNewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: FactoryViewHolder, position: Int) = holder.bind(factoryList[position])

    override fun getItemCount() = factoryList.size
}