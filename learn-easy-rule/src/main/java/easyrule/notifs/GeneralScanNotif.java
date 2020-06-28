package easyrule.notifs;

/**
 * 
 * Created by tianakai on 2018/8/15.
 * @author tiankai
 */
public class GeneralScanNotif extends GeneralKakfaNotif {
	
    private Integer proxyId;
    private String proxyName;
    private String localIp;
	public Integer getProxyId() {
		return proxyId;
	}
	public void setProxyId(Integer proxyId) {
		this.proxyId = proxyId;
	}
	public String getProxyName() {
		return proxyName;
	}
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	public String getLocalIp() {
		return localIp;
	}
	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

}
