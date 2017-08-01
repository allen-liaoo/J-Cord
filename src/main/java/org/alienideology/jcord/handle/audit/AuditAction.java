package org.alienideology.jcord.handle.audit;

import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.util.DataUtils;

/**
 * AuditAction - An action that can be performed with a audit log reason.
 *
 * @author AlienIdeology
 */
public abstract class AuditAction<T> {

    protected final Requester requester;
    private String[] params;

    public AuditAction(IdentityImpl identity, HttpPath path, String... params) {
        requester = new Requester(identity, path);
        this.params = params;
    }

    public T withReason(String reason) {
        if (reason != null) {
            requester.updateRequest(request ->
                    request.header("X-Audit-Log-Reason", // Add header
                            DataUtils.encodeToUrl(reason).replace('+', ' '))); // URI Encode
        }
        requester.request(params);
        return request();
    }

    public T withoutReason() {
        return withReason(null);
    }

    protected abstract T request();

}
