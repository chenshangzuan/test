package kled.test.controller.mvc_test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Kled
 * @version: BeanUrlController.java, v0.1 2020-11-08 16:31 Kled
 */
@Controller
@RequestMapping(value = "/test")
public class ModelInitController {

//    @ModelAttribute
//    //返回值绑定到指定的模型属性上，controller中的每个方法执行前执行
//    public void addModelType(@RequestParam String type, Model model) {
//        model.addAttribute("type", type + " from addModelType");
//    }

    @ModelAttribute("type")
    //作用同上
    public String getType(@RequestParam String type) {
        return type + " from getType";
    }

    @RequestMapping(value = "/modelAttribute1")
    public String modelAttributeTest1(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return "index";
    }

    @RequestMapping(value = "/modelAttribute2")
    @ResponseBody
    public String modelAttributeTest2(/*从视图中读取*/@ModelAttribute("type") String type) throws Exception {
        return type;
    }

//    @RequestMapping(value = "/modelAttribute3")
//    @ModelAttribute("type")
      //view 由RequestToViewNameTranslator解析？
      //model -> key=type value="111"
//    public String modelAttributeTest3() throws Exception {
//        return "111";
//    }

    //无法读取url type参数
//    @RequestMapping(value = "/modelAttribute4")
//    @ResponseBody
//    public String modelAttributeTest4(/*相当于Form或url中获取参数，可不注解*/@ModelAttribute String type) throws Exception {
//        return type;
//    }
}
