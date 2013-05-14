package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.mime.MimeType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerContext {

    private boolean isRendered;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private MimeType mimeType;

    public ControllerContext(HttpServletRequest request, HttpServletResponse response, MimeType mimeType) {
        this.request = request;
        this.response = response;
        this.mimeType = mimeType;
    }

    public boolean isRendered() {
        return isRendered;
    }

    public void setRendered(boolean rendered) {
        isRendered = rendered;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void redirect(String url) {
        String contextPath = request.getContextPath();
        String realPath = contextPath.equals("") ? url : contextPath + url;
        try {
            response.sendRedirect(realPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRendered = true;
    }

    public void string(String text) {
        response.setContentType("text/plain");
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRendered = true;
    }

    public void render(int StatusCode) throws IOException {
        response.sendError(StatusCode);
    }
}
