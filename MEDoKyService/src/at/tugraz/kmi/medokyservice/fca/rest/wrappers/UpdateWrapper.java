package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

/**
 * Wrapper used to receive valuations
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 *
 */
public class UpdateWrapper extends AbstractWrapper {

  public long   learningObjectId;
  public String externalUID;
  public boolean course;

}
