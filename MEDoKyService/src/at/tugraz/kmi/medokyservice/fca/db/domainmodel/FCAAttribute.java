package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import at.tugraz.kmi.medokyservice.fca.db.FCAAbstract;

/**
 * An FCA Attribute -- a purely semantic class
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class FCAAttribute extends FCAAbstract {

  /**
   * 
   */
  private static final long serialVersionUID = -5485480910283112605L;

  /**
   * @see FCAAbstract
   * @param name
   * @param description
   */
  public FCAAttribute(String name, String description) {
    super(name, description);

  }

}
