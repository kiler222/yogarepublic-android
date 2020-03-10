package com.mysore.ashtanga.yoga.yogarepublic


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_timetable.*
import org.joda.time.LocalDate


class TimetableFragment() : Fragment() {


    private val TAG = "PJ timetabelfrag"

    private lateinit var viewPager: ViewPager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_timetable, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val tabLayout = tab_layout  // view.findViewById(R.id.tab_layout)

        viewPager = pager
        viewPager.offscreenPageLimit = 7

        tabLayout.setupWithViewPager(viewPager)


        Log.e(TAG, "teraz onViewCrated")

        val adapter = TimetableAdapter(childFragmentManager)

        adapter.addFrag(MondayFragment(), getString(R.string.monday))
        adapter.addFrag(TuesdayFragment(), getString(R.string.tuesday))
        adapter.addFrag(WednesdayFragment(), getString(R.string.wednesday))
        adapter.addFrag(ThursdayFragment(), getString(R.string.thursday))
        adapter.addFrag(FridayFragment(), getString(R.string.friday))
        adapter.addFrag(SaturdayFragment(), getString(R.string.saturday))
        adapter.addFrag(SundayFragment(), getString(R.string.sunday))


        viewPager.adapter = adapter

        val dayOfWeekNumber = LocalDate.now().dayOfWeek //SimpleDateFormat("u", Locale.US).format(DateTime.now().millis).toInt()

        viewPager.setCurrentItem(dayOfWeekNumber-1)

    }

}
