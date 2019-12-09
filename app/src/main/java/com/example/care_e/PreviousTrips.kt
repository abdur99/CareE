//package com.example.care_e
//
//import android.content.Context
//import android.net.Uri
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageButton
//import android.widget.TextView
//import android.widget.Toast
//import androidx.lifecycle.ViewModelProviders
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.android.synthetic.main.fragment_previous_trips.*
//import java.math.BigDecimal
//import java.math.RoundingMode
//import java.util.*
//
//
//class PreviousTrips : Fragment(), ViewModel.DataChangedListener {
//    var name = ""
//
//    override fun updateRecycler() {
//        viewAdapter.notifyDataSetChanged()
//    }
//
//    lateinit var CarEViewModel: ViewModel
//    lateinit var viewAdapter: RecyclerViewAdapter
//    lateinit var viewManager: RecyclerView.LayoutManager
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        CarEViewModel = activity?.run{
//            ViewModelProviders.of(this).get(CarEViewModel::class.java)
//        }?: throw Exception("activity invalid")
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_previous_trips, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        if ((arguments?.getString("name")) != null) { name = arguments?.getString("name").toString() }
//
//        CarEViewModel.listener = this
//
//        viewManager = LinearLayoutManager(context)
//        viewAdapter = RecyclerViewAdapter(ArrayList())
//
//        trips_recycler.apply {
//            this.layoutManager = viewManager
//            this.adapter = viewAdapter
//        }
//
//        CarEViewModel.Trips.observe(this, Observer {
//            viewAdapter.tripList = it
//            viewAdapter.notifyDataSetChanged()
//        })
//
//        ItemTouchHelper(SwipeHelper()).attachToRecyclerView(trips_recycler)
//    }
//
//    private fun itemSelected(trip: TripInfo){
//    }
//
//    inner class SwipeHelper : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean {
//            return false
//        }
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            var curTrip = CarEViewModel.Trips.value!!.get(viewHolder.adapterPosition)
//            val user = FirebaseAuth.getInstance().currentUser
//            user?.let {
//                name = user.uid
//            }
//            CarEViewModel.UserUID.value = name
//
//            if (CarEViewModel.UserUID.value == name)  {
//                //CarEViewModel.deleteTrip(curTrip)
//            } else {
//                Toast.makeText(getActivity(),"You cannot delete this recording. It belongs to someone else!", Toast.LENGTH_LONG).show()
//            }
//            viewAdapter.notifyDataSetChanged()
//        }
//    }
//
//    class RecyclerViewAdapter(var tripList: ArrayList<TripInfo>) :
//        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
//            val view: View =
//                LayoutInflater.from(parent.context).inflate(R.layout.trip_item, parent, false)
//
//            return RecyclerViewHolder(view)
//        }
//
//        override fun getItemCount(): Int {
//            return tripList.size
//        }
//
//        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
//            holder.bind(tripList[position])
//        }
//
//        class RecyclerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//            fun bind(trip: TripInfo) {
//                view.run {
//                    view.findViewById<TextView>(R.id.distance).text = (BigDecimal(trip.tripdistance).setScale(3, RoundingMode.HALF_EVEN)).toString() + "km"
//                    view.findViewById<TextView>(R.id.cost).text = "$ " + (BigDecimal(trip.tripcost).setScale(3, RoundingMode.HALF_EVEN)).toString()
//                    view.findViewById<TextView>(R.id.emissions).text = (BigDecimal(trip.tripemission/1000).setScale(3, RoundingMode.HALF_EVEN)).toString() + "kg"
//                }
//            }
//        }
//    }
//
//}
