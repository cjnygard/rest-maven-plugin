package com.github.cjnygard.mvn.rest;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

public class FollowRedirectFilter implements ClientResponseFilter
{
    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException
    {
        if (responseContext.getStatusInfo().getFamily() != Response.Status.Family.REDIRECTION)
            return;

        Response resp = requestContext.getClient().target(responseContext.getLocation()).request().method(requestContext.getMethod());

        responseContext.setEntityStream((InputStream) resp.getEntity());
        responseContext.setStatusInfo(resp.getStatusInfo());
        responseContext.setStatus(resp.getStatus());
    }
}
