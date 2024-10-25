package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository) {

        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
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

    public void handleAddProductToCart(long id) {
        // id được lấy từ url
    }
}
