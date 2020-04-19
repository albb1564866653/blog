package com.cjh.blog;

//import com.cjh.blog.interceptor.LoginInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableTransactionManagement//开启事务管理
@SpringBootApplication
@MapperScan("com.cjh.blog.dao")
public class BlogApplication implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {//注册 拦截器
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/*")
//                //排除不拦截的地址
//                .excludePathPatterns("/admin")
//                .excludePathPatterns("/admin/login");
//    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
