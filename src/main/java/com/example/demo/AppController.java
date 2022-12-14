package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bill.Bill;
import com.example.demo.bill.BillService;
import com.example.demo.billDetail.BillDetailService;
import com.example.demo.cart.Cart;
import com.example.demo.cart.CartService;
import com.example.demo.category.Category;
import com.example.demo.category.CategoryService;
import com.example.demo.product.Product;
import com.example.demo.product.ProductService;
import com.example.demo.role.Role;
import com.example.demo.role.RoleService;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

@Controller
public class AppController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CartService cartService;
	@Autowired
	private BillService billService;
	@Autowired
	private BillDetailService billDetailService;

	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		List<Product> listProducts = productService.listAll();

		// Lấy ngẫu nhiên 4 sản phẩm
		Collections.shuffle(listProducts);
		int randomSeriesLength = 4;
		int randomSeriesLength2 = 8;

		List<Product> randomProductsOne = listProducts.subList(0, randomSeriesLength);
		List<Product> randomProductsTwo = listProducts.subList(randomSeriesLength, randomSeriesLength2);

		model.addAttribute("randomProductsOne", randomProductsOne);
		model.addAttribute("randomProductsTwo", randomProductsTwo);

		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);

		return "index";
	}

	// Chức năng đăng nhập
	@RequestMapping("/login")
	public String viewLoginPage(Model model) {

		// Chặn vào login sau khi đã login
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}

		return "redirect:/";
	}

	// Chức năng đăng ký
	@RequestMapping("/register")
	public String viewRegisterPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		// Chặn vào register sau khi đã login
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "register";
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/register-save", method = RequestMethod.POST)
	public String saveRegister(@Validated @ModelAttribute("user") User user, BindingResult result, Model model,
			@Param("confirmPassword") String confirmPassword) {
		if (!confirmPassword.equals(user.getPassword())) {
			model.addAttribute("confirmPassError", "Mật khẩu không trùng khớp");
		}

		if (result.hasErrors() || !confirmPassword.equals(user.getPassword())) {
			return "register";
		}

		int strength = 10; // work factor of bcrypt
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		Role customeRole = roleService.get(1);
		user.setRole(customeRole);
		userService.save(user);
		return "redirect:/login";
	}

	// Trang sản phẩm
	@RequestMapping("/products")
	public String viewProductsPage(Model model) {
		List<Product> listProducts = productService.listAll();
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("categoryId", 0);

		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);

		return "products";
	}

	@RequestMapping("/products/{id}")
	public String viewProductsByCatPage(Model model, @PathVariable int id) {
		List<Product> listProducts = productService.listProductsByCat(id);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("categoryId", id);

		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);

		return "products";
	}

	// Trang chi tiết sản phẩm
	@RequestMapping("/product/{id}")
	public String viewDetailPage(Model model, @PathVariable int id) {
		Product product = productService.get(id);
		model.addAttribute("product", product);

		// Lấy ra các sản phẩm tương tự
		int categoryId = Math.toIntExact(product.getCategory().getId());
		List<Product> similarProducts = productService.listProductsByCat(categoryId);
		similarProducts.removeIf(p -> (p.getId().equals(product.getId())));

		// Lấy ngẫu nhiên 4 sản phẩm
		Collections.shuffle(similarProducts);
		int randomSeriesLength = 4;
		List<Product> randomSimilarProducts = similarProducts.subList(0, randomSeriesLength);

		model.addAttribute("similarProducts", randomSimilarProducts);

		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);

		return "detail";
	}

	// Trang giỏ hàng
	@RequestMapping("/cart")
	public String viewCartPage(Model model) {
		getCartInfo(model);

		return "cart";
	}

	// Chức năng thêm sản phẩm vào giỏ hàng
	@RequestMapping("/cart/add/{productId}")
	public String addCartPage(HttpServletRequest request, Model model, @PathVariable int productId,
			@RequestParam(value = "quantity", required = false) Integer productQuantity) {
		int quantity = productQuantity != null ? productQuantity : 1;
		Product product = productService.get(productId);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userService.findByUsername(userDetails.getUsername());

		List<Cart> carts = cartService.listCartItemsByCustomer(Math.toIntExact(user.getId()));

		Long existId = null;
		for (Cart cart : carts) {
			if (Math.toIntExact(cart.getProduct().getId()) == productId) {
				existId = cart.getId();
				break;
			}
		}

		Cart cart;

		if (existId != null) { // Sản phẩm đã có trong giỏ hàng
			cart = cartService.get(existId);
			cart.setQuantity(cart.getQuantity() + quantity);
		} else {
			cart = new Cart();
			cart.setUser(user);
			cart.setProduct(product);
			cart.setQuantity(quantity);
		}

		cartService.save(cart);

		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	// Chức năng cập nhật số lượng sản phẩm trong giỏ hàng
	@RequestMapping("/cart/update/{id}/{action}")
	public String updateCartPage(HttpServletRequest request, Model model, @PathVariable int id,
			@PathVariable String action) {
		Cart cart = cartService.get(Math.toIntExact(id));

		if (action.equals("increase")) {
			cart.setQuantity(cart.getQuantity() + 1);
		} else {
			cart.setQuantity(cart.getQuantity() - 1);
		}

		cartService.save(cart);

		return "redirect:/cart";
	}

	// Chức năng xóa sản phẩm trong giỏ hàng
	@RequestMapping("/cart/delete/{id}")
	public String deleteCartPage(Model model, @PathVariable int id) {
		cartService.delete(Math.toIntExact(id));

		return "redirect:/cart";
	}

	// Chức năng thanh toán
	@RequestMapping("/checkout")
	public String viewCheckoutPage(Model model) {
		getCartInfo(model);

		Bill bill = new Bill();
		model.addAttribute("bill", bill);

		return "thanhtoan";
	}

	@RequestMapping(value = "/checkout-save", method = RequestMethod.POST)
	public String saveCheckoutPage(@Validated @ModelAttribute("bill") Bill bill, BindingResult result, Model model) {
		if (result.hasErrors()) {
			getCartInfo(model);
			return "thanhtoan";
		}

//		Bill newBill = billService.save(bill);

		return "redirect:/history";
	}

	// Chức năng xem thông tin cá nhân
	@RequestMapping("/profile")
	public String viewProfilePage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));

		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);

		return "profile";
	}

	// Chức năng xem lịch sử mua hàng
	@RequestMapping("/history")
	public String viewHistoryPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));

		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);

		return "history";
	}

	// Chức năng đổi mật khẩu
	@RequestMapping("/change-password")
	public String pageChangePassword(Model model) {
		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);
		
		return "change-password";
	}

	@RequestMapping(value = "/change-password-save", method = RequestMethod.POST)
	public String changePassword(@Validated Model model, @RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {

		/*
		 * System.out.println(oldPassword); System.out.println(newPassword);
		 * System.out.println(confirmPassword);
		 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userService.findByUsername(userDetails.getUsername());
//		System.out.println(userPassword);

		int strength = 10; // work factor of bcrypt
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

		if (oldPassword.equals("")) {
			model.addAttribute("blankOldPass", "Mật khẩu không được để trống");
		} else if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
			model.addAttribute("oldPassError", "Sai mật khẩu cũ");
		}

		if (newPassword.equals("")) {
			model.addAttribute("blankNewPass", "Mật khẩu không được để trống");
		} else if (newPassword.equals(oldPassword)) {
			model.addAttribute("newPassError", "Mật khẩu mới không được trùng mật khẩu cũ");
		}

		if (confirmPassword.equals("")) {
			model.addAttribute("blankConfirmPass", "Mật khẩu không được để trống");
		} else if (!confirmPassword.equals(newPassword)) {
			model.addAttribute("confirmPassError", "Mật khẩu không trùng khớp");
		}

//		System.out.println(bCryptPasswordEncoder.matches(oldPassword, user.getPassword()));

		if (oldPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")
				|| !confirmPassword.equals(newPassword) || newPassword.equals(oldPassword)
				|| !bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
			
			int cartTotalQuantity = getCartTotalQuantity();
			model.addAttribute("cartTotalQuantity", cartTotalQuantity);
			
			return "change-password";
		}

		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		userService.save(user);

		return "redirect:/logout";
	}

	// Chức năng chỉnh sửa thông tin cá nhân
	@RequestMapping("/change-userInfo")
	public String viewChangeProfilePage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));

		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);
		
		return "change-userInfo";
	}

	@RequestMapping(value = "/change-userInfo-save", method = RequestMethod.POST)
	public String changeUserInfo(Model model, @RequestParam("fullname") String fullname,
			@RequestParam("phone") String phone, @RequestParam("address") String address) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userService.findByUsername(userDetails.getUsername());

		if (fullname.equals("")) {
			model.addAttribute("blankFullname", "Không được để trống thông tin này");
		}
		if (phone.equals("")) {
			model.addAttribute("blankPhone", "Không được để trống thông tin này");
		}
		if (address.equals("")) {
			model.addAttribute("blankAddress", "Không được để trống thông tin này");
		}

		if (fullname.equals("") || phone.equals("") || address.equals("")) {
			model.addAttribute("user", user);
			
			int cartTotalQuantity = getCartTotalQuantity();
			model.addAttribute("cartTotalQuantity", cartTotalQuantity);
			
			return "change-userInfo";
		}

		user.setFullname(fullname);
		user.setPhone(phone);
		user.setAddress(address);
		userService.save(user);

		return "redirect:/profile";
	}

	// =====CHỨC NĂNG CỦA QUẢN LÝ=====
	@RequestMapping("/admin")

	public String viewAdminPage(Model model) {
		List<User> listCustomers = userService.listUsers(1, 1);
		List<User> listStaffs = userService.listUsers(3, 4);
		model.addAttribute("listCustomers", listCustomers);
		model.addAttribute("listStaffs", listStaffs);

		return "admin";
	}

	// ======CHỨC NĂNG CỦA NHÂN VIÊN KHO=====
	@RequestMapping("/storehouse")
	public String viewStorehousePage(Model model) {
		List<Product> listProducts = productService.listAll();
		model.addAttribute("listProducts", listProducts);

		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		return "storehouse";
	}

	@RequestMapping("/storehouse/{id}")
	public String viewDetailStorehousePage(Model model, @PathVariable int id) {

		Product product = productService.get(id);
		model.addAttribute("product", product);

		return "detail-storehouse";
	}

	@RequestMapping("/add-product")
	public String showAddProductPage(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);

		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		return "add-product";
	}

	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images";

	@RequestMapping(value = "/save-product", method = RequestMethod.POST)
	public String saveProduct(@Validated @ModelAttribute("product") Product product, BindingResult result, Model model,
			@RequestParam("imageFile") MultipartFile file, @RequestParam("editId") String id) {
		if (file.isEmpty()) {
			model.addAttribute("imgError", "Vui lòng chọn hình ảnh sản phẩm");
		}

		if (result.hasErrors()) {
			List<Category> listCategory = categoryService.listAll();
			model.addAttribute("listCategory", listCategory);
			return "add-product";
		}

		String oriFileName = file.getOriginalFilename();

		// Cập nhật sản phẩm
		if (!id.equals("")) {
			Product oldProduct = productService.get(Long.parseLong(id));

			product.setId(Long.parseLong(id));
			product.setImage(oldProduct.getImage());

			if (!oriFileName.equals("")) {
				// Xóa ảnh cũ
				Path path = Paths.get(UPLOAD_DIRECTORY + "/" + oldProduct.getImage().split("/")[2]);
				try {
					Files.deleteIfExists(path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (!oriFileName.equals("")) {
			StringBuilder fileNames = new StringBuilder();
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			product.setImage("/images/" + fileNames.toString());
		}

		productService.save(product);

		return "redirect:/storehouse";
	}

	@RequestMapping("/edit-product/{id}")
	public String editProduct(Model model, @PathVariable(name = "id") int id) {
		Product product = productService.get(id);
		model.addAttribute("product", product);

		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		return "add-product";
	}

	@RequestMapping("/delete-product/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		Product product = productService.get(id);
		Path path = Paths.get(UPLOAD_DIRECTORY + "/" + product.getImage().split("/")[2]);
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		productService.delete(id);
		return "redirect:/storehouse";
	}

	// =====CHỨC NĂNG CỦA KẾ TOÁN=====
	@RequestMapping("/accountant")

	public String viewAccountantPage(Model model) {

		return "accountant";
	}

	// Lấy ra số lượng sản phẩm đang có trong giỏ hàng
	public int getCartTotalQuantity() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		int totalQuantity;

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			User user = userService.findByUsername(userDetails.getUsername());
			List<Cart> listCart = cartService.listCartItemsByCustomer(Math.toIntExact(user.getId()));
			totalQuantity = listCart.stream().mapToInt(item -> item.getQuantity()).sum();
		} else {
			totalQuantity = 0;
		}

		return totalQuantity;
	}

	// Lấy toàn bộ thông tin giỏ hàng
	public void getCartInfo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userService.findByUsername(userDetails.getUsername());

		List<Cart> listCart = cartService.listCartItemsByCustomer(Math.toIntExact(user.getId()));
		model.addAttribute("listCart", listCart);

		int total = listCart.stream().mapToInt(item -> item.getQuantity() * item.getProduct().getPrice()).sum();
		model.addAttribute("total", total);

		int totalQuantity = listCart.stream().mapToInt(item -> item.getQuantity()).sum();
		model.addAttribute("totalQuantity", totalQuantity);
	}
}
