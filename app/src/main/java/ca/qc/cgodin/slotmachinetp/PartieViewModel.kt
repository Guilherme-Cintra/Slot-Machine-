package ca.qc.cgodin.slotmachinetp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PartieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PartieRepository

    val allParties: LiveData<List<Partie>>





    init {
        val partieDAO =
            PartieRoomDatabase.getDatabase(application).partieDAO()
        repository = PartieRepository(partieDAO)
        allParties = repository.allParties
    }

    fun insert(partie: Partie) = viewModelScope.launch(Dispatchers.IO) { repository.insert(partie)
    }

    fun deleteEverything() =viewModelScope.launch(Dispatchers.IO){repository.deleteAll()}

}