package cz.cvut.fel.ear.ear_project.config

import cz.cvut.fel.ear.ear_project.service.ProjectService
import cz.cvut.fel.ear.ear_project.service.StoryService
import org.springframework.aop.Advisor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Role
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class MethodSecurityConfig {
    @Autowired
    private lateinit var projectService: ProjectService

    @Autowired
    private lateinit var storyService: StoryService

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    fun preAuthorize(): Advisor {
        return AuthorizationManagerBeforeMethodInterceptor.preAuthorize()
    }

    /*
    @Bean
    fun cmseh(): CustomMethodSecurityExpressionRoot {
        return CustomMethodSecurityExpressionRoot(projectService, storyService)
    }

     */
}
