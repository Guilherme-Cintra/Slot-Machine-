package ca.qc.cgodin.slotmachinetp

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_partie")
data class Partie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "image1")
    val image1: Int,
    @ColumnInfo(name = "image2")
    val image2: Int,
    @ColumnInfo(name = "image3")
    val image3: Int,
    @ColumnInfo(name = "mise")
    val mise: String,
    @ColumnInfo(name = "prix")
    val prix: String,
    @ColumnInfo(name = "nouveau_solde")
    val nouveauSolde: String,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "mode")
    val mode: String
)
