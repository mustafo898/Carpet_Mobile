package dark.composer.carpet.presentation.fragment.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.databinding.ItemCardFactoryNewBinding

class FactoryAdminAdapter : RecyclerView.Adapter<FactoryAdminAdapter.FactoryViewHolder>() {
    private val listFactory = mutableListOf<FactoryResponse>()

    private var clickListener: ((id:Int) -> Unit)? = null

    fun setClickListener(f: (id:Int) -> Unit) {
        clickListener = f
    }

    inner class FactoryViewHolder(private val binding: ItemCardFactoryNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FactoryResponse) {
            binding.factoryName.text = data.name
            if (data.photoUrl.isNullOrEmpty()){
                binding.image.setImageResource(R.drawable.image)
            }else{
                Glide.with(binding.root).load(data.photoUrl).into(binding.image)
            }
            itemView.setOnClickListener {
                clickListener?.invoke(data.id)
            }
            binding.card.setCardBackgroundColor(randomBgColor((0..5).random()))
        }
    }

    fun randomBgColor(position: Int): Int {
        val list = mutableListOf<Int>()
        list.add(Color.CYAN)
        list.add(Color.GREEN)
        list.add(Color.RED)
        list.add(Color.YELLOW)
        list.add(Color.BLUE)
        list.add(Color.MAGENTA)
        return list[position]
    }

    fun setList(list:List<FactoryResponse>){
        listFactory.clear()
        listFactory.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FactoryViewHolder(
            ItemCardFactoryNewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: FactoryViewHolder, position: Int) = holder.bind(listFactory[position])

    override fun getItemCount() = listFactory.size
}