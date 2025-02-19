package io.wookoo.bookapp.di

import io.wookoo.bookapp.core.data.HttpClientFactory
import io.wookoo.bookapp.features.book.data.network.IRemoteBookDataSource
import io.wookoo.bookapp.features.book.data.network.KtorRemoteBookDataSource
import io.wookoo.bookapp.features.book.data.repository.MasterRepository
import io.wookoo.bookapp.features.book.domain.IBookRepository
import io.wookoo.bookapp.features.book.presentation.booklist.mvi.BookListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule : Module

val sharedModule = module {
    single { HttpClientFactory.create(engine = get()) }
    singleOf(::KtorRemoteBookDataSource).bind<IRemoteBookDataSource>()
    singleOf(::MasterRepository).bind<IBookRepository>()
    viewModelOf(::BookListViewModel)
}