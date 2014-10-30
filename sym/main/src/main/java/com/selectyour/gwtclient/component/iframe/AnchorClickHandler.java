package com.selectyour.gwtclient.component.iframe;// MaiseyenkaDP gdfan 23.07.12 20:47

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.selectyour.gwtclient.base.ResourceUrlUtils;

/**
 * click on anchor <a></a> handler
 */
public class AnchorClickHandler implements ClickHandler {
    private IFrame iframe;

    public AnchorClickHandler(IFrame iframe) {
        this.iframe = iframe;
    }

    public void onClick(ClickEvent event) {
        Element element = Element.as(event.getNativeEvent().getEventTarget());
        while (!(element.hasTagName("A") || element.hasTagName("a"))) {
            element = element.getParentElement();
        }
        AnchorElement anchor = AnchorElement.as(element);
        String href = anchor.getHref();
        if (ResourceUrlUtils.isSupportedLink(href)) {
            iframe.reload(href);
        } else {
            if (Window.confirm("В настоящее время на сайте 'Выбери твое' поддерживается только работа "
                    + " с разделом Каталог: Классика и Модерн.\n"
                    + "Хотите открыть текущую страницу в отдельном окне, чтобы перейти по данной ссылке ?")) {
                Window.open(iframe.getCurrentUrl(), null, null);
            }
        }

        event.preventDefault();
    }

}
