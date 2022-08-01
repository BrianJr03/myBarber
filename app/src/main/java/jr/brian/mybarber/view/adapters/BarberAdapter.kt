package jr.brian.mybarber.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.BarberCardBinding
import jr.brian.mybarber.model.data.barber.Barber

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
                // TODO - Launch select services activity
            }
        }
    }

    inner class BarberViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(barberCard: Barber) {
            val name = v.findViewById<TextView>(R.id.barber_name)
            val rating = v.findViewById<AppCompatRatingBar>(R.id.barber_rating)
            val pfpUrl = v.findViewById<AppCompatImageView>(R.id.barber_image)
            // TODO - load image via glide
            name.text = barberCard.barberName
            rating.rating = barberCard.userRating.toFloat()
        }
    }
}
