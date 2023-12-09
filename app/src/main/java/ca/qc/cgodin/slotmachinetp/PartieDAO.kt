package ca.qc.cgodin.slotmachinetp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PartieDAO {
    //prendre toutes es parties
    @Query("SELECT * FROM table_partie")
    fun getParties() : LiveData<List<Partie>>

    //Supprimer la base de données
    @Query("DELETE FROM table_partie")
    fun deleteParties()

    //Insère dans la BD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(partie: Partie)
}