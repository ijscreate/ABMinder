package com.mtcdb.stem.mathtrix.calculator

import android.annotation.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.recyclerview.widget.*
import com.mtcdb.stem.mathtrix.R
import java.util.*

@Suppress("UNCHECKED_CAST")
class CalculationOptionAdapter(
    private val originalOptions : List<CalculationOption>,
    private val onItemClick : (CalculationOption) -> Unit,
) : RecyclerView.Adapter<CalculationOptionAdapter.ViewHolder>(), Filterable {

    private var filteredOptions : List<CalculationOption> = originalOptions

    override fun getFilter() : Filter {
        return object : Filter() {
            override fun performFiltering(constraint : CharSequence?) : FilterResults {
                val filterResults = FilterResults()
                val query = constraint.toString().lowercase(Locale.getDefault())

                val filteredList = if (query.isEmpty()) {
                    originalOptions
                } else {
                    originalOptions.filter { option ->
                        option.name.lowercase(Locale.getDefault()).contains(query)
                    }
                }

                filterResults.values = filteredList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint : CharSequence?, results : FilterResults?) {
                filteredOptions = results?.values as List<CalculationOption>
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textViewName : TextView = itemView.findViewById(R.id.tVCalculationOption)
        val textViewDescription : TextView =
            itemView.findViewById(R.id.tVCalculationOptionDescription)
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calculation_option, parent, false)
        val arrow = itemView.findViewById<ImageView>(R.id.arrow)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            arrow.setColorFilter(R.color.black)
        } else {
            arrow.setColorFilter(R.color.white)
        }

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        val option = filteredOptions[position]
        holder.textViewName.text = option.name
        holder.textViewDescription.text = option.description

        holder.itemView.setOnClickListener {
            onItemClick(option)
        }
    }

    override fun getItemCount() : Int = filteredOptions.size

}