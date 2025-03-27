package id.manstore.module.cart.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.manstore.core.util.Resource
import id.manstore.core.util.UiEvents
import id.manstore.module.cart.domain.use_case.AddCartItemsUseCase
import id.manstore.module.cart.domain.use_case.GetCartItemsUseCase
import id.manstore.module.product.domain.model.Product
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addCartItemsUseCase: AddCartItemsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CartItemsState())
    val state: State<CartItemsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getCartItems()
        }
    }

    private suspend fun getCartItems() {
        getCartItemsUseCase().collectLatest { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        cartItems = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "Unknown error occurred!"
                        )
                    )
                }
            }
        }
    }

    fun addCartItems(id: Int, products: List<Product>) {
        viewModelScope.launch {
            addCartItemsUseCase(id, products).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(isLoading = false)
                        _eventFlow.emit(UiEvents.SnackbarEvent("Added to cart successfully!"))
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false)
                        _eventFlow.emit(
                            UiEvents.SnackbarEvent(result.message ?: "Failed to add item!")
                        )
                    }
                }
            }
        }
    }
}