package outsera.goldenraspberry.application.exception;

public class DataImportException extends BusinessException {
    public DataImportException(String message) { super(message); }
    public DataImportException(String message, Throwable cause) { super(message, cause); }
}
