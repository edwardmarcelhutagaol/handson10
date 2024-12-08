package dto;

public class ApiResponse {
    private String response;
    private Integer responseCode;

    public ApiResponse() {
    }

    public ApiResponse(String response, Integer responseCode) {
        this.response = response;
        this.responseCode = responseCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
