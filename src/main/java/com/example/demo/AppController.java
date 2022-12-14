package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.Year;
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

import com.example.demo.bill.Bill;
import com.example.demo.bill.BillService;
import com.example.demo.billDetails.BillDetails;
import com.example.demo.billDetails.BillDetailsService;
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
	private BillDetailsService billDetailsService;

	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		List<Product> listProducts = productService.listAll();

		// L???y ng???u nhi??n 4 s???n ph???m
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

	// Ch???c n??ng ????ng nh???p
	@RequestMapping("/login")
	public String viewLoginPage(Model model) {

		// Ch???n v??o login sau khi ???? login
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}

		return "redirect:/";
	}

	// Ch???c n??ng ????ng k??
	@RequestMapping("/register")
	public String viewRegisterPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		// Ch???n v??o register sau khi ???? login
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "register";
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/register-save", method = RequestMethod.POST)
	public String saveRegister(@Validated @ModelAttribute("user") User user, BindingResult result, Model model,
			@Param("confirmPassword") String confirmPassword) {
		// Ki???m tra email ???? ???????c s??? d???ng ch??a
		List<User> listusers = userService.listAll();
		boolean emailExists = listusers.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()));
		if (emailExists) {
			model.addAttribute("emailExistsError", "Email n??y ???? ???????c s??? d???ng");
		}

		if (!confirmPassword.equals(user.getPassword())) {
			model.addAttribute("confirmPassError", "M???t kh???u kh??ng tr??ng kh???p");
		}

		if (result.hasErrors() || !confirmPassword.equals(user.getPassword()) || emailExists) {
			return "register";
		}

		int strength = 10; // work factor of bcrypt
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		Role customeRole = roleService.get(1);
		user.setRole(customeRole);
		userService.save(user);
		return "redirect:/login?register";
	}

	// Trang s???n ph???m
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

	// Trang chi ti???t s???n ph???m
	@RequestMapping("/product/{id}")
	public String viewDetailPage(Model model, @PathVariable int id) {
		Product product = productService.get(id);
		model.addAttribute("product", product);

		// L???y ra c??c s???n ph???m t????ng t???
		int categoryId = Math.toIntExact(product.getCategory().getId());
		List<Product> similarProducts = productService.listProductsByCat(categoryId);
		similarProducts.removeIf(p -> (p.getId().equals(product.getId())));

		// L???y ng???u nhi??n 4 s???n ph???m
		Collections.shuffle(similarProducts);
		int randomSeriesLength = 4;
		List<Product> randomSimilarProducts = similarProducts.subList(0, randomSeriesLength);

		model.addAttribute("similarProducts", randomSimilarProducts);

		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);

		return "detail";
	}

	// Trang gi??? h??ng
	@RequestMapping("/cart")
	public String viewCartPage(Model model) {
		getCartInfo(model);

		return "cart";
	}

	// Ch???c n??ng th??m s???n ph???m v??o gi??? h??ng
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

		if (existId != null) { // S???n ph???m ???? c?? trong gi??? h??ng
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

	// Ch???c n??ng c???p nh???t s??? l?????ng s???n ph???m trong gi??? h??ng
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

	// Ch???c n??ng x??a s???n ph???m trong gi??? h??ng
	@RequestMapping("/cart/delete/{id}")
	public String deleteCartPage(Model model, @PathVariable int id) {
		cartService.delete(Math.toIntExact(id));

		return "redirect:/cart";
	}

	// Ch???c n??ng thanh to??n
	@RequestMapping("/checkout")
	public String viewCheckoutPage(Model model) {
		getCartInfo(model);

		Bill bill = new Bill();
		model.addAttribute("bill", bill);

		return "thanhtoan";
	}

	@RequestMapping(value = "/checkout-save", method = RequestMethod.POST)
	public String saveCheckoutPage(@Validated @ModelAttribute("bill") Bill bill, BindingResult result, Model model,
			@RequestParam("total") int total) {
		if (result.hasErrors()) {
			getCartInfo(model);
			return "thanhtoan";
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userService.findByUsername(userDetails.getUsername());

		bill.setTotal(total);
		bill.setCustomer(user);

		// T???o h??a ????n
		Bill newBill = billService.save(bill);

		List<Cart> listCart = cartService.listCartItemsByCustomer(Math.toIntExact(user.getId()));

		for (Cart cart : listCart) {
			// L??u s???n ph???m v??o chi ti???t h??a ????n
			BillDetails billDetail = new BillDetails();
			billDetail.setBill(newBill);
			billDetail.setProduct(cart.getProduct());
			billDetail.setQuantity(cart.getQuantity());
			billDetailsService.save(billDetail);

			// Tr??? s???n ph???m trong kho
			Product product = productService.get(cart.getProduct().getId());
			product.setQuantity(product.getQuantity() - cart.getQuantity());
			productService.save(product);

			// X??a s???n ph???m kh???i gi??? h??ng
			cartService.delete(cart.getId());
		}

		return "redirect:/history";
	}

	// Ch???c n??ng xem th??ng tin c?? nh??n
	@RequestMapping("/profile")
	public String viewProfilePage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));

		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);

		return "profile";
	}

	// Ch???c n??ng xem l???ch s??? mua h??ng
	@RequestMapping("/history")
	public String viewHistoryPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userService.findByUsername(userDetails.getUsername());

		int cartTotalQuantity = getCartTotalQuantity();
		model.addAttribute("cartTotalQuantity", cartTotalQuantity);

		List<Bill> bills = billService.listBillsByCustomer(Math.toIntExact(user.getId()));
		model.addAttribute("bills", bills);

		// L???y ra c??c s???n ph???m trong t???ng bill
		for (Bill bill : bills) {
			List<BillDetails> billDetails = billDetailsService.listBillDetailsByBill(Math.toIntExact(bill.getId()));
			model.addAttribute("billDetails" + bill.getId(), billDetails);
		}

		return "history";
	}

	// Ch???c n??ng ?????i m???t kh???u
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
			model.addAttribute("blankOldPass", "M???t kh???u kh??ng ???????c ????? tr???ng");
		} else if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
			model.addAttribute("oldPassError", "Sai m???t kh???u c??");
		}

		if (newPassword.equals("")) {
			model.addAttribute("blankNewPass", "M???t kh???u kh??ng ???????c ????? tr???ng");
		} else if (newPassword.equals(oldPassword)) {
			model.addAttribute("newPassError", "M???t kh???u m???i kh??ng ???????c tr??ng m???t kh???u c??");
		}

		if (confirmPassword.equals("")) {
			model.addAttribute("blankConfirmPass", "M???t kh???u kh??ng ???????c ????? tr???ng");
		} else if (!confirmPassword.equals(newPassword)) {
			model.addAttribute("confirmPassError", "M???t kh???u kh??ng tr??ng kh???p");
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

	// Ch???c n??ng ch???nh s???a th??ng tin c?? nh??n
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
			model.addAttribute("blankFullname", "Kh??ng ???????c ????? tr???ng th??ng tin n??y");
		}
		if (phone.equals("")) {
			model.addAttribute("blankPhone", "Kh??ng ???????c ????? tr???ng th??ng tin n??y");
		}
		if (address.equals("")) {
			model.addAttribute("blankAddress", "Kh??ng ???????c ????? tr???ng th??ng tin n??y");
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

	// =====CH???C N??NG C???A QU???N L??=====
	@RequestMapping("/admin")

	public String viewAdminPage(Model model) {
		List<User> listCustomers = userService.listUsers(1, 1);
		List<User> listStaffs = userService.listUsers(3, 4);
		List<Role> listRole = roleService.listAll();
		listRole.remove(1);
		model.addAttribute("listCustomers", listCustomers);
		model.addAttribute("listStaffs", listStaffs);
		model.addAttribute("listRole", listRole);

		return "admin";
	}

	@RequestMapping("/admin/{id}")
	public String viewDetailUserPage(Model model, @PathVariable int id) {
		User user = userService.get(id);
		;
		model.addAttribute("user", user);

		return "detail-user";
	}

	@RequestMapping("/add-staff")
	public String showAddStaffPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		List<Role> listRole = roleService.listAll();
		listRole.remove(0);
		listRole.remove(0);
		model.addAttribute("listRole", listRole);

		return "add-staff";
	}

	@RequestMapping(value = "/save-staff", method = RequestMethod.POST)
	public String saveStaff(@Validated @ModelAttribute("user") User user, BindingResult result, Model model) {
		List<User> listUser = userService.listAll();
		boolean flag = false;
		for (int i = 0; i < listUser.size(); i++) {
			if (listUser.get(i).getEmail().equals(user.getEmail())) {
				model.addAttribute("staffError", "Email ???? t???n t???i");
				flag = true;
			}
		}
		if (result.hasErrors() || flag == true) {
			List<Role> listRole = roleService.listAll();
			listRole.remove(0);
			listRole.remove(0);
			model.addAttribute("listRole", listRole);

			return "add-staff";
		}

		int strength = 10; // work factor of bcrypt
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
//			System.out.println(user.getPassword());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // password ???????c ?????t m???c ?????nh l?? 123456 khi
																			// t???o nh??n vi??n m???i
//			System.out.println(user.getPassword());

		userService.save(user);

		return "redirect:/admin";
	}

	@RequestMapping("admin/change-staffRole/{id}")
	public String viewChangeStaffRole(Model model, @PathVariable int id) {
		User user = userService.get(id);
		model.addAttribute("user", user);
		List<Role> listRole = roleService.listAll();
		listRole.remove(0);
		listRole.remove(0);
		model.addAttribute("listRole", listRole);

		return "change-staffRole";
	}

	@RequestMapping(value = "admin/save-roleChange", method = RequestMethod.POST)
	public String saveStaffRoleChange(Model model, @RequestParam("editId") String id,
			@RequestParam("role") String role) {

		User user = userService.get(Long.parseLong(id));
		user.setRole(roleService.get(Long.parseLong(role)));
		userService.save(user);

		return "redirect:/admin";
	}

	@RequestMapping("/delete-user/{id}")
	public String deleteUser(@PathVariable(name = "id") int id) {

		userService.delete(id);
		return "redirect:/admin";
	}

	// ======CH???C N??NG C???A NH??N VI??N KHO=====
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
			model.addAttribute("imgError", "Vui l??ng ch???n h??nh ???nh s???n ph???m");
		}

		// Ki???m tra t??n s???n ph???m ???? t???n t???i
		List<Product> listproducts = productService.listAll();
		boolean titleExists = listproducts.stream().anyMatch(p -> p.getTitle().equals(product.getTitle()));
		if (titleExists) {
			model.addAttribute("titleExistsError", "S???n ph???m n??y ???? t???n t???i");
		}

		if (result.hasErrors()) {
			List<Category> listCategory = categoryService.listAll();
			model.addAttribute("listCategory", listCategory);
			if (!id.equals("")) {
				product.setId(Long.parseLong(id));
				model.addAttribute("product", product);
			}
			return "add-product";
		}

		String oriFileName = file.getOriginalFilename();

		// C???p nh???t s???n ph???m
		if (!id.equals("")) {
			Product oldProduct = productService.get(Long.parseLong(id));

			product.setId(Long.parseLong(id));
			product.setImage(oldProduct.getImage());

			if (!oriFileName.equals("")) {
				// X??a ???nh c??
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

	// Qu???n l?? danh m???c
	@RequestMapping("/add-category")
	public String showAddCategoryPage(Model model) {

		Category category = new Category();
		model.addAttribute("category", category);

		return "add-category";
	}

	@RequestMapping(value = "/save-category", method = RequestMethod.POST)
	public String saveCategory(@Validated @ModelAttribute("category") Category category, BindingResult result,
			Model model, @RequestParam("editId") String id) {
		List<Category> listCategory = categoryService.listAll();
		boolean flag = false;
		for (int i = 0; i < listCategory.size(); i++) {
			if (listCategory.get(i).getName().equals(category.getName())) {
				model.addAttribute("categoryError", "Danh m???c ???? t???n t???i");
				flag = true;
			}
		}

		if (result.hasErrors() || flag == true) {
			if (!id.equals("")) {
				category.setId(Long.parseLong(id));
				model.addAttribute("category", category);
			}
			return "add-category";
		}

		// C???p nh???t danh m???c
		if (!id.equals("")) {

			category.setId(Long.parseLong(id));

		}

		categoryService.save(category);

		return "redirect:/storehouse";
	}

	@RequestMapping("/edit-category/{id}")
	public String editCategory(Model model, @PathVariable(name = "id") int id) {
		Category category = categoryService.get(id);
		model.addAttribute("category", category);

		return "add-category";
	}

	@RequestMapping("/delete-category/{id}")
	public String deleteCategory(@PathVariable(name = "id") int id) {

		categoryService.delete(id);
		return "redirect:/storehouse";
	}

	// =====CH???C N??NG C???A K??? TO??N=====
	@RequestMapping("/accountant")

	public String viewAccountantPage(Model model) {
		List<Bill> bills = billService.listAll();
		model.addAttribute("bills", bills);

		// L???y ra c??c s???n ph???m trong t???ng bill
		for (Bill bill : bills) {
			List<BillDetails> billDetails = billDetailsService.listBillDetailsByBill(Math.toIntExact(bill.getId()));
			model.addAttribute("billDetails" + bill.getId(), billDetails);
		}

		return "accountant";
	}

	// Th???ng k?? doanh thu
	@RequestMapping("/accountant/statistic")
	public String viewstatisticProduct(Model model) {

		return "statistic";
	}

	@RequestMapping(value = "/statistic-save", method = RequestMethod.POST)
	public String statisticProduct(Model model, @RequestParam("period") String period) {
		String unit = period.split(" ")[0];
		int time = Integer.parseInt(period.split(" ")[1]);

		Integer total = 0;
		int year = Year.now().getValue();

		switch (unit) {
		case "year":
			total = billService.statisticByYear(time);
			break;
		case "quarter":
			total = billService.statisticByQuarter(year, time);
			break;
		case "month":

			total = billService.statisticByMonth(year, time);
			break;

		default:
			break;
		}

		total = total == null ? 0 : total;

		model.addAttribute("total", total);
		model.addAttribute("period", period);

		return "statistic";
	}

	// L???y ra s??? l?????ng s???n ph???m ??ang c?? trong gi??? h??ng
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

	// L???y to??n b??? th??ng tin gi??? h??ng
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
