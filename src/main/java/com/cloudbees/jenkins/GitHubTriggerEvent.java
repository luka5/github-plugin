package com.cloudbees.jenkins;

import jenkins.scm.api.SCMEvent;
import org.kohsuke.github.GHEventPayload;

/**
 * Encapsulates an event for {@link GitHubPushTrigger}.
 *
 * @since 1.26.0
 */
public class GitHubTriggerEvent {

    /**
     * The timestamp of the event (or if unavailable when the event was received)
     */
    private final long timestamp;
    /**
     * The origin of the event (see {@link SCMEvent#originOf(javax.servlet.http.HttpServletRequest)})
     */
    private final String origin;
    /**
     * The user that the event was provided by.
     */
    private final String triggeredByUser;
    /**
     * The push that the event was provided by.
     */
    private final GHEventPayload.Push push;

    private GitHubTriggerEvent(long timestamp, String origin, String triggeredByUser, GHEventPayload.Push push) {
        this.timestamp = timestamp;
        this.origin = origin;
        this.triggeredByUser = triggeredByUser;
        this.push = push;
    }

    public static Builder create() {
        return new Builder();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getOrigin() {
        return origin;
    }

    public String getTriggeredByUser() {
        return triggeredByUser;
    }

    public GHEventPayload.Push getPush() {
        return push;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GitHubTriggerEvent that = (GitHubTriggerEvent) o;

        if (timestamp != that.timestamp) {
            return false;
        }
        if (origin != null ? !origin.equals(that.origin) : that.origin != null) {
            return false;
        }
        if (triggeredByUser != null ? triggeredByUser.equals(that.triggeredByUser) : that.triggeredByUser == null) {
            return false;
        }
//        return push != null ? push.equals(that.push) : that.push == null // todo fixme
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (triggeredByUser != null ? triggeredByUser.hashCode() : 0);
        result = 31 * result + (push != null ? push.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GitHubTriggerEvent{"
                + "timestamp=" + timestamp
                + ", origin='" + origin + '\''
                + ", triggeredByUser='" + triggeredByUser + '\''
                + ", push='" + push + '\''
                + '}';
    }

    /**
     * Builder for {@link GitHubTriggerEvent} instances..
     */
    public static class Builder {
        private long timestamp;
        private String origin;
        private String triggeredByUser;
        private GHEventPayload.Push push = null;

        private Builder() {
            timestamp = System.currentTimeMillis();
        }

        public Builder withTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withOrigin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder withTriggeredByUser(String triggeredByUser) {
            this.triggeredByUser = triggeredByUser;
            return this;
        }

        public Builder withGHEventPushPayload(GHEventPayload.Push push) {
            this.push = push;
            return this;
        }

        public GitHubTriggerEvent build() {
            return new GitHubTriggerEvent(timestamp, origin, triggeredByUser, push);
        }

        @Override
        public String toString() {
            return "GitHubTriggerEvent.Builder{"
                    + "timestamp=" + timestamp
                    + ", origin='" + origin + '\''
                    + ", triggeredByUser='" + triggeredByUser + '\''
                    + ", push='" + push + '\''
                    + '}';
        }
    }
}
