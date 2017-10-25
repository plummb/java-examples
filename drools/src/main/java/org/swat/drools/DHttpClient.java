/*
 * Copyright Â© 2017 Swatantra Agrawal. All rights reserved.
 */

package org.swat.drools;

import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

/**
 * @author Swatantra Agrawal
 * on 29-Aug-2017
 */
public class DHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DHttpClient.class);
    private static final String TLS_VERSION = "TLSv1.2";
    private volatile static SSLConnectionSocketFactory DEFAULT_APACHE_SSL_FACTORY;
    private volatile static SocketConfig DEFAULT_SOCKET_CONFIG;


    private DHttpClient() {
    }

    /**
     * This MUST be the default Apache Client that is to be used.
     *
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getDefaultApacheClient() {
        //Ensure  TLSv1.2 for outbound communication
        SSLConnectionSocketFactory factory = getSslConnectionSocketFactory();

        SocketConfig socketConfig = getConfiguredSocketConfig();

        HttpClientBuilder builder = HttpClients.custom().setSSLSocketFactory(factory) // Ensure SSL is set of TLSv1.2
                .setDefaultSocketConfig(socketConfig); // Ensure Socket Properties is set correctly


        return builder.build();
    }

    /**
     * @return SSLConnectionSocketFactory
     */
    private synchronized static SSLConnectionSocketFactory getSslConnectionSocketFactory() {
        if (null == DEFAULT_APACHE_SSL_FACTORY) {
            SSLContext sslContext = SSLContexts.createSystemDefault();
            DEFAULT_APACHE_SSL_FACTORY = new SSLConnectionSocketFactory(sslContext, new String[]{TLS_VERSION}, null, null);
        }
        return DEFAULT_APACHE_SSL_FACTORY;
    }


    /**
     * @return SocketConfig as configured through the Properties files.
     */
    private static synchronized SocketConfig getConfiguredSocketConfig() {
        if (null == DEFAULT_SOCKET_CONFIG) {
            final boolean keepAlive = true; // Keeps the Socket alive
            final boolean tcpNoDelay = true; // Sends data over socket as soon as it is written to stream
            final int readTimeout = 10 * 1000; // Timeout waiting for a response
            final int lingerTimeout = 10; // 10 seconds
            LOGGER.info(String
                    .format("The properties of SocketConfig {SoKeepAlive=%b, TcpNoDelay=%b, SoTimeout=%d milliseconds, SoLinger=%d seconds }", keepAlive, tcpNoDelay, readTimeout, lingerTimeout));
            DEFAULT_SOCKET_CONFIG =
                    SocketConfig.custom().setSoKeepAlive(keepAlive).setTcpNoDelay(tcpNoDelay).setSoTimeout(readTimeout)
                            .setSoLinger(lingerTimeout).build();
        }
        return DEFAULT_SOCKET_CONFIG;
    }
}
