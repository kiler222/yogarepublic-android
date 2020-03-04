package com.mysore.ashtanga.yoga.yogarepublic

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.eunsiljo.timetablelib.data.TimeData
import com.github.eunsiljo.timetablelib.data.TimeTableData
import com.github.eunsiljo.timetablelib.view.TimeTableView
import kotlinx.android.synthetic.main.fragment_monday.*
import java.text.SimpleDateFormat
import java.util.*


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
class ThursdayFragment: Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var mNow: Long = 0
    private val mLongHeaders = SharedDate.mLongerHeaders//private val mLongHeaders: List<String> = Arrays.asList(getString(R.string.small_room), getString(R.string.big_room))
    private val TAG = "PJ Thursday Fragment"

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
        val begining = (SharedDate.thursdayBig[0].getValue("start") as Double).toLong()
        val date = Date(begining)
        val format = SimpleDateFormat("yyyy.MM.dd")
        val dateBegining = format.format(date)
        val df = SimpleDateFormat("yyyy.MM.dd")
        mNow = df.parse(dateBegining).time


        timeTable!!.setTimeTable(mNow, getTimetable(mNow, mLongHeaders))


    }




    private fun getTimetable(
        date: Long,
        headers: List<String>
    ): ArrayList<TimeTableData>? {
        val tables: ArrayList<TimeTableData> = ArrayList()
        val values: ArrayList<TimeData<*>> = ArrayList()
        //SALA 1 ---------------------
        SharedDate.thursdaySmall.forEach {

            val col = selectColor(it.getValue("name").toString())
            val st = Date((it.getValue("start") as Double).toLong())
            val startTime = SimpleDateFormat("HH:mm").format(st)
            val et = Date((it.getValue("end") as Double).toLong())
            val endTime = SimpleDateFormat("HH:mm").format(et)
            var td1 = TimeData(0, "$startTime - $endTime\n${it.getValue("name")} - ${it.getValue("teacher")}",
                col, R.color.white, (it.getValue("start") as Double).toLong(),
                (it.getValue("end") as Double).toLong())
            values.add(td1)

        }

        tables.add(TimeTableData(headers[0],values))



        //SALA 2 ---------------------

        val values2: ArrayList<TimeData<*>> = ArrayList()





        SharedDate.thursdayBig.forEach {


            val col = selectColor(it.getValue("name").toString())
            val st = Date((it.getValue("start") as Double).toLong())
            val startTime = SimpleDateFormat("HH:mm").format(st)
            val et = Date((it.getValue("end") as Double).toLong())
            val endTime = SimpleDateFormat("HH:mm").format(et)
            var td1 = TimeData(0, "$startTime - $endTime\n${it.getValue("name")} - ${it.getValue("teacher")}",
                col, R.color.white, (it.getValue("start") as Double).toLong(),
                (it.getValue("end") as Double).toLong())
            values2.add(td1)

        }


        tables.add(TimeTableData(headers[1],values2))



        return tables

    }





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
