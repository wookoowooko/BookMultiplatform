package io.wookoo.bookapp.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.wookoo.bookapp.book.data.network.IRemoteBookDataSource
import io.wookoo.bookapp.book.data.network.KtorRemoteBookDataSource
import io.wookoo.bookapp.book.data.repository.MasterRepository
import io.wookoo.bookapp.book.database.construct.DatabaseFactory
import io.wookoo.bookapp.book.database.construct.FavoriteBookDatabase
import io.wookoo.bookapp.book.domain.IBookRepository
import io.wookoo.bookapp.book.presentation.features.SelectedBookViewModel
import io.wookoo.bookapp.book.presentation.features.bookdetail.mvi.BookDetailViewModel
import io.wookoo.bookapp.book.presentation.features.booklist.mvi.BookListViewModel
import io.wookoo.bookapp.core.data.HttpClientFactory

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(engine = get()) }
    singleOf(::KtorRemoteBookDataSource).bind<IRemoteBookDataSource>()
    singleOf(::MasterRepository).bind<IBookRepository>()
    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single {
        get<FavoriteBookDatabase>().favoriteBookDao()
    }
}