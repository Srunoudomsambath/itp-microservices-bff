package co.istad.sambath.cart.controller;
import co.istad.sambath.cart.client.dto.ResponseProduct;
import co.istad.sambath.cart.model.Cart;
import co.istad.sambath.cart.model.dto.AddToCartRequest;
import co.istad.sambath.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 🛒 Get current user's cart
     */
    @GetMapping
    public ResponseEntity<Cart> getMyCart(
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject(); // 🔑 standard user id
        String email = jwt.getClaimAsString("email");

        log.info("📥 Fetching cart for userId={}, email={}", userId, email);

        return ResponseEntity.ok(cartService.getCart(userId, email));
    }

    /**
     * ➕ Add product to cart
     */
    @PostMapping
    public ResponseEntity<Cart> addToCart(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody AddToCartRequest request
    ) {
        String userId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");

        log.info("➕ Add to cart userId={}, productId={}, qty={}",
                userId, request.getUuid(), request.getQuantity());

        return ResponseEntity.ok(
                cartService.addToCart(userId, email, request)
        );
    }

    /**
     * 🔄 Update quantity
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Cart> updateQuantity(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String productId,
            @RequestParam Integer quantity
    ) {
        String userId = jwt.getSubject();

        log.info("🔄 Update quantity userId={}, productId={}, qty={}",
                userId, productId, quantity);

        return ResponseEntity.ok(
                cartService.updateQuantity(userId, productId, quantity)
        );
    }

    /**
     * ❌ Remove product from cart
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Cart> removeFromCart(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String productId
    ) {
        String userId = jwt.getSubject();

        log.info("❌ Remove product userId={}, productId={}", userId, productId);

        return ResponseEntity.ok(
                cartService.removeFromCart(userId, productId)
        );
    }

    /**
     * 🧹 Clear cart
     */
    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        log.info("🧹 Clear cart for userId={}", userId);

        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
