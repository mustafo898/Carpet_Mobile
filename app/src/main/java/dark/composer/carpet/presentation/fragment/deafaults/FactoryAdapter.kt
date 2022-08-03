package dark.composer.carpet.presentation.fragment.deafaults

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.databinding.ItemFactoryBinding

class FactoryAdapter(private var context: Context) :
    RecyclerView.Adapter<FactoryAdapter.SaleViewHolder>() {
    private val listFactory = mutableListOf<FactoryResponse>()

    inner class SaleViewHolder(val binding: ItemFactoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: FactoryResponse) {
            binding.name.text = list.name
            if (list.photoUrl?.isNotEmpty() == true) {
                Glide.with(context).load(list.photoUrl).into(binding.image)
            } else {
                binding.image.setImageResource(R.drawable.ic_image_null)
            }
            binding.date.text = list.createdDate.substring(0, 10)
            binding.time.text = list.createdDate.substring(11, 16)
            Log.d("DDDDD", "bind: ${binding.time}")

            itemView.setOnClickListener {
                clickListener?.invoke(list.id)
            }
        }
    }

    private var clickListener: ((position: Int) -> Unit)? = null

    fun setClickListener(f: (position: Int) -> Unit) {
        clickListener = f
    }

    fun setListFactory(list: List<FactoryResponse>) {
        this.listFactory.clear()
        this.listFactory.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SaleViewHolder(
        ItemFactoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) =
        holder.bind(listFactory[position])

    override fun getItemCount() = listFactory.size
}
