package io.github.astro;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author WenBo Zhou
 * @Date 2023/9/26 10:34
 */
@RestController
public class MyController {

    @Resource
    MyExport export;

    @RequestMapping("/hello/{word}")
    public String hello(@PathVariable("word") String word) {
        String hello = export.hello(word);
        System.out.println(hello);
        return hello;
    }
}
