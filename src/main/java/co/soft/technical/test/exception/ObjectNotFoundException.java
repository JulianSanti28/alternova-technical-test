package co.soft.technical.test.exception;

public class ObjectNotFoundException extends BaseException{
    /**
     * Constructs a BaseException with the specified detail key message.
     *
     * @param key the detailed key message
     */
    public ObjectNotFoundException(String key) {
        super(key);
    }
}
