package com.lumina.studios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lumina.studios.databinding.*
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter(
    private val photos: List<Photo>,
    private val onPhotoClick: (Photo) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_STORY = 0
        private const val TYPE_ARCHIVE = 1
        private const val TYPE_SERVICES = 2
        private const val TYPE_PRICING = 3
        private const val TYPE_BOOKING = 4
        private const val TYPE_CONTACT = 5
    }

    private val services = listOf("Portrait Session", "Fashion & Editorial", "Architecture", "Product Shot", "Wedding", "Corporate Event")
    private val sessionTypes = listOf("Mini Session", "Standard Session", "Deluxe Session", "Full Day")
    private val timeSlots = listOf("08:00 AM", "10:00 AM", "12:00 PM", "02:00 PM", "04:00 PM", "06:00 PM")

    private val serviceBasePrices = mapOf(
        "Portrait Session" to 5000,
        "Fashion & Editorial" to 15000,
        "Architecture" to 20000,
        "Product Shot" to 10000,
        "Wedding" to 200000,
        "Corporate Event" to 50000
    )
    private val typeMultipliers = mapOf(
        "Mini Session" to 0.6,
        "Standard Session" to 1.0,
        "Deluxe Session" to 1.8,
        "Full Day" to 6.0
    )

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_STORY -> StoryVH(ItemMainStoryBinding.inflate(inflater, parent, false))
            TYPE_ARCHIVE -> ArchiveVH(ItemMainArchiveBinding.inflate(inflater, parent, false))
            TYPE_SERVICES -> ServicesVH(ItemMainServicesBinding.inflate(inflater, parent, false))
            TYPE_PRICING -> PricingVH(ItemMainPricingBinding.inflate(inflater, parent, false))
            TYPE_BOOKING -> BookingVH(ItemMainBookingBinding.inflate(inflater, parent, false))
            TYPE_CONTACT -> ContactVH(ItemMainContactBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        when (holder) {
            is ArchiveVH -> {
                holder.b.archiveRecycler.layoutManager = LinearLayoutManager(context)
                holder.b.archiveRecycler.adapter = InnerPhotoAdapter(photos, onPhotoClick)
            }
            is ServicesVH -> {
                holder.b.innerServicesRecycler.layoutManager = LinearLayoutManager(context)
                holder.b.innerServicesRecycler.adapter = InnerServiceAdapter()
            }
            is PricingVH -> {
                holder.b.pricingRecycler.layoutManager = LinearLayoutManager(context)
                holder.b.pricingRecycler.adapter = InnerPricingAdapter()
            }
            is BookingVH -> {
                val sAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, services)
                sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                holder.b.bookingServiceSpinner.adapter = sAdapter

                val tAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, sessionTypes)
                tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                holder.b.bookingTypeSpinner.adapter = tAdapter

                val timeAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, timeSlots)
                timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                holder.b.bookingTimeSpinner.adapter = timeAdapter

                var selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)
                holder.b.bookingCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val cal = Calendar.getInstance()
                    cal.set(year, month, dayOfMonth)
                    selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
                }

                val updateCost = {
                    val sService = holder.b.bookingServiceSpinner.selectedItem?.toString() ?: ""
                    val sType = holder.b.bookingTypeSpinner.selectedItem?.toString() ?: ""
                    val base = serviceBasePrices[sService] ?: 0
                    val mult = typeMultipliers[sType] ?: 1.0
                    val total = (base * mult).toInt()
                    holder.b.bookingCostText.text = "KSh $total"
                }

                val listener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) { updateCost() }
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
                holder.b.bookingServiceSpinner.onItemSelectedListener = listener
                holder.b.bookingTypeSpinner.onItemSelectedListener = listener

                holder.b.submitBookingBtn.setOnClickListener {
                    val phone = holder.b.bookingPhoneInput.text.toString()
                    if (phone.isEmpty()) {
                        Toast.makeText(context, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Your booking for $selectedDate has been sent!", Toast.LENGTH_LONG).show()
                    }
                }
            }
            is ContactVH -> {
                holder.b.callBtn.setOnClickListener { Toast.makeText(context, "Calling +254 143 356 401...", Toast.LENGTH_SHORT).show() }
                holder.b.emailBtn.setOnClickListener { Toast.makeText(context, "Opening Email...", Toast.LENGTH_SHORT).show() }
                holder.b.tiktokBtn.setOnClickListener { Toast.makeText(context, "Opening TikTok...", Toast.LENGTH_SHORT).show() }
                holder.b.instagramBtn.setOnClickListener { Toast.makeText(context, "Opening Instagram...", Toast.LENGTH_SHORT).show() }
                holder.b.youtubeBtn.setOnClickListener { Toast.makeText(context, "Opening YouTube...", Toast.LENGTH_SHORT).show() }
                holder.b.facebookBtn.setOnClickListener { Toast.makeText(context, "Opening Facebook...", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    override fun getItemCount() = 6

    inner class StoryVH(val b: ItemMainStoryBinding) : RecyclerView.ViewHolder(b.root)
    inner class ArchiveVH(val b: ItemMainArchiveBinding) : RecyclerView.ViewHolder(b.root)
    inner class ServicesVH(val b: ItemMainServicesBinding) : RecyclerView.ViewHolder(b.root)
    inner class PricingVH(val b: ItemMainPricingBinding) : RecyclerView.ViewHolder(b.root)
    inner class BookingVH(val b: ItemMainBookingBinding) : RecyclerView.ViewHolder(b.root)
    inner class ContactVH(val b: ItemMainContactBinding) : RecyclerView.ViewHolder(b.root)

    inner class InnerPhotoAdapter(val list: List<Photo>, val onClick: (Photo) -> Unit) : RecyclerView.Adapter<InnerPhotoAdapter.VH>() {
        inner class VH(val b: ItemPhotoFullBinding) : RecyclerView.ViewHolder(b.root)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(ItemPhotoFullBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        override fun onBindViewHolder(holder: VH, position: Int) {
            val p = list[position]
            holder.b.image.setImageResource(p.resId)
            holder.b.title.text = p.title
            holder.b.category.text = p.category
            holder.b.root.setOnClickListener { onClick(p) }
        }
        override fun getItemCount() = list.size
    }

    inner class InnerServiceAdapter : RecyclerView.Adapter<InnerServiceAdapter.VH>() {
        private val services = listOf(
            "Portrait Session" to "Clear and professional photos of people.",
            "Fashion Editorial" to "Stylish photos for clothing and brands.",
            "Architectural" to "High-quality photos of buildings and interiors.",
            "Wedding" to "Complete coverage of your special day.",
            "Corporate" to "Professional photos for your business events."
        )
        inner class VH(val b: ItemServiceBinding) : RecyclerView.ViewHolder(b.root)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        override fun onBindViewHolder(holder: VH, position: Int) {
            val (n, d) = services[position]
            holder.b.serviceName.text = n
            holder.b.serviceDesc.text = d
            holder.b.bookServiceBtn.visibility = View.GONE
            holder.b.samplesContainer.removeAllViews()
        }
        override fun getItemCount() = services.size
    }

    inner class InnerPricingAdapter : RecyclerView.Adapter<InnerPricingAdapter.VH>() {
        private val pricingData = listOf(
            PricingInfo("Bronze Package", "KES 150k", "Starter", "KES 200k", "• 1 Photographer | 1 Videographer\n• 6 Hours of coverage"),
            PricingInfo("Silver Package", "KES 250k", "Standard", "KES 350k", "• 2 Photographers | 2 Videographers\n• 10 Hours of coverage"),
            PricingInfo("Gold Package", "KES 450k", "Premium", "KES 600k", "• 3 Photographers | 2 Videographers | 1 Drone\n• 12 Hours of coverage"),
            PricingInfo("Platinum Package", "KES 750k", "Unlimited", "KES 1M+", "• 5 Photographers | 3 Videographers | 2 Drones\n• Multi-day coverage")
        )
        inner class VH(val b: ItemPricingDetailBinding) : RecyclerView.ViewHolder(b.root)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(ItemPricingDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = pricingData[position]
            holder.b.serviceName.text = item.service
            holder.b.normalPrice.text = item.normal
            holder.b.packageName.text = item.packageName
            holder.b.packagePrice.text = item.packagePrice
            holder.b.packageFeatures.text = item.features
        }
        override fun getItemCount() = pricingData.size
    }

    data class PricingInfo(val service: String, val normal: String, val packageName: String, val packagePrice: String, val features: String)
}
