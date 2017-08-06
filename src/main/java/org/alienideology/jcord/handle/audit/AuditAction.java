package org.alienideology.jcord.handle.audit;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.alienideology.jcord.util.DataUtils;

/**
 * AuditAction - An action that can be performed with a audit log reason.
 *
 * To perform an action, on of the following method must be invoked:
 * <ul>
 *     <li>{@link #withReason(String)}</li>
 *     <li>{@link #withoutReason()}</li>
 * </ul>
 * Invoking {@link #withReason(String)} will cause the action be performed with an audit log reason,
 * while invoking {@link #withoutReason()} will cause the action be performed without any audit log reason.
 *
 * @author AlienIdeology
 * @param <T> The type of return value for this action.
 */
public abstract class AuditAction<T> {

    private final Requester requester;
    private String[] params;

    /**
     * The constructor for an audit action.
     * Note that the audit action is constructed internally,
     * the user wont ever need to use this constructor.
     *
     * @param identity The identity of this action.
     * @param path The path for the http request.
     * @param params The parameters to complete the path.
     */
    public AuditAction(IdentityImpl identity, HttpPath path, String... params) {
        requester = new Requester(identity, path);
        this.params = params;
    }

    /**
     * Complete this action with an audit log reason.
     * The reason can may empty or {@code null}.
     *
     * @param reason The reason.
     * @return The result of this action.
     */
    public T withReason(String reason) {
        requester.request(params);
        if (reason != null && !reason.isEmpty()) {
            requester.updateRequest(request ->
                    request.header("X-Audit-Log-Reason", // Add header
                            DataUtils.encodeToUrl(reason).replace('+', ' '))); // URI Encode
        }
        return request(requester);
    }

    /**
     * Complete this action without an audit log reason.
     * This is the same as invoking {@link #withReason(String)} with an {@code null} or empty string.
     *
     * @return The result of this action.
     */
    public T withoutReason() {
        return withReason(null);
    }

    // Requester, used to perform request
    protected abstract T request(Requester requester);

    /**
     * An empty audit action, as a place holder for ignored actions (because the value passed is not valid).
     *
     * @param <T> The type of return value for this action, which is probably {@link Void}.
     */
    public static class EmptyAuditAction<T> extends AuditAction<T> {

        /**
         * Default constructor for an empty audit action.
         */
        public EmptyAuditAction() {
            super(null, null);
        }

        private EmptyAuditAction(IdentityImpl identity, HttpPath path, String... params) {
            this();
        }

        /**
         * This does not perform any action. It returns null.
         *
         * @param reason The reason.
         * @return Null.
         */
        @Override
        public T withReason(String reason) {
            return request(null);
        }

        /**
         * This does not perform any action. It returns null.
         *
         * @return Null.
         */
        @Override
        public T withoutReason() {
            return request(null);
        }

        @Override
        protected T request(Requester requester) {
            return null;
        }
    }

}
