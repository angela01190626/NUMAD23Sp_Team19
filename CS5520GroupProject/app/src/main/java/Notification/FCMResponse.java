package Notification;

import java.util.List;

public class FCMResponse {

    private long multicastId;

    private int success;

    private int failure;

    private int canonicalId;

    private List<FCMResult> result;

    private long messageId;

    public FCMResponse() {

    }

    public long getMulticastId() {
        return multicastId;
    }

    public void setMulticastId(long multicastId) {
        this.multicastId = multicastId;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonicalId() {
        return canonicalId;
    }

    public void setCanonicalId(int canonicalId) {
        this.canonicalId = canonicalId;
    }

    public List<FCMResult> getResult() {
        return result;
    }

    public void setResult(List<FCMResult> result) {
        this.result = result;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
