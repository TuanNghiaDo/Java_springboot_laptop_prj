package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
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

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        // id được lấy từ url
        // check xem user đã có giỏ hàng chưa? chưa có thì tạo mới
        User user = this.userService.getUserByEmail(email);
        Cart cart = this.cartRepository.findByUser(user);
        if (cart == null) {
            // tạo mới cart
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setQuantity(0);

            cart = this.cartRepository.save(newCart);
        }
        // lưu cart_detail
        // tìm product theo id
        Product product = this.productRepository.findById(productId);
        // check xem product đã có trong giỏ hàng chưa?
        CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
        if (oldDetail == null) {
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setPrice(product.getPrice());
            cartDetail.setQuantity(1);
            this.cartDetailRepository.save(cartDetail);

            // update số lượng sản phẩm trong giỏ hàng, tăng quantity của cart lên 1
            int s = cart.getQuantity() + 1;
            cart.setQuantity(s);
            this.cartRepository.save(cart);
            session.setAttribute("sumProductInCart", s);
        }
        // nếu có trong giỏ hàng rồi thì tăng số lượng lên 1
        else {
            oldDetail.setQuantity(oldDetail.getQuantity() + 1);
            this.cartDetailRepository.save(oldDetail);
        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public CartDetail fetchCartDetailByCartAndProduct(Cart cart, Product product) {
        return this.cartDetailRepository.findByCartAndProduct(cart, product);
    }
}
