package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.Map;

/**
 * Wrapper used to receive valuations
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 *
 */
public class ValuationWrapper extends AbstractWrapper {

  public Map<Long,Float> objectValuations, attributeValuations;
  public String externalUID;
  
}
