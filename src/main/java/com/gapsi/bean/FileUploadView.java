package com.gapsi.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class FileUploadView implements Serializable {
	private static final long serialVersionUID = 3779090422755428828L;
	private UploadedFile file;

	public UploadedFile getFile() {
		System.out.println("Geeting the file:" + file.getFileName());
		return file;
	}

	public void setFile(UploadedFile file) {
		System.out.println("Stting the file" + file.getFileName());
		this.file = file;
	}

	public void handleFileUpload(FileUploadEvent event) {
		if (file != null) {
			System.out.println("calling file upload...");
			File targetFolder = new File("/tmp");

			if (!targetFolder.exists()) {
				targetFolder.mkdirs();
			}
			try {
				file = event.getFile();
				System.out.println("File:" + file.getFileName());
				FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
				FacesContext.getCurrentInstance().addMessage(null, message);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("file upload after catch..");
		} else {
			System.out.println("File is null");

		}

	}

	public String save() throws IOException {
		if (file != null) {
			System.out.println(
					"Uploaded File Name Is :: " + file.getFileName() + " :: Uploaded File Size :: " + file.getSize());
			String filename = FilenameUtils.getName(file.getFileName());
			InputStream input = file.getInputstream();
			OutputStream output = new FileOutputStream(new File("/path/to/uploads", filename));

			try {
				IOUtils.copy(input, output);
			} finally {
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(output);
			}
		} else {
			System.out.println("File is null");
		}

		return "";
	}

}