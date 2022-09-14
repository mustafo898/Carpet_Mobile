package dark.composer.carpet.presentation.fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dark.composer.carpet.databinding.ItemFilterNewBinding

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>(){
    private val list = mutableListOf<String>()

    private var clickListener: ((id:Int) -> Unit)? = null

    fun setClickListener(f: (id:Int) -> Unit) {
        clickListener = f
    }

    inner class FilterViewHolder(private val binding: ItemFilterNewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(s:String){
            binding.btn.text = s
            binding.btn.setOnClickListener {
                clickListener?.invoke(layoutPosition)
            }
            itemView.setOnClickListener {
                deleteItem(layoutPosition)
            }
        }
    }

    fun setList(list: List<String>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int){
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilterViewHolder(
        ItemFilterNewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size
}