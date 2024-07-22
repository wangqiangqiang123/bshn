package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration // 标明是配置类
@EnableSwagger2 // 开启swagger功能
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)  // DocumentationType.SWAGGER_2 固定的，代表swagger2
                //.groupName("分布式任务系统") // 如果配置多个文档的时候，那么需要配置groupName来分组标识
                .apiInfo(apiInfo()) // 用于生成API信息
                .select() // select()函数返回一个ApiSelectorBuilder实例,用来控制接口被swagger做成文档
                // 扫描指定包下的接口，最为常用
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                //.withClassAnnotation(RestController.class) // 扫描带有指定注解的类下所有接口
                //.withMethodAnnotation(PostMapping.class) // 扫描带有指定注解的方法接口
                //.apis(RequestHandlerSelectors.any()) // 扫描所有

                // 选择所有的API,如果你想只为部分API生成文档，可以配置这里
                .paths(PathSelectors.any()
                        //.any() // 满足条件的路径，该断言总为true
                        //.none() // 不满足条件的路径，该断言总为false（可用于生成环境屏蔽 swagger）
                        //.ant("/user/**") // 满足字符串表达式路径
                        //.regex("") // 符合正则的路径
                )
                .build().globalOperationParameters(getParamList());

    }

    private List<Parameter> getParamList(){
        ParameterBuilder pb=new ParameterBuilder();
        List<Parameter> list=new ArrayList<>();
        pb.name("Authorization").description("登录或注册不需要token验证,其他接口需要token进行验证").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();
        list.add(pb.build());
        return list;
    }

    /**
     * 用于定义API主界面的信息，比如可以声明所有的API的总标题、描述、版本
     * @return
     */
    private ApiInfo apiInfo() {

        Contact contact = new Contact(
                "王强强", // 作者姓名
                "https://github.com/wangqiangqiang123/bshn/tree/master", // 作者网址
                "hnwangqq_666@163.com"); // 作者邮箱

        return new ApiInfoBuilder()
                .title("图书管理系统API") //  可以用来自定义API的主标题
                .description("图书管理系统SwaggerAPI管理") // 可以用来描述整体的API
                .termsOfServiceUrl("https://github.com/wangqiangqiang123/bshn/tree/master") // 用于定义服务的域名（跳转链接）
                .version("1.0") // 可以用来定义版本
                .license("图书管理系统")
                .licenseUrl("https://github.com/wangqiangqiang123/bshn/tree/master")
                .contact(contact)
                .build(); //
    }
}
