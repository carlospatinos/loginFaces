package com.jsfexamples.bean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.fileupload.FileUploadRenderer;

public class CustomFileUploadRenderer extends FileUploadRenderer {

    @Override
    public void decode(FacesContext context, UIComponent component) {
    	System.out.println("yuhuuuuuuu");
        if (context.getExternalContext().getRequestContentType().toLowerCase().startsWith("multipart/")) {
        	System.out.println("yuhuuuuuuu22222");
            super.decode(context, component);
        }
    }

}