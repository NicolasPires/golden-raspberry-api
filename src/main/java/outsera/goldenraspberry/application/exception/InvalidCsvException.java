package outsera.goldenraspberry.application.exception;

public class InvalidCsvException extends DataImportException {
    public InvalidCsvException(String message) { super(message); }
    public InvalidCsvException(String message, Throwable cause) { super(message, cause); }
}
