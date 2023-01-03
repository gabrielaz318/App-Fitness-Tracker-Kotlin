package co.tiagoaguiar.fitnesstracker.model

import androidx.room.*

@Dao
interface CalcDao {

    // @Insert  -> Inserir
    // @Update  -> Atualizar
    // @Delete  -> Remover
    // Query    -> Buscar

    @Insert
    fun insert(calc: Calc)

    @Query("SELECT * FROM Calc WHERE type = :type")
    fun getRegisterByType(type: String) : List<Calc>
}