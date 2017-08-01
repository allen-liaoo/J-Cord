package org.alienideology.jcord.handle.audit;

import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
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
 */
public abstract class AuditAction<T> {

    private final Requester requester;
    private String[] params;

    public AuditAction(IdentityImpl identity, HttpPath path, String... params) {
        requester = new Requester(identity, path);
        this.params = params;
    }

    public T withReason(String reason) {
        requester.request(params);
        if (reason != null) {
            requester.updateRequest(request ->
                    request.header("X-Audit-Log-Reason", // Add header
                            DataUtils.encodeToUrl(reason).replace('+', ' '))); // URI Encode
        }
        return request(requester);
    }

    public T withoutReason() {
        return withReason(null);
    }

    // Requester, used to perform request
    protected abstract T request(Requester requester);

    public static class EmptyAuditAction<T> extends AuditAction<T> {

        public EmptyAuditAction() {
            super(null, null);
        }

        private EmptyAuditAction(IdentityImpl identity, HttpPath path, String... params) {
            this();
        }

        @Override
        public T withReason(String reason) {
            return request(null);
        }

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
