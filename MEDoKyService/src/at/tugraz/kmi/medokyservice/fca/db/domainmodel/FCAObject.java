package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import at.tugraz.kmi.medokyservice.fca.db.FCAAbstract;

/**
 * An FCA Object -- a purely semantic class.
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class FCAObject extends FCAAbstract {

  /**
   * 
   */
  private static final long serialVersionUID = 6828315313577050214L;

  /**
   * @see FCAAbstract
   * @param name
   * @param description
   */
  public FCAObject(String name, String description, String creationId) {
    super(name, description, creationId);
  }

}
