package in.gagan.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.controller.BaseController;

/**
 * Implementation class of the AbstractController. This class is just to load
 * 
 * @author gaganthind
 *
 */
@RestController
public class HomePageController implements BaseController {

	/**
	 * This method returns the home page
	 */
	@GetMapping("/load")
	public String loadHomePage() {
		return "Hello";
	}
	
}
