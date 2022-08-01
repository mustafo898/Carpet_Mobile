package dark.composer.carpet.presentation.fragment.factory.factory_detail

//import dark.composer.carpet.databinding.ItemFactoryDetailsBinding

//class Adapter(val context:Context,val adapter: dark.composer.carpet.presentation.fragment.deafaults.FactoryAdapter) : RecyclerView.Adapter<Adapter.DetailsViewHolder>() {
//    private val fragmentList = mutableListOf<FactoryResponse>()
//    private val pagination = mutableListOf<FactoryResponse>()
//
//    private var clickListener: ((position:Int) -> Unit)? = null
//
//    fun setClickListener(f: (position:Int) -> Unit) {
//        clickListener = f
//    }
//
//    inner class DetailsViewHolder(val binding:ItemFactoryDetailsBinding):RecyclerView.ViewHolder(binding.root){
//        fun bind(factoryResponse: FactoryResponse) {
//            binding.status.text = factoryResponse.status
//            binding.name.text = factoryResponse.name
//            binding.time.text = factoryResponse.createdDate.substring(11,16)
//            binding.date.text = factoryResponse.createdDate.substring(0,10)
//            Glide.with(binding.root).load(factoryResponse.photoUrl).into(binding.image)
//            binding.list.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//            binding.list.adapter = adapter
//        }
//    }
//
//    fun setFragment(list:List<FactoryResponse>){
//        fragmentList.clear()
//        fragmentList.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    fun setPagination(list: List<FactoryResponse>){
//        pagination.clear()
//        pagination.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DetailsViewHolder(
//        ItemFactoryDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
//
//    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) = holder.bind(fragmentList[position])
//
//    override fun getItemCount() = fragmentList.size
//}