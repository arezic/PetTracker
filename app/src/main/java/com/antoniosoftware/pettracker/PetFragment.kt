package com.antoniosoftware.pettracker

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_pet.*


data class Pet(var name: String, var isSelected: Boolean = false)

class PetFragment : Fragment() {

    private var addPetDialog: Dialog? = null

    public var petListForTesting : MutableList<Pet> = mutableListOf(
       Pet("Rocky"),
       Pet("Miu",true),
       Pet("Šiljo"),
       Pet("Luna"),
       Pet("Miki")
   )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_pets.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(petListForTesting)
        }

        button_add_pet.setOnClickListener {
            addPetDialog = Dialog(this.requireContext())          //maybe change to context property
            showPopup()
        }
    }

    private fun showPopup(){
        addPetDialog!!.setContentView(R.layout.layout_dialog)
        val buttonAddPet: Button = addPetDialog!!.findViewById(R.id.button_dialog_add)
        buttonAddPet.setOnClickListener {
            /*val intent = Intent(this,PlayActivity::class.java)
            startActivity(intent)*/
            val editTextPetName : EditText = addPetDialog!!.findViewById(R.id.edit_text_pet_name)
            val pet = Pet(editTextPetName.text.toString())
            petListForTesting.add(pet)
            recycler_view_pets.adapter?.notifyItemInserted(petListForTesting.size - 1)

            /*val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false)
            }
            ft.detach(this).attach(this).commit()*/
            addPetDialog!!.dismiss()
        }
        addPetDialog!!.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(): PetFragment = PetFragment()
    }
}