package ca.qc.cgodin.slotmachinetp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PartieViewModelFactory(private val application: Application): ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(PartieViewModel::class.java)){
            PartieViewModel(application) as T
        } else{
            throw IllegalArgumentException("Unkown ViewModel Class")
        }
    }
}
