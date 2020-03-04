package com.mysore.ashtanga.yoga.yogarepublic

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class MembershipsAdapter(private val memberships: ArrayList<Membership>, val mContext: Context): RecyclerView.Adapter<MembershipsAdapter.MembershipHolder>() {

    val TAG = "PJ MembAdapter"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MembershipsAdapter.MembershipHolder {

        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return MembershipHolder(inflatedView)


    }

    override fun getItemCount() = memberships.size



    override fun onBindViewHolder(holder: MembershipsAdapter.MembershipHolder, position: Int) {
        val itemMembership = memberships[position]
        holder.bindMembership(itemMembership, mContext)

    }


    //1
    class MembershipHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var membership: Membership? = null

        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }


        fun bindMembership(membership: Membership, mContext: Context) {
            this.membership = membership
//            Picasso.with(view.context).load(photo.url).into(view.itemImage)



            if (membership.membershipName == "header") {
//                Log.e("PJbindMemebrship", "header???? ${membership.membershipName}")

                view.expirationDate.text = mContext.getString(R.string.valid_till)
                view.expirationDate.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                view.membershipName.text = mContext.getString(R.string.memberships)
                view.membershipName.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                view.isValidDot.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary))
                view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary))


            } else {
//                Log.e("PJbindMemebrship", "nie jest header???? ${membership.membershipName}")

                var expDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.US).format(membership.expirationDate)

                if (expDate == "01-01-2051") {
                    view.expirationDate.text = mContext.getString(R.string.permanent)

                } else {
//                    Log.e("PJ bindMembershpi", "wstawianie expData $expDate do pola  ")
                    view.expirationDate.text = expDate
                }


                view.membershipName.text = membership.membershipName



                if (membership.isValid) {
                    view.isValidDot.setColorFilter(ContextCompat.getColor(mContext, R.color.valid))
                } else {
                    view.isValidDot.setColorFilter(ContextCompat.getColor(mContext, R.color.inValid))
                }

            }

        }



//        companion object {
//            //5
//            private val MEMBERSHIP_KEY = "MEMBERSHIP"
//        }
    }



}