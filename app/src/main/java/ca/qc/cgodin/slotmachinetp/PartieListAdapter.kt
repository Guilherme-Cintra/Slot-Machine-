package ca.qc.cgodin.slotmachinetp;

import android.content.Context
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class PartieListAdapter(context: Context
) : RecyclerView.Adapter<PartieListAdapter.PartieViewHolder>(){


    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var parties = emptyList<Partie>()



    inner class PartieViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

        val background: ConstraintLayout = itemView.findViewById(R.id.background)
        val img1: ImageView = itemView.findViewById(R.id.imageView)
        val img2: ImageView = itemView.findViewById(R.id.imageView2)
        val img3: ImageView = itemView.findViewById(R.id.imageView3)
        val mise: TextView = itemView.findViewById(R.id.miseView)
        val gain: TextView = itemView.findViewById(R.id.gainOuPerte)
        val solde: TextView = itemView.findViewById(R.id.nouvSolde)
        val noPArtie: TextView = itemView.findViewById(R.id.numberId)
        val modeNormal: TextView = itemView.findViewById(R.id.normalModeId)
        val modeCasseCou: TextView = itemView.findViewById(R.id.dareId)
        val gainTextView: TextView = itemView.findViewById(R.id.gainResult)
        val lossTextView: TextView = itemView.findViewById(R.id.lossResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartieViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_item, parent, false)
        return PartieViewHolder(itemView)
    }

    override fun getItemCount() = parties.size

    fun setParties(parties: List<Partie>){
        this.parties = parties

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PartieViewHolder, position: Int) {


        val current = parties[position]
        holder.background.setBackgroundColor(Color.parseColor(current.color))
        holder.img1.setImageResource(current.image1)
        holder.img2.setImageResource(current.image2)
        holder.img3.setImageResource(current.image3)
        holder.gain.text = current.prix
        holder.mise.text = current.mise
        holder.solde.text = current.nouveauSolde
        holder.noPArtie.text = current.id.toString()

        if (current.mode == "normal") {
            holder.modeNormal.visibility = View.VISIBLE
            holder.modeCasseCou.visibility = View.GONE
        } else if (current.mode == "cassecou"){
            holder.modeCasseCou.visibility = View.VISIBLE
            holder.modeNormal.visibility = View.GONE
        }

        //Le texte view va apparaítre dépendamment de la couleur. Rouge réprésente une défait tandis que vert c'est un gain
        if (current.color == "#FA8072") {
            holder.lossTextView.visibility = View.VISIBLE
            holder.gainTextView.visibility = View.GONE
        } else if(current.color == "#00FFFF") {
            holder.lossTextView.visibility = View.GONE
            holder.gainTextView.visibility = View.VISIBLE
        }
    }

}