package com.arrowcode.view.di

import com.arrowcode.view.detailsscreen.DetailsViewModel
import com.arrowcode.view.listscreen.ListViewModel
import org.koin.dsl.module

val viewModule = module {

    factory { (challengeId: String) -> DetailsViewModel(challengeId, get()) }
    factory { ListViewModel(get()) }
}