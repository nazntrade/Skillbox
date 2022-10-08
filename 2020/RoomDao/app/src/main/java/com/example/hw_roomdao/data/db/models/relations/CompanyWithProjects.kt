package com.example.hw_roomdao.data.db.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.hw_roomdao.data.db.models.Company
import com.example.hw_roomdao.data.db.models.CompanyContract
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.data.db.models.ProjectContract

// One to Many
data class CompanyWithProjects(
    @Embedded val company: Company,
    @Relation(
        parentColumn = CompanyContract.Columns.ID,
        entityColumn = ProjectContract.Columns.ID
    )
    val projects: List<Project>
)
