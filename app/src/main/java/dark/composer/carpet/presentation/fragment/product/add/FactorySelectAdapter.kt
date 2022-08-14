package dark.composer.carpet.presentation.fragment.product.add

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.databinding.ItemSelectFactoryBinding

class FactorySelectAdapter(val context: Context) :
    RecyclerView.Adapter<FactorySelectAdapter.SaleViewHolder>() {
    private val listFactory = mutableListOf<FactoryResponse>()
    private var itemSelect = -1

    inner class SaleViewHolder(val binding: ItemSelectFactoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: FactoryResponse) {
            binding.name.text = list.name

            if (list.photoUrl?.isNotEmpty() == true) {
                Glide.with(context).load(list.photoUrl).into(binding.image)
            } else {
                binding.image.setImageResource(R.drawable.image)
            }

            Log.d("DDDDD", "bind: ${binding.image}")

            if (itemSelect == layoutPosition) {
                Toast.makeText(binding.root.context, "$itemSelect", Toast.LENGTH_SHORT).show()
                itemView.setBackgroundColor(binding.root.resources.getColor(R.color.demo_dark_transparent))
            } else {
                itemView.setBackgroundColor(binding.root.resources.getColor(R.color.white))
            }

            itemView.setOnClickListener {
                clickListener?.invoke(list.id)
                itemSelect = layoutPosition
                notifyDataSetChanged();
            }
        }
    }

    private var clickListener: ((position: Int) -> Unit)? = null

    fun setClickListener(f: (position: Int) -> Unit) {
        clickListener = f
    }

    fun setListFactory(list: List<FactoryResponse>) {
        listFactory.clear()
        listFactory.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SaleViewHolder(
        ItemSelectFactoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) =
        holder.bind(listFactory[position])

    override fun getItemCount() = listFactory.size
}