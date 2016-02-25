/**
 * ScadaBRAPI.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.org.scadabr.api;

public interface ScadaBRAPI extends java.rmi.Remote {
    public br.org.scadabr.api.config.RemoveFlexProjectResponse removeFlexProject(int id) throws java.rmi.RemoteException;
    public br.org.scadabr.api.config.SetFlexBuilderConfigResponse setFlexBuilderConfig(br.org.scadabr.api.config.SetFlexBuilderConfigParams setFlexBuilderConfigParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.config.GetFlexBuilderConfigResponse getFlexBuilderConfig(int projectId) throws java.rmi.RemoteException;
    public br.org.scadabr.api.config.BrowseFlexProjectsResponse browseFlexProjects() throws java.rmi.RemoteException;
    public br.org.scadabr.api.config.ConfigureDataPointResponse configureDataPoint(br.org.scadabr.api.config.ConfigureDataPointParams configureDataPointParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.config.RemoveDataPointResponse removeDataPoint(br.org.scadabr.api.config.RemoveDataPointParams removeDataPointParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.config.BrowseDataPointsResponse browseDataPoints(br.org.scadabr.api.config.BrowseDataPointsParams browseDataPointsParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.config.RemoveDataSourceResponse removeDataSource(br.org.scadabr.api.config.RemoveDataSourceParams removeDataSourceParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.config.ConfigureDataSourceResponse configureDataSource(br.org.scadabr.api.config.ConfigureDataSourceParams configureDataSourceParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.config.BrowseDataSourcesResponse browseDataSources(br.org.scadabr.api.config.BrowseDataSourcesParams browseDataSourcesParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.da.GetStatusResponse getStatus() throws java.rmi.RemoteException;
    public br.org.scadabr.api.da.ReadDataResponse readData(br.org.scadabr.api.da.ReadDataParams readDataParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.da.WriteDataResponse writeData(br.org.scadabr.api.da.WriteDataParams writeDataParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.da.WriteStringDataResponse writeStringData(br.org.scadabr.api.da.WriteStringDataParams writeStringDataParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.da.BrowseTagsResponse browseTags(br.org.scadabr.api.da.BrowseTagsParams browseTagsParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.hda.GetDataHistoryResponse getDataHistory(br.org.scadabr.api.hda.GetDataHistoryParams getDataHistoryParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.ae.GetActiveEventsResponse getActiveEvents(br.org.scadabr.api.ae.GetActiveEventsParams getActiveEventsParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.ae.GetEventsHistoryResponse getEventsHistory(br.org.scadabr.api.ae.GetEventsHistoryParams getEventsHistoryParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.ae.AckEventsResponse ackEvents(br.org.scadabr.api.ae.AckEventsParams ackEventsParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.ae.BrowseEventsResponse browseEventsDefinitions(br.org.scadabr.api.ae.BrowseEventsParams browseEventsParams) throws java.rmi.RemoteException;
    public br.org.scadabr.api.ae.AnnotateEventResponse annotateEvent(br.org.scadabr.api.ae.AnnotateEventParams annotateEventParams) throws java.rmi.RemoteException;
}
