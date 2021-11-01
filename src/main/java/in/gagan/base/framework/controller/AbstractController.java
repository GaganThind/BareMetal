package in.gagan.base.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

/**
 * This abstract controller class provides the common functionality.
 * 
 * @author gaganthind
 *
 */
@RestController
public abstract class AbstractController implements BaseController {

	@Autowired
	protected MessageSource message;
	
	@Autowired
	protected LocaleResolver localeResolver;
	
}
