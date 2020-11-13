package kled.test.controller.mvc_test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author: Kled
 * @version: BeanUrlController.java, v0.1 2020-11-08 16:31 Kled
 */
@Controller
@RequestMapping(value = "/test")
public class ForwardAndReDirectController{

    @Autowired
    private FlashMapManager flashMapManager;

    @RequestMapping(value = "/forward")
    public ModelAndView forwardRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//        httpServletRequest.setAttribute("type", "forward_default");
//        //默认会使用转发
//        return "index";
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("type", "forward_default");
        return mav;
    }

    @RequestMapping(value = "/forward1")
    public ModelAndView forwardRequest1(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //转发至forward2, url、request、response不变
        ModelAndView mav = new ModelAndView("forward:forward2");
        mav.addObject("type", "forward1");
        return mav;
    }

    @RequestMapping(value = "/forward2")
    public String forwardRequest2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return "index";
    }

    @RequestMapping(value = "/redirect1")
    public ModelAndView redirectRequest1(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //转发至forward2, url、request、response发生变化
        //参数默认会以requestParam的形式传递, redirect2?type=redirect1
//        FlashMap flashMap = RequestContextUtils.getOutputFlashMap(httpServletRequest);
//        flashMapManager.saveOutputFlashMap(flashMap, httpServletRequest, httpServletResponse);
        ModelAndView mav = new ModelAndView("redirect:redirect2");
        mav.addObject("type", "redirect1");
        return mav;
    }

    @RequestMapping(value = "/redirect2")
    public String redirectRequest2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        System.out.println(httpServletRequest.getAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE));
        return "index";
    }
}
