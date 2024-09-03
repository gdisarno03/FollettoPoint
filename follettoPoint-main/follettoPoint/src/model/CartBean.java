package model;

import java.util.ArrayList;
import java.util.List;

public class CartBean {

	private List<ProductCartBean> products;
	
	public CartBean() {
		products = new ArrayList<>();
	}
	
	public void addProduct(ProductBean product, int q) {
		for(ProductCartBean prod : products) {
			if(prod.getProduct().getCode() == product.getCode()) {
				if(prod.getQuantityCart() + q <= prod.getProduct().getQuantity())
					prod.setQuantityCart(prod.getQuantityCart()+q);
				return;
				}
		}
		products.add(new ProductCartBean(product, q));
	}
	
	public void deleteProduct(ProductBean product) {
		for(ProductCartBean prod : products) {
			if(prod.getProduct().getCode() == product.getCode()) {
					products.remove(prod);
			break;
			}
		}
 	}
	
	public List<ProductCartBean> getProducts() {
		return  products;
	}

}
