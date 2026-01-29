package com.freedom.quilterandroidtechtest

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
@Serializable
sealed interface AppDestination : NavKey {
    @Serializable
    data object BookList : AppDestination

}