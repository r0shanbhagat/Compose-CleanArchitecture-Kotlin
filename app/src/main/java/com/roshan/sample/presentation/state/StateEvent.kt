package com.roshan.sample.presentation.state

/**
 * Movie state event.
 *
 * @constructor Create empty constructor for movie state event
 */
sealed class StateEvent {
    /**
     * Get movie list
     *
     * @constructor
     */
    data class ProductDetails(val productId: String) : StateEvent()

    /**
     * None
     *
     * @constructor Create empty None
     */
    data object None : StateEvent()
}