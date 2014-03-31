package beans;

import java.util.ArrayList;
import java.util.Arrays;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="checkout", eager=true)
@SessionScoped
public class CheckoutBean {

    public CheckoutBean() {
    }
}
