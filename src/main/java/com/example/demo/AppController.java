package com.example.demo;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping("/")
	public String viewHomePage(Model model) {
//		List<Product> listProducts = productService.listAll();
//		model.addAttribute("listProducts", listProducts);

		return "index";
	}

	// Chức năng đăng ký
	@RequestMapping("/register")
	public String viewLoginPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return "register";
	}

	@RequestMapping(value = "/register/save", method = RequestMethod.POST)
	public String saveRegister(@ModelAttribute("user") User user) {
//		User user = new User();
//		user.setAddress("HCM");
//		user.setEmail("sfa@gmail.com");
//		user.setFullname("Tuan");
//		user.setPhone("0123213123");
//		user.setPassword("123456");
		int strength = 10; // work factor of bcrypt
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		Role customeRole = roleService.get(1);
		user.setRole(customeRole);
		userService.save(user);
		return "redirect:/login";
	}

	//Trang sản phẩm
		@RequestMapping("/product")
		public String viewProductPage(Model model) {
			List<Product> listProducts = productService.listAll();
			model.addAttribute("listProducts", listProducts);
			
			List<Category> listCategory = categoryService.listAll();
			model.addAttribute("listCategory", listCategory);
			
			return "product";
		}
		
		@RequestMapping("/detail")
		public String viewDetailPage(Model model) {
			/*
			 * List<Product> listProducts = productService.listAll();
			 * model.addAttribute("listProducts", listProducts);
			 */
			
			return "detail";
		}

	@RequestMapping("/cart")
	public String viewCartPage(Model model) {
//		List<Product> listProducts = productService.listAll();
//		model.addAttribute("listProducts", listProducts);

		return "cart";
	}

	// Chức năng của quản lý
	@RequestMapping("/admin")

	public String viewAdminPage(Model model) {
		List<User> listCustomers = userService.listUsers(1, 1);
		List<User> listStaffs = userService.listUsers(3, 4);
		model.addAttribute("listCustomers", listCustomers);
		model.addAttribute("listStaffs", listStaffs);

		return "admin";
	}

	// Chức năng của nhân viên kho
	@RequestMapping("/storehouse")
	public String viewStorehousePage(Model model) {
		List<Product> listProducts = productService.listAll();
		model.addAttribute("listProducts", listProducts);

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
//		Product product = new Product();
//		model.addAttribute("product", product);

		return "add-product";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		productService.save(product);

		return "redirect:/";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_product");
		Product product = productService.get(id);
		mav.addObject("product", product);

		return mav;
	}

	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		productService.delete(id);
		return "redirect:/";
	}
}
