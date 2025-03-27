package id.manstore.module.product.domain.model

import id.manstore.R
import id.manstore.module.destinations.CartScreenDestination
import id.manstore.module.destinations.Destination
import id.manstore.module.destinations.HomeScreenDestination

sealed class BottomNavItem(var icon: Int, var destination: Destination) {
    data object Home : BottomNavItem(
        icon = R.drawable.ic_home,
        destination = HomeScreenDestination
    )

    data object Cart : BottomNavItem(
        icon = R.drawable.ic_basket,
        destination = CartScreenDestination
    )
}
