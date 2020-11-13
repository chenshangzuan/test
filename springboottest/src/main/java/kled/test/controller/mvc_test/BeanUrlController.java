package kled.test.controller.mvc_test;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author: Kled
 * @version: BeanUrlController.java, v0.1 2020-11-08 16:31 Kled
 */
@Component(value = "/beanUrl")
public class BeanUrlController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mav = new ModelAndView("welcome");
        mav.addObject("now", LocalDateTime.now().toString());
        String name = httpServletRequest.getParameter("name");
        mav.addObject("name", name == null ? "你是?" : name);
        return mav;
    }
}
