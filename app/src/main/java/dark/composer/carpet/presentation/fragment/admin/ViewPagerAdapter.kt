package dark.composer.carpet.presentation.fragment.admin

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dark.composer.carpet.presentation.fragment.factory.FactoryFragment
import dark.composer.carpet.presentation.fragment.product.ProductFragment

class ViewPagerAdapter(fragment:Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when(position){
        0 -> ProductFragment()
        1 -> FactoryFragment()
        else -> FactoryFragment()
    }
}