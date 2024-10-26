package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {

        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public Product handleSaveNewProduct(Product product) {
        Product sp = this.productRepository.save(product);
        return sp;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public void deleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    public Product getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void handleAddProductToCart(String email, long productId) {
        // id được lấy từ url
        // check xem user đã có giỏ hàng chưa? chưa có thì tạo mới
        User user = this.userService.getUserByEmail(email);
        Cart cart = this.cartRepository.findByUser(user);
        if (cart == null) {
            // tạo mới cart
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setQuantity(1);

            cart = this.cartRepository.save(newCart);
        }
        // lưu cart_detail
        // tìm product theo id
        Product product = this.productRepository.findById(productId);
        CartDetail cartDetail = new CartDetail();
        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        cartDetail.setPrice(product.getPrice());
        cartDetail.setQuantity(1);
        this.cartDetailRepository.save(cartDetail);
    }
}
