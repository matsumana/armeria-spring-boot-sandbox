package info.matsumana.armeria.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.Server;

import info.matsumana.armeria.TestContext;
import info.matsumana.armeria.bean.handler.HelloResponse;

@SpringJUnitConfig(TestContext.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE,
        properties = "centraldogma.server.host=")
public class IntegrationTest4 {

    private static final ObjectReader objectReader = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .readerFor(new TypeReference<HelloResponse>() {});

    @Autowired
    private Server server;

    private WebClient client;

    @BeforeEach
    public void beforeEach() {
        final int port = server.activeLocalPort();
        client = WebClient.of("http://127.0.0.1:" + port);
    }

    @Test
    public void healthCheck() throws Exception {
        final AggregatedHttpResponse res = client.get("/internal/healthcheck").aggregate().join();
        assertThat(res.status()).isEqualTo(HttpStatus.OK);
        assertThat(res.content().toStringUtf8()).isEqualTo("{\"healthy\":true}");
    }

    @Test
    public void docs() throws Exception {
        final AggregatedHttpResponse res = client.get("/internal/docs/").aggregate().join();
        assertThat(res.status()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void metrics() throws Exception {
        final AggregatedHttpResponse res = client.get("/internal/metrics").aggregate().join();
        assertThat(res.status()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void hello() throws Exception {
        final AggregatedHttpResponse res = client.get("/hello/bar").aggregate().join();
        assertThat(res.status()).isEqualTo(HttpStatus.OK);

        final String json = res.content().toStringUtf8();
        final HelloResponse response = objectReader.readValue(json);
        assertThat(response.getMessage()).isEqualTo("Hello, bar");
    }
}
