package test.myblog.model;

public class CKEditorResponse {
    private boolean uploaded;
    private String fileName;
    private String url;
    private String error;
    
	public CKEditorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CKEditorResponse(boolean uploaded, String fileName, String url) {
		super();
		this.uploaded = uploaded;
		this.fileName = fileName;
		this.url = url;
	}
	
	public CKEditorResponse(boolean uploaded, String error) {
		super();
		this.uploaded = uploaded;
		this.error = error;
	}
	
	public boolean isUploaded() {
		return uploaded;
	}
	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

    // getters and setters omitted for brevity
}

