package com.shopmall.config;

import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Swagger 配置
 *
 * @author xiao
 * @data 2024/3/19 22:30
 */
public class SwaggerConfiguration {
    /**
     * 对C端用户接口文档
     */
    public Docket webApiDoc(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("用户端接口文档")
                .pathMapping("/")
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shopmall"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .globalRequestParameters(globalRequestParameters())
                .globalResponses(HttpMethod.GET,getGlobalResponseMessage())
                .globalResponses(HttpMethod.POST,getGlobalResponseMessage());
    }




    public Docket adminApiDoc(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("管理端接口文档")
                .pathMapping("/")
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shopmall"))
                .paths(PathSelectors.ant("/admin/**"))
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("test电商平台")
                .description("微服务接口文档")
                .contact(new Contact("xiaozhigang","https://com.shopmall","微信 17719442576"))
                .version("v1.0")
                .build();
    }

    private List<RequestParameter> globalRequestParameters() {
        return Collections.singletonList(new RequestParameterBuilder()
                .name("token")
                .description("登录命令")
                .in(ParameterType.HEADER)
                .query(o->o.model(m->m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build()
        );
    }

    private List<Response> getGlobalResponseMessage() {
        return Collections.singletonList(new ResponseBuilder()
                .code("4xx")
                .description("请求错误，根据code和msg检查")
                .build());
    }
}
