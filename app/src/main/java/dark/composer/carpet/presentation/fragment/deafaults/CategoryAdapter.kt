package dark.composer.carpet.presentation.fragment.deafaults

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import dark.composer.carpet.R
import dark.composer.carpet.databinding.ItemCategoryBinding
import dark.composer.carpet.data.dto.CategoryModel

class CategoryAdapter(private var context: Context) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private val categoryList = mutableListOf<CategoryModel>()

    private var clickListener: ((description:TextView, price:TextView, image:ImageView) -> Unit)? = null

    fun setClickListener(f: (description:TextView,price:TextView,image:ImageView) -> Unit) {
        clickListener = f
    }

    inner class CategoryViewHolder(private var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CategoryModel) {
            binding.name.text = data.description
//            Glide.with(context).load(data.image)
//                .override(100, 100)
//                .into(binding.image)
            binding.image.setImageResource(data.image)
            binding.price.text = data.rate

            binding.name.transitionName = "name_$layoutPosition"
            binding.price.transitionName = "price_$layoutPosition"
            binding.image.transitionName = "image_$layoutPosition"

            ViewCompat.setTransitionName(binding.name, "name_$layoutPosition");
            ViewCompat.setTransitionName(binding.price, "price_$layoutPosition");
            ViewCompat.setTransitionName(binding.image, "image_$layoutPosition");

            val anim = AnimationUtils.loadAnimation(context, R.anim.slide_up)
            itemView.startAnimation(anim)

            itemView.setOnClickListener {
                clickListener?.invoke(binding.name,binding.price,binding.image)
            }
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
