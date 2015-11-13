package com.example.mixin;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

/**
 * Created by mileslux on 11/13/2015.
 */
public final class JsonResponseSupportFactoryBean implements InitializingBean {
    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> handlers = Lists.newArrayList(adapter.getReturnValueHandlers());
        decorateHandlers(handlers);
        adapter.setReturnValueHandlers(handlers);
    }

    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for (HandlerMethodReturnValueHandler handler : handlers) {
            if (handler instanceof HttpEntityMethodProcessor) {
                JsonResponseEntityReturnValueHandler decorator = new JsonResponseEntityReturnValueHandler(handler);
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);
            } else if (handler instanceof RequestResponseBodyMethodProcessor) {
                JsonResponseBodyReturnValueHandler decorator = new JsonResponseBodyReturnValueHandler(handler);
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);
            }
        }
    }

}
