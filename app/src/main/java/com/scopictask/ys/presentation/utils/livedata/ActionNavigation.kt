package com.scopictask.ys.presentation.utils.livedata

sealed class ActionNavigation {
    data object ToSignUpFragment : ActionNavigation()
    data object ToSignInFragment : ActionNavigation()
    data object ToWelcomeFragment : ActionNavigation()
    data object ToListFragment : ActionNavigation()
    data object ToProfileFragment : ActionNavigation()
    data object LogOut : ActionNavigation()
}