package dark.composer.carpet.presentation.fragment.deafaults

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dark.composer.carpet.data.dto.CategoryModel
import dark.composer.carpet.databinding.ItemSaleBinding

class PopularAdapter(private var context: Context) :
    RecyclerView.Adapter<PopularAdapter.CategoryViewHolder>() {
    private val categoryList = mutableListOf<CategoryModel>()

    inner class CategoryViewHolder(private var binding: ItemSaleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CategoryModel) {
            binding.name.text = data.description
//            Glide.with(context).load(data.image)
//                .override(100, 100)
//                .into(binding.image)
            binding.image.setImageResource(data.image)
            binding.time.text = data.rate
        }
    }

    fun set(list: List<CategoryModel>) {
        categoryList.clear()
        categoryList.addAll(list)
        notifyDataSetChanged()
    }

    fun add(categoryModel: CategoryModel) {
        categoryList.add(categoryModel)
        notifyItemInserted(categoryList.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(categoryList[position])

    override fun getItemCount() = categoryList.size
}
