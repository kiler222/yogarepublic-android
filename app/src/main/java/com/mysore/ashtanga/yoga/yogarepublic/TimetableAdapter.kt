package com.mysore.ashtanga.yoga.yogarepublic


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TimetableAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragmentList: MutableList<Fragment> = ArrayList()
    private val mFragmentTitleList: MutableList<String> = ArrayList()


    override fun getCount(): Int  = mFragmentList.count()

    override fun getItem(i: Int): Fragment {

        return mFragmentList.get(i)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitleList.get(position)
    }

    fun addFrag(
        fragment: Fragment?,
        title: String?
    ) {
        mFragmentList.add(fragment!!)
        mFragmentTitleList.add(title!!)
    }


}

