package ca.qc.cgodin.slotmachinetp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database
    (entities = arrayOf(Partie::class), version = 1, exportSchema = false)
abstract class PartieRoomDatabase: RoomDatabase() {
    abstract fun partieDAO(): PartieDAO

    companion object{
        //Annotation qui previent multiples instances de la BD ouvrant en même temps
        @Volatile
        private var INSTANCE: PartieRoomDatabase? = null

        fun getDatabase(context: Context): PartieRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PartieRoomDatabase::class.java,
                "Machine_database"
            ).build()
            return INSTANCE as PartieRoomDatabase
        }
    }
}