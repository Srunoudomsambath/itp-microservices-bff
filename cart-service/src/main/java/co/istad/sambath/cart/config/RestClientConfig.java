package co.istad.sambath.cart.config;


import co.istad.sambath.cart.client.ProductClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public ProductClient productClient(LoadBalancerClient loadBalancerClient) {

        RestClient restClient = RestClient.builder()
                // 🔑 SERVICE NAME (Eureka / Gateway aware)
                .baseUrl("http://localhost:8080")
                .requestInterceptor((request, body, execution) -> {

                    // 🔐 Propagate JWT
                    var authentication = SecurityContextHolder.getContext().getAuthentication();

                    if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                        String token = jwtAuth.getToken().getTokenValue();
                        request.getHeaders().setBearerAuth(token);
                    }

                    return execution.execute(request, body);
                })
                .build();

        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                        .build();

        return factory.createClient(ProductClient.class);
    }
}
