package com.mysore.ashtanga.yoga.yogarepublic

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.eunsiljo.timetablelib.data.TimeData
import com.github.eunsiljo.timetablelib.data.TimeTableData
import com.github.eunsiljo.timetablelib.view.TimeTableView
import kotlinx.android.synthetic.main.fragment_monday.*
import org.joda.time.DateTime
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MondayFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MondayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SaturdayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var mNow: Long = 0
    private val mLongHeaders: List<String> = Arrays.asList("Sala I", "Sala II")
    private val TAG = "PJ Sat Fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monday, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timeTable!!.setStartHour(6)

        timeTable!!.setShowHeader(true)
        timeTable!!.setTableMode(TimeTableView.TableMode.SHORT)
        val now = DateTime.now()
        mNow = now.withTimeAtStartOfDay().millis

        timeTable!!.setTimeTable(mNow, getTimetable(mNow, mLongHeaders))


    }



    private fun getTimetable(
        date: Long,
        headers: List<String>
    ): ArrayList<TimeTableData>? {
        val colors_table = resources.obtainTypedArray(R.array.colors_table)
        val colors_table_light =
            resources.obtainTypedArray(R.array.colors_table_light)
        val tables: ArrayList<TimeTableData> = ArrayList()
//        for (i in headers.indices) {
        val values: ArrayList<TimeData<*>> = ArrayList()
        var start = DateTime(date)
        Log.e(TAG, date.toString())


        var td1 = TimeData(0, "8:30 - 10:00\nAshtanga III\nPrzemek",
            R.color.ashtanga3color, R.color.white, start.plusMinutes(8*60+30).millis,
            start.plusHours(10).plusMinutes(0).millis)
        values.add(td1)


        var td11 = TimeData(0, "9:45 - 10:15\nMysore - Intro",
            R.color.mysorecolor, R.color.white, start.plusMinutes(9*60+45).millis,
            start.plusHours(10).plusMinutes(15).millis)
        values.add(td11)

        var td111 = TimeData(0, "11:00 - 12:15\nSzczęśliwy kręgosłup\nWojtek",
            R.color.kregoslupcolor, R.color.white, start.plusMinutes(11*60).millis,
            start.plusHours(12).plusMinutes(15).millis)
        values.add(td111)

        val td3 = TimeData(0, "12:30 - 13:45\nAshtanga I\nMagda",
            R.color.ashtanga1color, R.color.white, start.plusMinutes(12*60+30).millis,
            start.plusMinutes(13*60+45).millis)
        values.add(td3)

        val td2 = TimeData(0, "14:00 - 15:15\nRegeneracja - Magda",
            R.color.regeneracjacolor, R.color.white, start.plusMinutes(14*60).millis,
            start.plusMinutes(15*60+15).millis)
        values.add(td2)



//
//        val td4 = TimeData(0, "18:45 - 20:00\nPoczątkująca\nMałgosia",
//            R.color.poczatkujacacolor, R.color.white, start.plusMinutes(18*60+45).millis,
//            start.plusMinutes(20*60).millis)
//        values.add(td4)

        val td5 = TimeData(0, "15:30 - 16:45\nKurs Jogi - Magda\n13.01 - 29.01",
            R.color.kurscolor, R.color.white, start.plusMinutes(15*60+30).millis,
            start.plusMinutes(16*60+45).millis)
        values.add(td5)

        tables.add(TimeTableData(headers[0],values))





        val values2: ArrayList<TimeData<*>> = ArrayList()

        val td21 = TimeData(0, "9:45 - 11:15\nMysore\nPrzemek",
            R.color.mysorecolor, R.color.white, start.plusHours(8).plusMinutes(45).millis,
            start.plusMinutes(11*60+15).millis)
        values2.add(td21)



        val td22 = TimeData(0, "11:15 - 12:30\nYoga Course in English\n04.01 - 19.01 - Magda",
            R.color.kurscolor, R.color.white, start.plusMinutes(11*60+15).millis,
            start.plusMinutes(12*60+30).millis)
        values2.add(td22)

//        val td23 = TimeData(0, "18:00\nMysore Intro",
//            R.color.mysorecolor, R.color.white, start.plusMinutes(18*60).millis,
//            start.plusMinutes(19*60).millis)
//        values2.add(td23)


        val td24 = TimeData(0, "14:00 - 15:15\nKurs medytacji\n4,5 i 6.01 - Agnieszka",
            R.color.medytacjacolor, R.color.white, start.plusMinutes(14*60).millis,
            start.plusMinutes(15*60+15).millis)
        values2.add(td24)




        tables.add(TimeTableData(headers[1],values2))



        return tables
    }




    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MondayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MondayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
