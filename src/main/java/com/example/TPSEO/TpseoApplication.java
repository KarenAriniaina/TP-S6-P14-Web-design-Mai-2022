package com.example.TPSEO;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class TpseoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpseoApplication.class, args);
    }

//    @Bean
//    public FilterRegistrationBean<GzipFilter> gzipFilter() {
//        FilterRegistrationBean<GzipFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new GzipFilter());
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }
//
//    @Order
//    private static class GzipFilter implements Filter {
//
//        @Override
//        public void doFilter(ServletRequest request, ServletResponse response,
//                FilterChain chain)
//                throws IOException, ServletException {
//            HttpServletRequest httpRequest = (HttpServletRequest) request;
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//            // Check if the client accepts Gzip encoding
//            String encoding = httpRequest.getHeader("Accept-Encoding");
//            if (encoding != null && encoding.contains("gzip")) {
//                // Wrap the response with a GzipResponseWrapper
//                GzipResponseWrapper wrappedResponse = new GzipResponseWrapper(httpResponse);
//
//                // Call the next filter in the chain
//                chain.doFilter(request, wrappedResponse);
//
//                // Get the content length and Gzip the output stream
//                byte[] responseBytes = wrappedResponse.getResponseData();
//                httpResponse.setHeader("Content-Encoding", "gzip");
//                httpResponse.setHeader("Content-Length", Integer.toString(responseBytes.length));
//                ServletOutputStream outputStream = httpResponse.getOutputStream();
//                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
//                gzipOutputStream.write(responseBytes);
//                gzipOutputStream.close();
//            } else {
//                // Client does not accept Gzip encoding, call the next filter in the chain
//                chain.doFilter(request, response);
//            }
//        }
//
//        @Override
//        public void destroy() {
//            // Cleanup code
//        }
//
//        private static class GzipResponseWrapper extends HttpServletResponseWrapper {
//
//            private ServletOutputStream outputStream;
//            private GZIPOutputStream gzipOutputStream;
//
//            public GzipResponseWrapper(HttpServletResponse response) {
//                super(response);
//            }
//
//            @Override
//            public ServletOutputStream getOutputStream() throws IOException {
//                if (gzipOutputStream == null) {
//                    // Create a new GzipOutputStream and wrap it with a ServletOutputStream
//                    gzipOutputStream = new GZIPOutputStream(super.getOutputStream());
//                    outputStream = new ServletOutputStream() {
//                        @Override
//                        public void write(int b) throws IOException {
//                            gzipOutputStream.write(b);
//                        }
//
//                        @Override
//                        public boolean isReady() {
//                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                        }
//
//                        @Override
//                        public void setWriteListener(WriteListener listener) {
//                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                        }
//                    };
//                }
//                return outputStream;
//            }
//
//            public byte[] getResponseData() throws IOException {
//                if (gzipOutputStream != null) {
//                    // Flush and close the GzipOutputStream
//                    gzipOutputStream.finish();
//                    gzipOutputStream.close();
//                }
//                return super.getOutputStream().toString().getBytes();
//            }
//        }
//    }

}
