package ca.qc.cgodin.slotmachinetp

import androidx.lifecycle.LiveData

class PartieRepository(private val partieDAO: PartieDAO) {
    val allParties: LiveData<List<Partie>> = partieDAO.getParties()


    suspend fun insert(partie: Partie){
        partieDAO.insert(partie)
    }

    suspend fun deleteAll(){
        partieDAO.deleteParties()
    }
}