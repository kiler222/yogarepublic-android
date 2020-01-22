package com.mysore.ashtanga.yoga.yogarepublic

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.github.eunsiljo.timetablelib.data.TimeData
import com.github.eunsiljo.timetablelib.data.TimeTableData
import com.github.eunsiljo.timetablelib.view.TimeTableView
import kotlinx.android.synthetic.main.activity_schedule.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.milliseconds

class Schedule : AppCompatActivity() {


    private val TAG = "PJ Schedule"
//    private var btnMode: View? = null
//    private var timeTable: TimeTableView? = null

    private val mShortSamples: ArrayList<TimeTableData> = ArrayList()
    private val mLongSamples: ArrayList<TimeTableData> = ArrayList()

    private val mTitles: List<String> =
        Arrays.asList("Korean", "English", "Math", "Science", "Physics", "Chemistry", "Biology")
    private val mLongHeaders: List<String> = Arrays.asList("Sala I", "Sala II")
    private val mShortHeaders: List<String> =
        Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    private var mNow: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
//        initLayout()




//        initListener()
        initData()
    }

//    private fun initLayout() {
//        btnMode = btnMode //findViewById(R.id.btnMode)
//        timeTable = timeTable //findViewById(R.id.timeTable) as TimeTableView
//
//    }

//    private fun initListener() {
//        btnMode!!.setOnClickListener {
//
//            Log.e(TAG, "klikniety btnMode")
//            toogleMode()
//            if (it.isActivated) {
//                timeTable!!.setShowHeader(false)
//                timeTable!!.setTableMode(TimeTableView.TableMode.SHORT)
//                Log.e(TAG, "Mode Short")
//                //timeTable.setTimeTable(getMillis("2017-11-10 00:00:00"), mShortSamples);
//                timeTable!!.setTimeTable(mNow, getSamples(mNow, mShortHeaders, mTitles))
////                timeTable!!.setTimeTable(mNow, getTimetable(mNow, mLongHeaders))
//            } else {
//                timeTable!!.setShowHeader(false)
//                timeTable!!.setTableMode(TimeTableView.TableMode.LONG)
//                Log.e(TAG, "Mode long")
//                //timeTable.setTimeTable(getMillis("2017-11-10 00:00:00"), mLongSamples);
//                timeTable!!.setTimeTable(mNow, getSamples(mNow, mLongHeaders, mTitles))
////                timeTable!!.setTimeTable(mNow, getTimetable(mNow, mLongHeaders))
//            }
//        }
//
//
//        timeTable!!.setOnTimeItemClickListener { view, position, item ->
//            val time = item.time
////            showToast(
////                this@Schedule,
////                time.title + ", " + DateTime(time.startMills).toString() +
////                        " ~ " + DateTime(time.stopMills).toString()
////            )
//
//            Toast.makeText(this, time.title + ", " + DateTime(time.startMills).toString() +
//                    " ~ " + DateTime(time.stopMills).toString(), Toast.LENGTH_LONG ).show()
//
//        }
//
//
//
//    }

    fun initData() { //initLongSamples();
//initShortSamples();
        timeTable!!.setStartHour(6)

        timeTable!!.setShowHeader(false)
        timeTable!!.setTableMode(TimeTableView.TableMode.SHORT)
        val now = DateTime.now()
        mNow = now.withTimeAtStartOfDay().millis

        Log.e(TAG, "mnow $mNow")
//        mNow = now.withTimeAtStartOfDay().millis
//        timeTable.setTimeTable(getMillis("2017-11-10 00:00:00"), mLongSamples)
//        timeTable!!.setTimeTable(mNow, getSamples(mNow, mLongHeaders, mTitles))
        timeTable!!.setTimeTable(mNow, getTimetable(mNow, mLongHeaders))



    }

    private fun getSamples(
        date: Long,
        headers: List<String>,
        titles: List<String>
    ): ArrayList<TimeTableData>? {
        val colors_table = resources.obtainTypedArray(R.array.colors_table)
        val colors_table_light =
            resources.obtainTypedArray(R.array.colors_table_light)
        val tables: ArrayList<TimeTableData> = ArrayList()
        for (i in headers.indices) {
            val values: ArrayList<TimeData<*>> = ArrayList()
            var start = DateTime(date)
            var end =
                start.plusMinutes((Math.random() * 10 + 1).toInt() * 30)
            for (j in titles.indices) {
                var color = colors_table_light.getResourceId(j, 0)
                var textColor = R.color.black
                //TEST
                if (headers.size == 2 && i == 1) {
                    color = colors_table.getResourceId(j, 0)
                    textColor = R.color.white
                }
                val timeData: TimeData<*> =
                    TimeData<Any?>(
                        j,
                        titles[j],
                        color,
                        textColor,
                        start.millis,
                        end.millis
                    )
                //TEST
                if (headers.size == 2 && j == 2) {
                    timeData.isShowError = true
                }
                values.add(timeData)
                start = end.plusMinutes((Math.random() * 10 + 1).toInt() * 10)
                end = start.plusMinutes((Math.random() * 10 + 1).toInt() * 30)
            }
            tables.add(TimeTableData(headers[i], values))

        }


//        tables.forEach {
//
//
//            Log.e(TAG, "${it.header}")
//
//            val values = it.values
//
//            values.forEach {
//
//                Log.e(TAG, it.title)
//
//                Log.e(TAG, it.colorRes.toString())
//                Log.e(TAG, it.key.toString())
//                Log.e(TAG, it.textColorRes.toString())
//                Log.e(TAG, it.startMills.toString())
//                Log.e(TAG, it.stopMills.toString())
//
//
//            }
//        }

//        val td = TimeData()

        return tables
    }


    fun getTimetable(
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


            var td1 = TimeData(0, "7:00 - 8:30\nAshtanga III\nPrzemek",
                R.color.ashtanga3color, R.color.white, start.plusMinutes(7*60).millis,
                start.plusHours(8).plusMinutes(30).millis)
            values.add(td1)




        val td2 = TimeData(0, "16:30 - 17:30\nRegeneracja - Marta",
                R.color.regeneracjacolor, R.color.white, start.plusMinutes(16*60+30).millis,
                start.plusMinutes(17*60+30).millis)
            values.add(td2)

            val td3 = TimeData(0, "17:30 - 18:45\nAshtanga I\nMarek",
                R.color.ashtanga1color, R.color.white, start.plusMinutes(17*60+30).millis,
                start.plusMinutes(18*60+45).millis)
            values.add(td3)


            val td4 = TimeData(0, "18:45 - 20:00\nPoczątkująca\nMałgosia",
                R.color.poczatkujacacolor, R.color.white, start.plusMinutes(18*60+45).millis,
                start.plusMinutes(20*60).millis, true)
            values.add(td4)

            val td5 = TimeData(0, "20:15 - 21:30\nKurs Jogi - Marta\n13.01 - 29.01",
                R.color.kurscolor, R.color.white, start.plusMinutes(20*60+15).millis,
                start.plusMinutes(21*60+30).millis)
            values.add(td5)

            tables.add(TimeTableData(headers[0],values))


        val values2: ArrayList<TimeData<*>> = ArrayList()

        val td21 = TimeData(0, "13:00 - 14:00\nLunch Time Yoga - Iza",
            R.color.lunchcolor, R.color.white, start.plusHours(13).millis,
            start.plusMinutes(14*60).millis)
        values2.add(td21)



        val td22 = TimeData(0, "16:00 - 19:00\nMysore\nMonika\n\n\n\n18:00 Mysore Intro",
            R.color.mysorecolor, R.color.white, start.plusMinutes(16*60).millis,
            start.plusMinutes(19*60).millis)
        values2.add(td22)

//        val td23 = TimeData(0, "18:00\nMysore Intro",
//            R.color.mysorecolor, R.color.white, start.plusMinutes(18*60).millis,
//            start.plusMinutes(19*60).millis)
//        values2.add(td23)


        val td24 = TimeData(0, "19:15 - 20:30\nAshtanga II\nPrzemek",
            R.color.ashtanga2color, R.color.white, start.plusMinutes(19*60+15).millis,
            start.plusMinutes(20*60+30).millis)
        values2.add(td24)




        tables.add(TimeTableData(headers[1],values2))



        return tables
    }





    private fun initLongSamples() { //TEST
        val values: ArrayList<TimeData<*>> = ArrayList()
        values.add(
            TimeData<Any?>(
                0,
                "Korean",
                R.color.color_table_1_light,
                getMillis("2017-11-10 04:00:00"),
                getMillis("2017-11-10 05:00:00")
            )
        )
        values.add(
            TimeData<Any?>(
                1,
                "English",
                R.color.color_table_2_light,
                getMillis("2017-11-10 07:00:00"),
                getMillis("2017-11-10 08:00:00")
            )
        )
        val values2: ArrayList<TimeData<*>> = ArrayList()
        values2.add(
            TimeData<Any?>(
                0,
                "Korean",
                R.color.color_table_1,
                R.color.white,
                getMillis("2017-11-10 03:00:00"),
                getMillis("2017-11-10 06:00:00")
            )
        )
        val timeData: TimeData<*> =
            TimeData<Any?>(
                1,
                "English",
                R.color.color_table_2,
                R.color.white,
                getMillis("2017-11-10 07:30:00"),
                getMillis("2017-11-10 08:55:00")
            )
        timeData.isShowError = true
        values2.add(timeData)
        values2.add(
            TimeData<Any?>(
                2,
                "Math",
                R.color.color_table_3,
                R.color.white,
                getMillis("2017-11-10 10:40:00"),
                getMillis("2017-11-10 11:45:00")
            )
        )
        values2.add(
            TimeData<Any?>(
                3,
                "Science",
                R.color.color_table_4,
                R.color.white,
                getMillis("2017-11-10 15:00:00"),
                getMillis("2017-11-10 17:10:00")
            )
        )
        values2.add(
            TimeData<Any?>(
                4,
                "Physics",
                R.color.color_table_5,
                R.color.white,
                getMillis("2017-11-10 17:30:00"),
                getMillis("2017-11-10 21:30:00")
            )
        )
        values2.add(
            TimeData<Any?>(
                5,
                "Chemistry",
                R.color.color_table_6,
                R.color.white,
                getMillis("2017-11-10 21:31:00"),
                getMillis("2017-11-10 22:45:00")
            )
        )
        values2.add(
            TimeData<Any?>(
                6,
                "Biology",
                R.color.color_table_7,
                R.color.white,
                getMillis("2017-11-10 23:00:00"),
                getMillis("2017-11-11 02:30:00")
            )
        )
        val tables: ArrayList<TimeTableData> = ArrayList()
        tables.add(TimeTableData("Plan", values))
        tables.add(TimeTableData("Do", values2))
        mLongSamples.addAll(tables)
    }

    private fun initShortSamples() { //TEST
        val values: ArrayList<TimeData<*>> = ArrayList()
        values.add(
            TimeData<Any?>(
                0,
                "Korean",
                R.color.color_table_1_light,
                getMillis("2017-11-10 04:00:00"),
                getMillis("2017-11-10 05:00:00")
            )
        )
        values.add(
            TimeData<Any?>(
                1,
                "English",
                R.color.color_table_2_light,
                getMillis("2017-11-10 07:00:00"),
                getMillis("2017-11-10 08:00:00")
            )
        )
        val values2: ArrayList<TimeData<*>> = ArrayList()
        values2.add(
            TimeData<Any?>(
                0,
                "Korean",
                R.color.color_table_1_light,
                getMillis("2017-11-10 03:00:00"),
                getMillis("2017-11-10 06:00:00")
            )
        )
        values2.add(
            TimeData<Any?>(
                1,
                "English",
                R.color.color_table_2_light,
                getMillis("2017-11-10 07:30:00"),
                getMillis("2017-11-10 08:30:00")
            )
        )
        values2.add(
            TimeData<Any?>(
                2,
                "Math",
                R.color.color_table_3_light,
                getMillis("2017-11-10 11:40:00"),
                getMillis("2017-11-10 11:45:00")
            )
        )
        values2.add(
            TimeData<Any?>(
                3,
                "Science",
                R.color.color_table_4_light,
                getMillis("2017-11-10 18:00:00"),
                getMillis("2017-11-10 18:10:00")
            )
        )
        values2.add(
            TimeData<Any?>(
                4,
                "Physics",
                R.color.color_table_5_light,
                getMillis("2017-11-10 20:00:00"),
                getMillis("2017-11-10 21:30:00")
            )
        )
        values2.add(
            TimeData<Any?>(
                5,
                "Chemistry",
                R.color.color_table_6_light,
                getMillis("2017-11-10 21:31:00"),
                getMillis("2017-11-10 22:45:00")
            )
        )
        values2.add(
            TimeData<Any?>(
                6,
                "Biology",
                R.color.color_table_7_light,
                getMillis("2017-11-10 23:00:00"),
                getMillis("2017-11-11 02:30:00")
            )
        )
        val tables: ArrayList<TimeTableData> = ArrayList()
        tables.add(TimeTableData("Sun", values))
        tables.add(TimeTableData("Mon", values2))
        tables.add(TimeTableData("Tue", values))
        tables.add(TimeTableData("Wed", values2))
        tables.add(TimeTableData("Thu", values))
        tables.add(TimeTableData("Fri", values2))
        tables.add(TimeTableData("Sat", values))
        mShortSamples.addAll(tables)
    }

//    private fun toogleMode() {
//        btnMode!!.isActivated = !btnMode!!.isActivated
//    }

    // =============================================================================
    // Date format
    // =============================================================================

    // =============================================================================
// Date format
// =============================================================================
    private fun getMillis(day: String): Long {
        val date: DateTime = getDateTimePattern().parseDateTime(day)
        return date.millis
    }

    private fun getDateTimePattern(): DateTimeFormatter {
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    }

    // =============================================================================
    // Toast
    // =============================================================================

    // =============================================================================
// Toast
// =============================================================================
    private fun showToast(activity: Activity, msg: String) {
        val toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        val v =
            toast.view.findViewById<View>(R.id.message) as TextView
        if (v != null) v.gravity = Gravity.CENTER
        toast.show()
    }



}
