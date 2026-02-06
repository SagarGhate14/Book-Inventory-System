package com.cg.dto;

public class CartItem {
	
	 private int bookId;
	    private String title;
	    private double price;
	    private int quantity;
	    
	    public CartItem() {
	    	
	    }

		public CartItem(int bookId, String title, double price) {
			super();
			this.bookId = bookId;
			this.title = title;
			this.price = price;
		}

		public int getBookId() {
			return bookId;
		}

		public void setBookId(int bookId) {
			this.bookId = bookId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	    
	    

}
