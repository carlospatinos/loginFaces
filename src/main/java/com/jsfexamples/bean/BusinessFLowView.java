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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
//import org.apache.commons.lang.builder.ToStringBuilder;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class BusinessFLowView implements Serializable {
	private static final long serialVersionUID = -3980071992108155000L;
	// private final Logger log = Logger.getLogger(this.getClass().getName());

	private static final Logger log = Logger.getLogger("BusinessFLowView");
	private static final String FILES_STORE_LOCATION = "/home/ecapati/Development/git/loginFaces/WebContent/WEB-INF/receivedFiles/";
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private UploadedFile file;
	private Part file2;

	private StreamedContent downloadedFile;
	private String uploadedDate;
	private boolean validFile;
	private List<LogEntry> logs = new ArrayList<LogEntry>();

	public BusinessFLowView() {
		String fileName = "/home/ecapati/Pictures/error.png";
		InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream(fileName);
		log.log(Level.INFO, "File to download is [{0}] size [{1}].", new Object[] { fileName, "500k" });
		downloadedFile = new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
	}

	public StreamedContent getDownloadedFile() {
		return downloadedFile;
	}

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

	public Part getFile2() {
		return file2;
	}

	public void setFile2(Part file2) {
		this.file2 = file2;
	}

	public String onFlowProcess(FlowEvent event) {
		String oldStep = event.getOldStep();
		String newStep = event.getNewStep();
		log.log(Level.INFO, "Processing event: [{0}]. Flow from [{1}] to [{2}]. Is valid file? [{3}]",
				new Object[] { event, oldStep, newStep, validFile });

		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful",  "Your message: ") );

		this.logs.add(new LogEntry(dateFormat.format(new Date()), oldStep, newStep));
		if (!validFile && newStep.equals("sendFile")) {
			log.log(Level.WARNING, "File has not been validated. [{0}]", validFile);
			validFile = false; // reset in case user goes back

			return "reviewFile";
		} else {
			log.log(Level.INFO, "Moving to [{0}]", newStep);
			return newStep;
		}
	}

	public String register() {
		// logs.clear();
		log.log(Level.INFO, ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE));
		return "main.xhtml?faces-redirect=true";
	}

	public void handleFileUpload() {
		if (file2 != null) {
			try {
				String fileName = file2.getName();
				long fileSize = file2.getSize();
				log.log(Level.INFO, "Saving file: [{0}], size: [{1}]", new Object[]{fileName, fileSize});
				saveStreamContent(fileName, file2.getInputStream());
			} catch (IOException ex) {
				log.log(Level.SEVERE, "Exception saving the file", ex);
			}
		}

	}
	
	public void bip() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful",  "Your message: ") );
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Second Message", "Additional Message Detail"));
    }

	private void saveStreamContent(String name, InputStream content) throws IOException {
		log.log(Level.INFO, "Saving file content.");
		//String here = new Scanner(content).useDelimiter("\\A").next();
		byte[] buffer = new byte[content.available()];
		content.read(buffer);

		File targetFile = new File(FILES_STORE_LOCATION + "targetFile.tmp");
		OutputStream outStream = new FileOutputStream(targetFile);
		outStream.write(buffer);
		outStream.close();
	}

	public void fileUploadListener(FileUploadEvent event) {
		handleFileUpload();
		System.out.println(event);
		System.out.println(file);
		System.out.println(file2);

	}

}
