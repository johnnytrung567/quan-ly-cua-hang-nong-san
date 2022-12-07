package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
	
	// Chức năng đăng nhập
		@RequestMapping("/login")
		public String viewLoginPage(Model model) {

			return "login";
		}

	// Chức năng đăng ký
	@RequestMapping("/register")
	public String viewRegisterPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return "register";
	}

	@RequestMapping(value = "/register-save", method = RequestMethod.POST)
	public String saveRegister(@Validated @ModelAttribute("user") User user, BindingResult result, Model model, @Param("confirmPassword") String confirmPassword) {
		if(!confirmPassword.equals(user.getPassword())) {
			model.addAttribute("confirmPassError", "Mật khẩu không trùng khớp");
		}
		
		if(result.hasErrors() || !confirmPassword.equals(user.getPassword())) {
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

	//Trang sản phẩm
		@RequestMapping("/products")
		public String viewProductsPage(Model model) {
			List<Product> listProducts = productService.listAll();
			model.addAttribute("listProducts", listProducts);
			
			List<Category> listCategory = categoryService.listAll();
			model.addAttribute("listCategory", listCategory);
			
			return "products";
		}
		
		@RequestMapping("/products/{id}")
		public String viewProductsByCatPage(Model model, @PathVariable int id) {
			List<Product> listProducts = productService.listProductsByCat(id);
			model.addAttribute("listProducts", listProducts);
			
			List<Category> listCategory = categoryService.listAll();
			model.addAttribute("listCategory", listCategory);
			
			return "products";
		}
		
		@RequestMapping("/product/{id}")
		public String viewDetailPage(Model model, @PathVariable int id) {
			Product product = productService.get(id);
			model.addAttribute("product", product);
			
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
	public String saveProduct(@Validated @ModelAttribute("product") Product product, BindingResult result, Model model, @RequestParam("imageFile") MultipartFile file, @RequestParam("editId") String id) {
		if(file.isEmpty()) {
			model.addAttribute("imgError", "Vui lòng chọn hình ảnh sản phẩm");
		}
		
		if(result.hasErrors()) {
			List<Category> listCategory = categoryService.listAll();
			model.addAttribute("listCategory", listCategory);
			return "add-product";
		}
		
		String oriFileName = file.getOriginalFilename();
		
		// Cập nhật sản phẩm
		if(!id.equals("")) {
			Product oldProduct = productService.get(Long.parseLong(id));
			
			product.setId(Long.parseLong(id));
			product.setImage(oldProduct.getImage());
			
			if(!oriFileName.equals("")) {
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
		
		if(!oriFileName.equals("")) {
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
	
//	@RequestMapping("/edit/{id}")
//	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
//		ModelAndView mav = new ModelAndView("edit_product");
//		Product product = productService.get(id);
//		mav.addObject("product", product);
//
//		return mav;
//	}

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
}
