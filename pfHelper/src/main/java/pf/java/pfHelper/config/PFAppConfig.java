package pf.java.pfHelper.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PFAppConfig {

    @PostConstruct
    public void beforeInit() {
    }
    @Value("${pf.appKey}")
    private  String appKey;
    
    @Value("${pf.useLocalData}")
    private  Boolean useLocalData;
    
    @Value("${pf.preventFgs}")
    private  Boolean preventFgs;
    
    @Value("${pf.pfDebug}")
    private  Boolean pfDebug;

    @Value("${pf.allowAutoLogin}")
    private Boolean allowAutoLogin; 
    

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public Boolean getUseLocalData() {
		//return true;
		return useLocalData;
	}

	public void setUseLocalData(Boolean useLocalData) {
		this.useLocalData = useLocalData;
	}

	public Boolean getPreventFgs() {
		return preventFgs;
	}

	public void setPreventFgs(Boolean preventFgs) {
		this.preventFgs = preventFgs;
	}

	public Boolean getPfDebug() {
		return pfDebug;
	}

	public void setPfDebug(Boolean pfDebug) {
		this.pfDebug = pfDebug;
	}

	public Boolean getAllowAutoLogin() {
		return allowAutoLogin;
	}

	public void setAllowAutoLogin(Boolean allowAutoLogin) {
		this.allowAutoLogin = allowAutoLogin;
	}
}
