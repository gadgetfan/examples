package com.selectyour.gwtclient.model;// MaiseyenkaDP gdfan 22.07.12 10:43

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;

public interface ProductImageService {
    void addProductsByImages(NodeList<Element> imgList, boolean addPossibleProducts);

    void clearProducts();
}
