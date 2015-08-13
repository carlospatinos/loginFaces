package com.jsfexamples.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
//import org.apache.commons.lang.builder.ToStringBuilder;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class BusinessFLowView implements Serializable {
	private static final long serialVersionUID = -3980071992108155000L;
	//private final Logger log = Logger.getLogger(this.getClass().getName());
	
	private static final Logger log = Logger.getLogger("BusinessFLowView");

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private UploadedFile file;
	private String uploadedDate;
	private boolean validFile;

	private List<LogEntry> logs = new ArrayList<LogEntry>();

	public List<LogEntry> getLogs() {
		return logs;
	}

	public void setLogs(List<LogEntry> logs) {
		this.logs = logs;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public boolean isValidFile() {
		return validFile;
	}

	public void setValidFile(boolean validFile) {
		this.validFile = validFile;
	}

	public String onFlowProcess(FlowEvent event) {
		String oldStep = event.getOldStep();
		String newStep = event.getNewStep();
		log.log(Level.INFO, "Flow from [{0}] to [{1}]. Is valid file? [{2}]", new Object[]{oldStep, newStep, validFile});
		
		this.logs.add(new LogEntry(dateFormat.format(new Date()), oldStep, newStep));
		if (!validFile && newStep.equals("sendFile")) {
			log.log(Level.WARNING, "File has not been validated. [{0}]", validFile);
			validFile = false; // reset in case user goes back
			return "reviewFile";
		} else  {
			log.log(Level.INFO, "Moving to [{0}]", newStep);
			return newStep;
		}
		/*
		 if (oldStep.equals("uploadFile") && newStep.equals("reviewFile")) {
			log.log(Level.INFO, "Resetting everything");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("businessFLowView", null);
			this.logs.clear();
			return newStep;
		} else
		 */
	}

	public String register() {
		//logs.clear();
		log.log(Level.INFO, ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE));
	    return "main.xhtml?faces-redirect=true";
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

			} catch (Exception ex) {
				log.log(Level.SEVERE, "Exception saving the file", ex);
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
