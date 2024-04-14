package com.arrowcode.domain.di

import com.arrowcode.domain.usecase.GetChallengeDetailsUseCase
import com.arrowcode.domain.usecase.GetCompletedChallengesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetCompletedChallengesUseCase(get())
    }

    factory {
        GetChallengeDetailsUseCase(get())
    }
}