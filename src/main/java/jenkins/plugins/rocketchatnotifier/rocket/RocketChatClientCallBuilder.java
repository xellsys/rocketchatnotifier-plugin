package jenkins.plugins.rocketchatnotifier.rocket;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import hudson.ProxyConfiguration;
import jenkins.model.Jenkins;
import jenkins.plugins.rocketchatnotifier.model.Response;
import jenkins.plugins.rocketchatnotifier.utils.NetworkUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.SingleClientConnManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map.Entry;
import java.util.logging.Logger;

/**
 * The call builder for the {@link RocketChatClient} and is only supposed to be
 * used internally.
 *
 * @author Bradley Hilton (graywolf336)
 * @version 0.0.1
 * @since 0.1.0
 */
public class RocketChatClientCallBuilder {

  private static final Logger logger = Logger.getLogger(RocketChatClientCallBuilder.class.getName());

  private final ObjectMapper objectMapper;

  private String serverUrl;

  private final RocketChatCallAuthentication authentication;

  protected RocketChatClientCallBuilder(String serverUrl, boolean trustSSL, String user, String password) {
    this(new RocketChatBasicCallAuthentication(serverUrl, user, password), serverUrl, trustSSL);
  }

  protected RocketChatClientCallBuilder(String serverUrl, boolean trustSSL, String webhookToken) {
    this(new RocketChatWebhookAuthentication(serverUrl, webhookToken), serverUrl, trustSSL);
  }

  protected RocketChatClientCallBuilder(RocketChatCallAuthentication authentication, String serverUrl,
                                        boolean trustSSL) {
    this.authentication = authentication;
    this.serverUrl = serverUrl;

    if (Jenkins.getInstance() != null && Jenkins.getInstance().proxy != null
      && !NetworkUtils.isHostOnNoProxyList(this.serverUrl, Jenkins.getInstance().proxy)) {
      final HttpClientBuilder clientBuilder = HttpClients.custom();
      final ProxyConfiguration proxy = Jenkins.getInstance().proxy;
      final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
      final HttpHost proxyHost = new HttpHost(proxy.name, proxy.port);
      final HttpRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyHost);

      clientBuilder.setRoutePlanner(routePlanner);
      clientBuilder.setDefaultCredentialsProvider(credentialsProvider);

      String username = proxy.getUserName();
      String password = proxy.getPassword();
      // Consider it to be passed if username specified. Sufficient?
      if (username != null && !"".equals(username.trim())) {
        logger.info("Using proxy authentication (user=" + username + ")");
        credentialsProvider.setCredentials(new AuthScope(proxyHost),
          new UsernamePasswordCredentials(username, password));
        Unirest.setHttpClient(clientBuilder.build());
      }
      else {
        Unirest.setProxy(proxyHost);
      }
    }
    else {
      Unirest.setHttpClient(createHttpClient(trustSSL));
    }
    this.objectMapper = new ObjectMapper();
    this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }


  protected Response buildCall(RocketChatRestApiV1 call) throws IOException {
    return this.buildCall(call, null, null);
  }

  protected Response buildCall(RocketChatRestApiV1 call, RocketChatQueryParams queryParams) throws IOException {
    return this.buildCall(call, queryParams, null);
  }

  protected Response buildCall(RocketChatRestApiV1 call, RocketChatQueryParams queryParams, Object body)
    throws IOException {
    if (call.requiresAuth() && !authentication.isAuthenticated()) {
      authentication.doAuthentication();
    }

    switch (call.getHttpMethod()) {
      case GET:
        return this.buildGetCall(call, queryParams);
      case POST:
        return this.buildPostCall(call, queryParams, body);
      default:
        throw new IOException("Http Method " + call.getHttpMethod().toString() + " is not supported.");
    }
  }

  private Response buildGetCall(RocketChatRestApiV1 call, RocketChatQueryParams queryParams) throws IOException {
    GetRequest req = Unirest.get(authentication.getUrlForRequest(call));

    if (call.requiresAuth()) {
      authentication.addAuthenticationDataToRequest(req);
    }

    if (queryParams != null && !queryParams.isEmpty()) {
      for (Entry<? extends String, ? extends String> e : queryParams.get().entrySet()) {
        req.queryString(e.getKey(), e.getValue());
      }
    }

    try {
      HttpResponse<String> res = req.asString();

      return objectMapper.readValue(res.getBody(), Response.class);
    }
    catch (UnirestException e) {
      throw new IOException(e);
    }
  }

  private Response buildPostCall(RocketChatRestApiV1 call, RocketChatQueryParams queryParams, Object body)
    throws IOException {
    HttpRequestWithBody req = Unirest.post(authentication.getUrlForRequest(call)).header("Content-Type",
      "application/json");

    if (call.requiresAuth()) {
      authentication.addAuthenticationDataToRequest(req);
    }

    if (queryParams != null && !queryParams.isEmpty()) {
      for (Entry<? extends String, ? extends String> e : queryParams.get().entrySet()) {
        req.queryString(e.getKey(), e.getValue());
      }
    }

    if (body != null) {
      req.body(objectMapper.writeValueAsString(body));
    }

    try {
      HttpResponse<String> res = req.asString();

      return objectMapper.readValue(res.getBody(), Response.class);
    }
    catch (UnirestException e) {
      throw new IOException(e);
    }
  }

  private HttpClient createHttpClient(boolean trustSSL) {
    if (!trustSSL) {
      return new DefaultHttpClient();
    }

    try {
      SSLContext sslContext = SSLContext.getInstance("SSL");

      // set up a TrustManager that trusts everything
      sslContext.init(null, new TrustManager[]{new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
          // intentionally left blank
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
          // intentionally left blank
        }
      }}, new SecureRandom());

      SSLSocketFactory sf = new SSLSocketFactory(sslContext);
      Scheme httpsScheme = new Scheme("https", 443, sf);
      SchemeRegistry schemeRegistry = new SchemeRegistry();
      schemeRegistry.register(httpsScheme);

      // apache HttpClient version >4.2 should use BasicClientConnectionManager
      ClientConnectionManager cm = new SingleClientConnManager(schemeRegistry);
      HttpClient httpClient = new DefaultHttpClient(cm);
      return httpClient;
    }
    catch (Exception e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }
}
