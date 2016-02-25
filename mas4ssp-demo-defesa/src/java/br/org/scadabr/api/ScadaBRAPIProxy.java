package br.org.scadabr.api;

public class ScadaBRAPIProxy implements br.org.scadabr.api.ScadaBRAPI {
  private String _endpoint = null;
  private br.org.scadabr.api.ScadaBRAPI scadaBRAPI = null;
  
  public ScadaBRAPIProxy() {
    _initScadaBRAPIProxy();
  }
  
  public ScadaBRAPIProxy(String endpoint) {
    _endpoint = endpoint;
    _initScadaBRAPIProxy();
  }
  
  private void _initScadaBRAPIProxy() {
    try {
      scadaBRAPI = (new br.org.scadabr.api.APILocator()).getAPI();
      if (scadaBRAPI != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)scadaBRAPI)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)scadaBRAPI)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (scadaBRAPI != null)
      ((javax.xml.rpc.Stub)scadaBRAPI)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public br.org.scadabr.api.ScadaBRAPI getScadaBRAPI() {
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI;
  }
  
  public br.org.scadabr.api.config.RemoveFlexProjectResponse removeFlexProject(int id) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.removeFlexProject(id);
  }
  
  public br.org.scadabr.api.config.SetFlexBuilderConfigResponse setFlexBuilderConfig(br.org.scadabr.api.config.SetFlexBuilderConfigParams setFlexBuilderConfigParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.setFlexBuilderConfig(setFlexBuilderConfigParams);
  }
  
  public br.org.scadabr.api.config.GetFlexBuilderConfigResponse getFlexBuilderConfig(int projectId) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.getFlexBuilderConfig(projectId);
  }
  
  public br.org.scadabr.api.config.BrowseFlexProjectsResponse browseFlexProjects() throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.browseFlexProjects();
  }
  
  public br.org.scadabr.api.config.ConfigureDataPointResponse configureDataPoint(br.org.scadabr.api.config.ConfigureDataPointParams configureDataPointParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.configureDataPoint(configureDataPointParams);
  }
  
  public br.org.scadabr.api.config.RemoveDataPointResponse removeDataPoint(br.org.scadabr.api.config.RemoveDataPointParams removeDataPointParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.removeDataPoint(removeDataPointParams);
  }
  
  public br.org.scadabr.api.config.BrowseDataPointsResponse browseDataPoints(br.org.scadabr.api.config.BrowseDataPointsParams browseDataPointsParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.browseDataPoints(browseDataPointsParams);
  }
  
  public br.org.scadabr.api.config.RemoveDataSourceResponse removeDataSource(br.org.scadabr.api.config.RemoveDataSourceParams removeDataSourceParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.removeDataSource(removeDataSourceParams);
  }
  
  public br.org.scadabr.api.config.ConfigureDataSourceResponse configureDataSource(br.org.scadabr.api.config.ConfigureDataSourceParams configureDataSourceParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.configureDataSource(configureDataSourceParams);
  }
  
  public br.org.scadabr.api.config.BrowseDataSourcesResponse browseDataSources(br.org.scadabr.api.config.BrowseDataSourcesParams browseDataSourcesParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.browseDataSources(browseDataSourcesParams);
  }
  
  public br.org.scadabr.api.da.GetStatusResponse getStatus() throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.getStatus();
  }
  
  public br.org.scadabr.api.da.ReadDataResponse readData(br.org.scadabr.api.da.ReadDataParams readDataParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.readData(readDataParams);
  }
  
  public br.org.scadabr.api.da.WriteDataResponse writeData(br.org.scadabr.api.da.WriteDataParams writeDataParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.writeData(writeDataParams);
  }
  
  public br.org.scadabr.api.da.WriteStringDataResponse writeStringData(br.org.scadabr.api.da.WriteStringDataParams writeStringDataParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.writeStringData(writeStringDataParams);
  }
  
  public br.org.scadabr.api.da.BrowseTagsResponse browseTags(br.org.scadabr.api.da.BrowseTagsParams browseTagsParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.browseTags(browseTagsParams);
  }
  
  public br.org.scadabr.api.hda.GetDataHistoryResponse getDataHistory(br.org.scadabr.api.hda.GetDataHistoryParams getDataHistoryParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.getDataHistory(getDataHistoryParams);
  }
  
  public br.org.scadabr.api.ae.GetActiveEventsResponse getActiveEvents(br.org.scadabr.api.ae.GetActiveEventsParams getActiveEventsParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.getActiveEvents(getActiveEventsParams);
  }
  
  public br.org.scadabr.api.ae.GetEventsHistoryResponse getEventsHistory(br.org.scadabr.api.ae.GetEventsHistoryParams getEventsHistoryParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.getEventsHistory(getEventsHistoryParams);
  }
  
  public br.org.scadabr.api.ae.AckEventsResponse ackEvents(br.org.scadabr.api.ae.AckEventsParams ackEventsParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.ackEvents(ackEventsParams);
  }
  
  public br.org.scadabr.api.ae.BrowseEventsResponse browseEventsDefinitions(br.org.scadabr.api.ae.BrowseEventsParams browseEventsParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.browseEventsDefinitions(browseEventsParams);
  }
  
  public br.org.scadabr.api.ae.AnnotateEventResponse annotateEvent(br.org.scadabr.api.ae.AnnotateEventParams annotateEventParams) throws java.rmi.RemoteException{
    if (scadaBRAPI == null)
      _initScadaBRAPIProxy();
    return scadaBRAPI.annotateEvent(annotateEventParams);
  }
  
  
}