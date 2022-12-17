package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.bill.BillService;
import com.example.demo.billDetails.BillDetailsService;
import com.example.demo.cart.CartService;
import com.example.demo.category.CategoryService;
import com.example.demo.product.ProductService;
import com.example.demo.role.RoleService;
import com.example.demo.user.UserService;

@WebMvcTest(value = AppController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class UserControllerTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ProductService productService;
	@MockBean
	private CategoryService categoryService;
	@MockBean
	private UserService userService;
	@MockBean
	private RoleService roleService;
	@MockBean
	private CartService cartService;
	@MockBean
	private BillService billService;
	@MockBean
	private BillDetailsService billDetailsService;

	@Test
	void shouldCreateUser() throws Exception {
		this.mockMvc
				.perform(post("/register-save").param("fullname", "duke").param("email", "test@gmail.com")
						.param("address", "Thanh pho hcm").param("phone", "012809128").param("password", "123456")
						.param("confirmPassword", "123456"))
				.andExpect(status().is3xxRedirection()).andExpect(header().string("Location", "/login?register"));
	}

	@Test
	@WithMockUser
	void shouldLoginUser() throws Exception {
		this.mockMvc.perform(post("/login").param("email", "test@gmail.com").param("password", "123456"))
				.andExpect(status().is3xxRedirection()).andExpect(header().string("Location", "/"));
	}

}
