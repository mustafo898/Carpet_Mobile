package dark.composer.carpet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.databinding.ItemCategoryBinding
import dark.composer.carpet.dto.CategoryModel

class CategoryAdapter(private var context: Context) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private val categoryList = mutableListOf<CategoryModel>()

    inner class CategoryViewHolder(private var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CategoryModel) {
            binding.name.text = data.description
//            Glide.with(context).load(data.image)
//                .override(100, 100)
//                .into(binding.image)
            binding.image.setImageResource(data.image)
            binding.price.text = data.rate
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
        ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(categoryList[position])

    override fun getItemCount() = categoryList.size
}
