package dark.composer.carpet.presentation.fragment.product.add

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.databinding.ItemAddImageBinding

class AddImageAdapter(val context: Context) : RecyclerView.Adapter<AddImageAdapter.SaleViewHolder>() {
    private val listFactory = mutableListOf<String>()

    inner class SaleViewHolder(val binding: ItemAddImageBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(list:String){
            if(list.toString().isNotEmpty()){
                Glide.with(context).load(list).into(binding.image)
            }else{
                binding.image.setImageResource(R.drawable.image)
            }

            Log.d("DDDDD", "bind: ${binding.image}")

            itemView.setOnClickListener {

            }
        }
    }

    private var clickListener: ((position:Int) -> Unit)? = null

    fun setClickListener(f: (position:Int) -> Unit) {
        clickListener = f
    }

    fun setListImage(list : List<String>){
//        listFactory.clear()
        listFactory.addAll(list)
        notifyDataSetChanged()
//        notifyItemInserted(list.length-1)
    }

    fun setImage(list : String){
//        listFactory.clear()
        listFactory.add(list)
        notifyItemInserted(listFactory.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= SaleViewHolder(
        ItemAddImageBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) = holder.bind(listFactory[position])

    override fun getItemCount() = listFactory.size
}