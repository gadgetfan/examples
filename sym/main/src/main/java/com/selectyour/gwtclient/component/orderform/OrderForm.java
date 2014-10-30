package com.selectyour.gwtclient.component.orderform;// MaiseyenkaDP gdfan 24.06.12 12:51

import com.google.gwt.dom.client.Element;
import com.selectyour.gwtclient.model.Product;

/**
 * form to make order on product
 */
public class OrderForm {
    /**
     * element, that contains form tag.
     */
    private Element idSubmit;
    private Element idInput;

    public OrderForm(Element idSubmit, Element idInput) {
        this.idSubmit = idSubmit;
        this.idInput = idInput;
    }

    /**
     * enables Submit button on form
     */
    public void enableSubmit() {
        if (idSubmit != null) {
            idSubmit.removeAttribute("disabled");
        }
    }

    /**
     * saves product data in form fields (<input type="hidden" ...></input>)
     *
     * @param product
     */
    public void saveProduct(Product product) {
        if (idInput != null) {
            idInput.setAttribute("value", product.getId());
        }
    }
}
