package com.antoniosoftware.pettracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private var list: MutableList<Pet>) : RecyclerView.Adapter<PetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PetViewHolder(inflater,parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet : Pet = list[position]
        holder.mMenuImageView?.setOnClickListener {
            showPopupMenu(holder.mMenuImageView!!,position)
        }
        holder.bind(pet)
    }

    fun showPopupMenu(view: View,position: Int){
        val popupMenu: PopupMenu = PopupMenu(view.context,view)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId){
                R.id.item1_select -> {
                    var previousSelectedPet : Pet? = list.find { pet -> pet.isSelected }
                    previousSelectedPet?.isSelected = false
                    list[position].isSelected = true
                    this.notifyDataSetChanged()
                    true
                }
                R.id.item2_add_tracking_data -> {
                    Toast.makeText(view.context,"Data added",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item3_delete -> {
                    list.removeAt(position)
                    this.notifyItemRemoved(position)
                    true
                }
                else -> false
            }
        }
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.show()
    }
}

class PetViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item,parent,false)) {
    private var mNameView: TextView? = null
    private var mSelectedView: TextView? = null
    var mMenuImageView: ImageView? = null

    init {
        mNameView = itemView.findViewById(R.id.text_view_pet_name)
        mSelectedView = itemView.findViewById(R.id.text_view_selected)
        mMenuImageView = itemView.findViewById(R.id.image_view_menu)
    }

    fun bind(pet: Pet){
        mNameView?.text = pet.name
        if (pet.isSelected)
            mSelectedView?.visibility = View.VISIBLE
        else mSelectedView?.visibility = View.INVISIBLE
    }

}