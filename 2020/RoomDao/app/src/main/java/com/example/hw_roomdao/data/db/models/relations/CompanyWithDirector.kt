package com.example.hw_roomdao.data.db.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.hw_roomdao.data.db.models.Company
import com.example.hw_roomdao.data.db.models.CompanyContract
import com.example.hw_roomdao.data.db.models.Director
import com.example.hw_roomdao.data.db.models.DirectorContract

// One to One
data class CompanyWithDirector(
    @Embedded val company: Company,
    @Relation(
        parentColumn = CompanyContract.Columns.ID,
        entityColumn = DirectorContract.Columns.ID
    )
    val director: Director
)
