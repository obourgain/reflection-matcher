package com.github.obourgain.matcher.reflection;

/**
 * Do not use the {@link AssertionError}, as it is annoying for tests
 *
 * @author olivier bourgain
 */
public class MatchException extends RuntimeException {

    public MatchException() {
    }

    public MatchException(Object message) {
        super(message != null ? message.toString() : "");
    }

}
