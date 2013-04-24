package testutil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

public class MockHttpServletResponse implements HttpServletResponse{
    OutputStream outputStream;
    PrintWriter writer;

    public MockHttpServletResponse() {
        outputStream = new ByteArrayOutputStream();
        writer = new PrintWriter(outputStream);
    }

    public String getResponse() {
        return outputStream.toString();
    }

    @Override
    public void addCookie(Cookie cookie) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean containsHeader(String name) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String encodeURL(String url) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String encodeRedirectURL(String url) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String encodeUrl(String url) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String encodeRedirectUrl(String url) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendError(int sc) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDateHeader(String name, long date) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addDateHeader(String name, long date) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setHeader(String name, String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addHeader(String name, String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setIntHeader(String name, int value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addIntHeader(String name, int value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setStatus(int sc) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setStatus(int sc, String sm) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCharacterEncoding() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getContentType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void setCharacterEncoding(String charset) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setContentLength(int len) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setContentType(String type) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBufferSize(int size) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getBufferSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void flushBuffer() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resetBuffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isCommitted() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setLocale(Locale loc) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Locale getLocale() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
