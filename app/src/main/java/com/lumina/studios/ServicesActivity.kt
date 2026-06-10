package com.lumina.studios

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.lumina.studios.databinding.ActivityServicesBinding
import com.lumina.studios.databinding.ItemServiceBinding

class ServicesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServicesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        val services = listOf(
            Service("portrait", "Portrait Session", "Professional lighting and posing for individuals or groups. Capturing the essence of human identity.", 0, listOf(
                Photo("Classic Mono", "Portrait", R.drawable.sample1),
                Photo("Studio Light", "Portrait", R.drawable.sample2)
            )),
            Service("fashion", "Fashion & Editorial", "High-end conceptual photography for brands and magazines. Visual storytelling for the modern era.", 0, listOf(
                Photo("Vogue Look", "Fashion", R.drawable.sample2),
                Photo("Runway Ready", "Fashion", R.drawable.sample4)
            )),
            Service("arch", "Architecture", "Capturing spaces, interiors, and structural design with technical precision and artistic flair.", 0, listOf(
                Photo("Modern Loft", "Architecture", R.drawable.sample3),
                Photo("Concrete Jungle", "Architecture", R.drawable.sample1)
            )),
            Service("product", "Product Shot", "Clean, sharp imagery to showcase your brand's products. High-fidelity asset capturing.", 0, listOf(
                Photo("Watch Macro", "Product", R.drawable.sample4),
                Photo("Tech Gear", "Product", R.drawable.sample3)
            ))
        )

        binding.servicesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.servicesRecycler.adapter = ServiceAdapter(services)
        
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.servicesRecycler)
    }

    inner class ServiceAdapter(private val list: List<Service>) : RecyclerView.Adapter<ServiceAdapter.VH>() {
        inner class VH(val b: ItemServiceBinding) : RecyclerView.ViewHolder(b.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return VH(ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val s = list[position]
            holder.b.serviceName.text = s.name
            holder.b.serviceDesc.text = s.description
            
            holder.b.samplesContainer.removeAllViews()
            s.samples.forEach { photo ->
                val iv = ImageView(this@ServicesActivity)
                val lp = ViewGroup.MarginLayoutParams(500, 700)
                lp.marginEnd = 24
                iv.layoutParams = lp
                iv.scaleType = ImageView.ScaleType.CENTER_CROP
                iv.setImageResource(photo.resId)
                iv.clipToOutline = true
                iv.setBackgroundResource(R.drawable.bg_glass)
                holder.b.samplesContainer.addView(iv)
            }

            holder.b.bookServiceBtn.setOnClickListener {
                val intent = Intent(this@ServicesActivity, BookingActivity::class.java)
                intent.putExtra("service_name", s.name)
                startActivity(intent)
            }
        }

        override fun getItemCount() = list.size
    }
}
