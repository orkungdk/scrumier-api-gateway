package tr.com.ogedik.apigateway.constants;

/**
 * @author orkun.gedik
 */
public enum Permission {
  DEFAULT,
  ADMIN,

  RETRIEVE_WORK_LOG,
  CREATE_WORK_LOG,
  UPDATE_WORK_LOG,
  DELETE_WORK_LOG,

  RETRIEVE_OTHERS_WORK_LOG,
  CREATE_OTHERS_WORK_LOG,
  UPDATE_OTHERS_WORK_LOG,
  DELETE_OTHERS_WORK_LOG;
}