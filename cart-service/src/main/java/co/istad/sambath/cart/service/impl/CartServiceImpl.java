package co.istad.sambath.cart.service.impl;

import co.istad.sambath.cart.client.ProductClient;
import co.istad.sambath.cart.client.dto.ResponseProduct;
import co.istad.sambath.cart.model.Cart;
import co.istad.sambath.cart.model.CartItemEntity;
import co.istad.sambath.cart.model.dto.AddToCartRequest;
import co.istad.sambath.cart.repository.CartRepository;
import co.istad.sambath.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Override
    @Transactional(readOnly = true)
    public Cart getCart(String userId, String userEmail) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    log.info("🛒 Creating new cart for user: {}", userId);
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setUserEmail(userEmail);
                    return cartRepository.save(newCart);
                });
    }

    @Override
    @Transactional
    public Cart addToCart(String userId, String userEmail, AddToCartRequest request) {

        log.info("🔍 Fetching product from Product service: {}", request.getUuid());
        ResponseProduct product = productClient.findById(request.getUuid());

        if (product == null) {
            log.error("❌ Product not found: {}", request.getUuid());
            throw new RuntimeException("Product not found with ID: " + request.getUuid());
        }

        log.info("✅ Product found: {} - {} - Price: {}",
                product.uuid(), product.productName(), product.price());

        Cart cart = getCart(userId, userEmail);

        Optional<CartItemEntity> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getUuid()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update existing item quantity
            CartItemEntity item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            log.info("📦 Updated quantity for product {} to {}", request.getUuid(), item.getQuantity());
        } else {
            // Create new cart item with ALL required fields
            CartItemEntity newItem = new CartItemEntity();
            newItem.setProductId(product.uuid());
            newItem.setProductName(product.productName());
            newItem.setPrice(product.price());
            newItem.setQuantity(request.getQuantity());

            cart.addItem(newItem);
            log.info("➕ Added new product to cart: {} x{}", product.productName(), request.getQuantity());
        }

        Cart savedCart = cartRepository.save(cart);
        log.info("💾 Cart saved with {} items, total: {}",
                savedCart.getTotalItems(), savedCart.getTotal());

        return savedCart;
    }

    @Override
    @Transactional
    public Cart updateQuantity(String userId, String productId, Integer quantity) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        Optional<CartItemEntity> item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst();

        if (item.isPresent()) {
            if (quantity <= 0) {
                cart.removeItem(item.get());
                log.info("🗑️ Removed product {} from cart", productId);
            } else {
                item.get().setQuantity(quantity);
                log.info("🔄 Updated product {} quantity to {}", productId, quantity);
            }
        } else {
            throw new RuntimeException("Product not found in cart: " + productId);
        }

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart removeFromCart(String userId, String productId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        if (!removed) {
            throw new RuntimeException("Product not found in cart: " + productId);
        }

        log.info("❌ Removed product {} from cart", productId);

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
        log.info("🧹 Cleared cart for user {}", userId);
    }
}