package dark.composer.carpet.presentation.fragment.product.veiwpager_fragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.countable.CountableFragment
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.uncountable.UncountableFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when(position){
        0-> CountableFragment()
        1-> UncountableFragment()
        else -> CountableFragment()
    }
}