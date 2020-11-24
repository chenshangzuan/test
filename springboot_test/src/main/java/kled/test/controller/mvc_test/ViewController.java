package kled.test.controller.mvc_test;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Kled
 * @version: BeanUrlController.java, v0.1 2020-11-08 16:31 Kled
 */
@Controller
@RequestMapping(value = "/test")
public class ViewController {

    @RequestMapping(value = "/model")
    @ResponseBody
    public Model model(Model model) throws Exception {
        model.addAttribute("model", "dadaa");
        //DefaultRequestToViewNameTranslator根据访问的路径，提供默认逻辑视图名称
        //转发至视图test/model
        return model;
    }
}
