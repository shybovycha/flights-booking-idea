package sources.exceptions;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.*;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;
import java.util.Map;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {
    private ExceptionHandler wrapped;

    CustomExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();

        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            Throwable t = context.getException();

            final FacesContext fc = FacesContext.getCurrentInstance();
            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();

            try {
                System.out.printf(">>> Exception caught: %s", t.getMessage());
                t.printStackTrace();

                requestMap.put("exceptionMessage", t.getMessage());

                ExternalContext extContext = fc.getExternalContext();
                String url = extContext.encodeActionURL(extContext.getRequestContextPath() + "/500.xhtml");
                extContext.redirect(url);
            } catch (Exception e) {
                String errorPageLocation = "/WEB-INF/500.xhtml";
                fc.setViewRoot(fc.getApplication().getViewHandler().createView(fc, errorPageLocation));
                fc.getPartialViewContext().setRenderAll(false);
                fc.renderResponse();
            } finally {
                i.remove();
            }
        }

        getWrapped().handle();
    }
}