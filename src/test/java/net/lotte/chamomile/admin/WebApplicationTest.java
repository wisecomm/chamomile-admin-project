package net.lotte.chamomile.admin;

import java.util.Map;

import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import net.lotte.chamomile.module.security.jwt.provider.ChamomileHazelcastTokenManager;
import net.lotte.chamomile.module.security.jwt.provider.ChamomileTokenManager;
import net.lotte.chamomile.module.security.jwt.provider.JwtAuthInterface;
import net.lotte.chamomile.module.security.jwt.vo.JwtRequest;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebApplicationTest {

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	private JwtAuthInterface jwtAuthInterface;

	protected MockHttpServletRequestBuilder getRequestBuilder(String url) throws Exception {
		return MockMvcRequestBuilders
				.get(url)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
				.contentType(MediaType.APPLICATION_JSON);
	}


	protected MockHttpServletRequestBuilder postRequestBuilder(String url) throws Exception {
		return MockMvcRequestBuilders
				.post(url)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
				.contentType(MediaType.APPLICATION_JSON);
	}

	private String getAccessToken() throws Exception {
		Map<String, String> token = jwtAuthInterface.authenticateToken(new JwtRequest("chmm23", "1111"));
		return token.get("accessToken");
	}

	@Configuration
	public static class TestConfig {
		@Bean
		public DataSource dataSource() {
			return new EmbeddedDatabaseBuilder()
					.setName("acceptanceTest")
					.setType(EmbeddedDatabaseType.H2)
					.setScriptEncoding("UTF-8")
					.ignoreFailedDrops(true)
					.addScript("schema.sql")
					.build();
		}

        @Bean
        public HazelcastInstance hazelcastInstance() {
            Config config = new Config();
            config.getScheduledExecutorConfig( "tokens" )
                    .setPoolSize ( 16 )
                    .setCapacity( 10000 )
                    .setDurability( 6 );
            return Hazelcast.newHazelcastInstance(config);
        }
        @Bean
        public ChamomileTokenManager chamomileTokenManager(HazelcastInstance hazelcastInstance) {
            return new ChamomileHazelcastTokenManager(hazelcastInstance);
        }
	}
}
