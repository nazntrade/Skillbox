package ru.ktsstudio.roomdao.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import ru.ktsstudio.roomdao.data.db.MIGRATION_1_2
import ru.ktsstudio.roomdao.data.db.SkillboxDatabase
import ru.ktsstudio.roomdao.data.db.UserDao
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): SkillboxDatabase {
        Timber.d("providesDatabase")
        return Room.databaseBuilder(
            application,
            SkillboxDatabase::class.java,
            SkillboxDatabase.DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun providesUserDao(db: SkillboxDatabase): UserDao {
        Timber.d("providesUserDao")
        return db.userDao()
    }

}