package com.flaringapp.smadlab6.presentation.activities.navigation

interface AppNavigation {

    fun navigateTo(
        screen: Screen,
        data: Any? = null
    )

}