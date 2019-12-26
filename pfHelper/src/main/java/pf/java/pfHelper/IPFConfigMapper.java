package pf.java.pfHelper;

import java.util.List;

public interface IPFConfigMapper
{
    List<PFModelConfigMapper> GetModelConfigMapper();

    PFPathConfig GetPathConfig();
    PFNetworkConfig GetNetworkConfig();
}
