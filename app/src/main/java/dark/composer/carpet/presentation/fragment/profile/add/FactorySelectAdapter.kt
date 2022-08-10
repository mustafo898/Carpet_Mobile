package dark.composer.carpet.presentation.fragment.profile.add

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.local.SingleSelectFactory
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.databinding.ItemFactoryBinding
import dark.composer.carpet.databinding.ItemSelectFactoryBinding
import okhttp3.internal.ignoreIoExceptions

class FactorySelectAdapter(val context: Context) :
    RecyclerView.Adapter<FactorySelectAdapter.SaleViewHolder>() {
    private val listFactory = mutableListOf<FactoryResponse>()
    private var itemSelection = -1

    inner class SaleViewHolder(val binding: ItemSelectFactoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: FactoryResponse) {
            binding.name.text = list.name

            if (list.photoUrl?.isNotEmpty() == true) {
                Glide.with(context).load(list.photoUrl).into(binding.image)
            } else {
                binding.image.setImageResource(R.drawable.ic_image_null)
            }

            Log.d("DDDDD", "bind: ${binding.image}")

            if (itemSelection == layoutPosition) {
                itemView.setBackgroundColor(binding.root.resources.getColor(R.color.demo_dark_transparent))
            } else {
                itemView.setBackgroundColor(binding.root.resources.getColor(R.color.white))
            }

            itemView.setOnClickListener {
                clickListener?.invoke(list.id,list.name)
                itemSelection = layoutPosition
                notifyDataSetChanged();
            }
        }
    }

    private var clickListener: ((position: Int,name:String) -> Unit)? = null

    fun setClickListener(f: (position: Int, name:String) -> Unit) {
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