package com.lumina.studios

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lumina.studios.databinding.ActivityBookingBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private var selectedDate: String = ""

    private val services = listOf(
        "Portrait Session",
        "Fashion & Editorial",
        "Architecture",
        "Product Shot"
    )

    private val sessionTypes = listOf(
        "Mini Session (30 mins)",
        "Standard Session (1 hour)",
        "Deluxe Session (2 hours)",
        "Full Day (8 hours)"
    )

    private val timeSlots = listOf(
        "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
        "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM",
        "04:00 PM", "05:00 PM", "06:00 PM"
    )

    // Base prices for services
    private val serviceBasePrices = mapOf(
        "Portrait Session" to 5000,
        "Fashion & Editorial" to 15000,
        "Architecture" to 20000,
        "Product Shot" to 10000
    )

    // Multipliers for session types
    private val typeMultipliers = mapOf(
        "Mini Session (30 mins)" to 0.6,
        "Standard Session (1 hour)" to 1.0,
        "Deluxe Session (2 hours)" to 1.8,
        "Full Day (8 hours)" to 6.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        // Setup Service Spinner
        val serviceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, services)
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.serviceSpinner.adapter = serviceAdapter

        // Setup Session Type Spinner
        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sessionTypes)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeSpinner.adapter = typeAdapter

        // Setup Time Slot Spinner
        val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeSlots)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.timeSpinner.adapter = timeAdapter

        // Setup Calendar
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        selectedDate = sdf.format(Calendar.getInstance().time) // Default to today

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = sdf.format(calendar.time)
        }

        // Pre-select service if passed from intent
        val selectedService = intent.getStringExtra("service_name")
        if (selectedService != null) {
            val index = services.indexOf(selectedService)
            if (index != -1) {
                binding.serviceSpinner.setSelection(index)
            }
        }

        // Setup listeners to update cost
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateCost()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.serviceSpinner.onItemSelectedListener = spinnerListener
        binding.typeSpinner.onItemSelectedListener = spinnerListener

        binding.bookBtn.setOnClickListener {
            val phone = binding.phoneInput.text.toString()
            val time = binding.timeSpinner.selectedItem.toString()
            
            if (phone.isEmpty()) {
                Toast.makeText(this, "Please provide a contact number", Toast.LENGTH_SHORT).show()
            } else {
                val finalCost = binding.costText.text.toString()
                val message = "Booking request for $selectedDate at $time ($finalCost) sent!"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun updateCost() {
        val selectedService = binding.serviceSpinner.selectedItem?.toString() ?: return
        val selectedType = binding.typeSpinner.selectedItem?.toString() ?: return

        val basePrice = serviceBasePrices[selectedService] ?: 0
        val multiplier = typeMultipliers[selectedType] ?: 1.0
        
        val totalCost = (basePrice * multiplier).toInt()
        binding.costText.text = "KSh $totalCost"
    }
}
