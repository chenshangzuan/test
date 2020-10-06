package kled.test.controller;


import kled.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kled
 * @since 2020-10-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "", produces = "application/json")
    public void test(){
        for (int i = 0; i < 5; i++) {
            System.out.println(userService.getById(1));
        }
    }

}
