package sources.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@Named("checkout")
@Scope("session")
public class CheckoutBean {

    public CheckoutBean() {
    }
}
