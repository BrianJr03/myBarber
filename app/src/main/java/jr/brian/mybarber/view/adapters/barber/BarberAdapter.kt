package jr.brian.mybarber.view.adapters.barber

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.BarberCardBinding
import jr.brian.mybarber.model.data.Constant
import jr.brian.mybarber.model.data.barber.Barber
import jr.brian.mybarber.view.activities.barber.BarberServicesActivity

class BarberAdapter(
    private val context: Context,
    private val barbers: List<Barber>
) :
    RecyclerView.Adapter<BarberAdapter.BarberViewHolder>() {

    lateinit var binding: BarberCardBinding

    override fun getItemCount() = barbers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarberViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = BarberCardBinding.inflate(layoutInflater, parent, false)
        return BarberViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BarberViewHolder, position: Int) {
        holder.apply {
            val barberCard = barbers[position]
            bind(barberCard)
            itemView.setOnClickListener {
                context.startActivity(Intent(context, BarberServicesActivity::class.java))
            }
        }
    }

    inner class BarberViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(barberCard: Barber) {
            val name = v.findViewById<TextView>(R.id.barber_name)
            val rating = v.findViewById<AppCompatRatingBar>(R.id.barber_rating)
            val pfpUrl = v.findViewById<AppCompatImageView>(R.id.barber_image)
            name.text = barberCard.barberName
            rating.rating = barberCard.userRating.toFloat()
            Glide.with(context)
                .load(Constant.BASE_IMAGE_URL + barberCard.profilePic)
                .into(pfpUrl)
        }
    }
}
