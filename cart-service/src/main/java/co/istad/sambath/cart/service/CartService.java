package co.istad.sambath.cart.service;


import co.istad.sambath.cart.model.Cart;
import co.istad.sambath.cart.model.dto.AddToCartRequest;

public interface CartService {

    Cart getCart(String userId, String userEmail);

    Cart addToCart(String userId, String userEmail, AddToCartRequest request);

    Cart updateQuantity(String userId, String productId, Integer quantity);

    Cart removeFromCart(String userId, String productId);

    void clearCart(String userId);
}
