package dark.composer.carpet.presentation.fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.databinding.ItemProfile1Binding
//import dark.composer.carpet.databinding.ItemProfilesBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    private val usersList = mutableListOf<ProfileResponse>()

    private var clickListener: ((id: Int) -> Unit)? = null

    fun setClickListener(f: (id: Int) -> Unit) {
        clickListener = f
    }

    inner class UsersViewHolder(private val binding: ItemProfile1Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProfileResponse) {
            if (data.url.isNullOrEmpty()) {
                binding.image.setImageResource(R.drawable.ic_person)
            }else{
                Glide.with(binding.root).load(data.url).into(binding.image)
            }
            binding.name.text = data.name+" " + data.surname
            binding.role.text = data.role

            itemView.setOnClickListener {
                clickListener?.invoke(data.id)
            }
        }
    }

    fun setList(list: List<ProfileResponse>) {
        usersList.clear()
        usersList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(
        ItemProfile1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) =
        holder.bind(usersList[position])

    override fun getItemCount() = usersList.size
}