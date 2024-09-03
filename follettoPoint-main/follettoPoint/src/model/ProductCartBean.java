package model;



public class ProductCartBean {
	//attributes
	ProductBean product;
	int quantityCart;

	//methods
	public ProductCartBean(){
		product = new ProductBean();
		quantityCart = 0;
	}
	
	public ProductCartBean(ProductBean p, int quantity) {
		product  = p;
		quantityCart = quantity;
	}

	public ProductBean getProduct() {
		return product;
	}
	
	public void setProduct(ProductBean p) {
		product = p;
	}
	
	public int getQuantityCart() {
		return quantityCart;
	}
	
	public void setQuantityCart(int q) {
		quantityCart = q;
	}
}
