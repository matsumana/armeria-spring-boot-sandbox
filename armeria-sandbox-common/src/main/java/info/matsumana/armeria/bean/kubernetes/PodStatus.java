package info.matsumana.armeria.bean.kubernetes;

import java.io.Serializable;

/**
 * https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.12/#podstatus-v1-core
 */
public class PodStatus implements Serializable {

    private static final long serialVersionUID = -7789580934211557531L;

    private String phase;
    private String hostIP;
    private String podIP;

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public String getPodIP() {
        return podIP;
    }

    public void setPodIP(String podIP) {
        this.podIP = podIP;
    }
}
