package org.springframework.data.mongodb.core;

import com.mongodb.*;
import org.bson.types.ObjectId;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoWriter;
import org.springframework.data.mongodb.core.mapping.event.MongoMappingEvent;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.CloseableIterator;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class MultiTenantMongoTemplate extends MongoTemplate {
    private final MongoTemplate globalTemplate;

    public MultiTenantMongoTemplate(Mongo mongo, String databaseName) {
        super(mongo, databaseName);
        globalTemplate = new MongoTemplate(mongo, databaseName);
    }

    public MultiTenantMongoTemplate(Mongo mongo, String databaseName, UserCredentials userCredentials) {
        super(mongo, databaseName, userCredentials);
        globalTemplate = new MongoTemplate(mongo, databaseName, userCredentials);
    }

    public MultiTenantMongoTemplate(MongoDbFactory mongoDbFactory) {
        super(mongoDbFactory);
        globalTemplate = new MongoTemplate(mongoDbFactory);
    }

    public MultiTenantMongoTemplate(MongoDbFactory mongoDbFactory, MongoConverter mongoConverter) {
        super(mongoDbFactory, mongoConverter);
        globalTemplate = new MongoTemplate(mongoDbFactory, mongoConverter);
    }

    protected abstract MongoTemplate resolveMongoTemplate();

    public MongoTemplate getGlobalTemplate() {
        return globalTemplate;
    }

    @Override
    public void setWriteResultChecking(WriteResultChecking resultChecking) {
        resolveMongoTemplate().setWriteResultChecking(resultChecking);
    }

    @Override
    public void setWriteConcern(WriteConcern writeConcern) {
        resolveMongoTemplate().setWriteConcern(writeConcern);
    }

    @Override
    public void setWriteConcernResolver(WriteConcernResolver writeConcernResolver) {
        resolveMongoTemplate().setWriteConcernResolver(writeConcernResolver);
    }

    @Override
    public void setReadPreference(ReadPreference readPreference) {
        resolveMongoTemplate().setReadPreference(readPreference);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        resolveMongoTemplate().setApplicationContext(applicationContext);
    }

    @Override
    public MongoConverter getConverter() {
        return resolveMongoTemplate().getConverter();
    }

    @Override
    public <T> CloseableIterator<T> stream(Query query, Class<T> entityType) {
        return resolveMongoTemplate().stream(query, entityType);
    }

    @Override
    public <T> CloseableIterator<T> stream(Query query, Class<T> entityType, String collectionName) {
        return resolveMongoTemplate().stream(query, entityType, collectionName);
    }

    @Override
    public String getCollectionName(Class<?> entityClass) {
        return resolveMongoTemplate().getCollectionName(entityClass);
    }

    @Override
    public CommandResult executeCommand(String jsonCommand) {
        return resolveMongoTemplate().executeCommand(jsonCommand);
    }

    @Override
    public CommandResult executeCommand(DBObject command) {
        return resolveMongoTemplate().executeCommand(command);
    }

    @Override
    public CommandResult executeCommand(DBObject command, int options) {
        return resolveMongoTemplate().executeCommand(command, options);
    }

    @Override
    public CommandResult executeCommand(DBObject command, ReadPreference readPreference) {
        return resolveMongoTemplate().executeCommand(command, readPreference);
    }

    @Override
    protected void logCommandExecutionError(DBObject command, CommandResult result) {
        resolveMongoTemplate().logCommandExecutionError(command, result);
    }

    @Override
    public void executeQuery(Query query, String collectionName, DocumentCallbackHandler dch) {
        resolveMongoTemplate().executeQuery(query, collectionName, dch);
    }

    @Override
    protected void executeQuery(Query query, String collectionName, DocumentCallbackHandler dch, CursorPreparer preparer) {
        resolveMongoTemplate().executeQuery(query, collectionName, dch, preparer);
    }

    @Override
    public <T> T execute(DbCallback<T> action) {
        return resolveMongoTemplate().execute(action);
    }

    @Override
    public <T> T execute(Class<?> entityClass, CollectionCallback<T> callback) {
        return resolveMongoTemplate().execute(entityClass, callback);
    }

    @Override
    public <T> T execute(String collectionName, CollectionCallback<T> callback) {
        return resolveMongoTemplate().execute(collectionName, callback);
    }

    @Override
    public <T> T executeInSession(DbCallback<T> action) {
        return resolveMongoTemplate().executeInSession(action);
    }

    @Override
    public <T> DBCollection createCollection(Class<T> entityClass) {
        return resolveMongoTemplate().createCollection(entityClass);
    }

    @Override
    public <T> DBCollection createCollection(Class<T> entityClass, CollectionOptions collectionOptions) {
        return resolveMongoTemplate().createCollection(entityClass, collectionOptions);
    }

    @Override
    public DBCollection createCollection(String collectionName) {
        return resolveMongoTemplate().createCollection(collectionName);
    }

    @Override
    public DBCollection createCollection(String collectionName, CollectionOptions collectionOptions) {
        return resolveMongoTemplate().createCollection(collectionName, collectionOptions);
    }

    @Override
    public DBCollection getCollection(String collectionName) {
        return resolveMongoTemplate().getCollection(collectionName);
    }

    @Override
    public <T> boolean collectionExists(Class<T> entityClass) {
        return resolveMongoTemplate().collectionExists(entityClass);
    }

    @Override
    public boolean collectionExists(String collectionName) {
        return resolveMongoTemplate().collectionExists(collectionName);
    }

    @Override
    public <T> void dropCollection(Class<T> entityClass) {
        resolveMongoTemplate().dropCollection(entityClass);
    }

    @Override
    public void dropCollection(String collectionName) {
        resolveMongoTemplate().dropCollection(collectionName);
    }

    @Override
    public IndexOperations indexOps(String collectionName) {
        return resolveMongoTemplate().indexOps(collectionName);
    }

    @Override
    public IndexOperations indexOps(Class<?> entityClass) {
        return resolveMongoTemplate().indexOps(entityClass);
    }

    @Override
    public BulkOperations bulkOps(BulkOperations.BulkMode bulkMode, String collectionName) {
        return resolveMongoTemplate().bulkOps(bulkMode, collectionName);
    }

    @Override
    public BulkOperations bulkOps(BulkOperations.BulkMode bulkMode, Class<?> entityClass) {
        return resolveMongoTemplate().bulkOps(bulkMode, entityClass);
    }

    @Override
    public BulkOperations bulkOps(BulkOperations.BulkMode mode, Class<?> entityType, String collectionName) {
        return resolveMongoTemplate().bulkOps(mode, entityType, collectionName);
    }

    @Override
    public ScriptOperations scriptOps() {
        return resolveMongoTemplate().scriptOps();
    }

    @Override
    public <T> T findOne(Query query, Class<T> entityClass) {
        return resolveMongoTemplate().findOne(query, entityClass);
    }

    @Override
    public <T> T findOne(Query query, Class<T> entityClass, String collectionName) {
        return resolveMongoTemplate().findOne(query, entityClass, collectionName);
    }

    @Override
    public boolean exists(Query query, Class<?> entityClass) {
        return resolveMongoTemplate().exists(query, entityClass);
    }

    @Override
    public boolean exists(Query query, String collectionName) {
        return resolveMongoTemplate().exists(query, collectionName);
    }

    @Override
    public boolean exists(Query query, Class<?> entityClass, String collectionName) {
        return resolveMongoTemplate().exists(query, entityClass, collectionName);
    }

    @Override
    public <T> List<T> find(Query query, Class<T> entityClass) {
        return resolveMongoTemplate().find(query, entityClass);
    }

    @Override
    public <T> List<T> find(Query query, Class<T> entityClass, String collectionName) {
        return resolveMongoTemplate().find(query, entityClass, collectionName);
    }

    @Override
    public <T> T findById(Object id, Class<T> entityClass) {
        return resolveMongoTemplate().findById(id, entityClass);
    }

    @Override
    public <T> T findById(Object id, Class<T> entityClass, String collectionName) {
        return resolveMongoTemplate().findById(id, entityClass, collectionName);
    }

    @Override
    public <T> GeoResults<T> geoNear(NearQuery near, Class<T> entityClass) {
        return resolveMongoTemplate().geoNear(near, entityClass);
    }

    @Override
    public <T> GeoResults<T> geoNear(NearQuery near, Class<T> entityClass, String collectionName) {
        return resolveMongoTemplate().geoNear(near, entityClass, collectionName);
    }

    @Override
    public <T> T findAndModify(Query query, Update update, Class<T> entityClass) {
        return resolveMongoTemplate().findAndModify(query, update, entityClass);
    }

    @Override
    public <T> T findAndModify(Query query, Update update, Class<T> entityClass, String collectionName) {
        return resolveMongoTemplate().findAndModify(query, update, entityClass, collectionName);
    }

    @Override
    public <T> T findAndModify(Query query, Update update, FindAndModifyOptions options, Class<T> entityClass) {
        return resolveMongoTemplate().findAndModify(query, update, options, entityClass);
    }

    @Override
    public <T> T findAndModify(Query query, Update update, FindAndModifyOptions options, Class<T> entityClass, String collectionName) {
        return resolveMongoTemplate().findAndModify(query, update, options, entityClass, collectionName);
    }

    @Override
    public <T> T findAndRemove(Query query, Class<T> entityClass) {
        return resolveMongoTemplate().findAndRemove(query, entityClass);
    }

    @Override
    public <T> T findAndRemove(Query query, Class<T> entityClass, String collectionName) {
        return resolveMongoTemplate().findAndRemove(query, entityClass, collectionName);
    }

    @Override
    public long count(Query query, Class<?> entityClass) {
        return resolveMongoTemplate().count(query, entityClass);
    }

    @Override
    public long count(Query query, String collectionName) {
        return resolveMongoTemplate().count(query, collectionName);
    }

    @Override
    public long count(Query query, Class<?> entityClass, String collectionName) {
        return resolveMongoTemplate().count(query, entityClass, collectionName);
    }

    @Override
    public void insert(Object objectToSave) {
        resolveMongoTemplate().insert(objectToSave);
    }

    @Override
    public void insert(Object objectToSave, String collectionName) {
        resolveMongoTemplate().insert(objectToSave, collectionName);
    }

    @Override
    protected void ensureNotIterable(Object o) {
        resolveMongoTemplate().ensureNotIterable(o);
    }

    @Override
    protected void prepareCollection(DBCollection collection) {
        resolveMongoTemplate().prepareCollection(collection);
    }

    @Override
    protected WriteConcern prepareWriteConcern(MongoAction mongoAction) {
        return resolveMongoTemplate().prepareWriteConcern(mongoAction);
    }

    @Override
    protected <T> void doInsert(String collectionName, T objectToSave, MongoWriter<T> writer) {
        resolveMongoTemplate().doInsert(collectionName, objectToSave, writer);
    }

    @Override
    public void insert(Collection<?> batchToSave, Class<?> entityClass) {
        resolveMongoTemplate().insert(batchToSave, entityClass);
    }

    @Override
    public void insert(Collection<?> batchToSave, String collectionName) {
        resolveMongoTemplate().insert(batchToSave, collectionName);
    }

    @Override
    public void insertAll(Collection<?> objectsToSave) {
        resolveMongoTemplate().insertAll(objectsToSave);
    }

    @Override
    protected <T> void doInsertAll(Collection<? extends T> listToSave, MongoWriter<T> writer) {
        resolveMongoTemplate().doInsertAll(listToSave, writer);
    }

    @Override
    protected <T> void doInsertBatch(String collectionName, Collection<? extends T> batchToSave, MongoWriter<T> writer) {
        resolveMongoTemplate().doInsertBatch(collectionName, batchToSave, writer);
    }

    @Override
    public void save(Object objectToSave) {
        resolveMongoTemplate().save(objectToSave);
    }

    @Override
    public void save(Object objectToSave, String collectionName) {
        resolveMongoTemplate().save(objectToSave, collectionName);
    }

    @Override
    protected <T> void doSave(String collectionName, T objectToSave, MongoWriter<T> writer) {
        resolveMongoTemplate().doSave(collectionName, objectToSave, writer);
    }

    @Override
    protected Object insertDBObject(String collectionName, DBObject dbDoc, Class<?> entityClass) {
        return resolveMongoTemplate().insertDBObject(collectionName, dbDoc, entityClass);
    }

    @Override
    protected List<ObjectId> insertDBObjectList(String collectionName, List<DBObject> dbDocList) {
        return resolveMongoTemplate().insertDBObjectList(collectionName, dbDocList);
    }

    @Override
    protected Object saveDBObject(String collectionName, DBObject dbDoc, Class<?> entityClass) {
        return resolveMongoTemplate().saveDBObject(collectionName, dbDoc, entityClass);
    }

    @Override
    public WriteResult upsert(Query query, Update update, Class<?> entityClass) {
        return resolveMongoTemplate().upsert(query, update, entityClass);
    }

    @Override
    public WriteResult upsert(Query query, Update update, String collectionName) {
        return resolveMongoTemplate().upsert(query, update, collectionName);
    }

    @Override
    public WriteResult upsert(Query query, Update update, Class<?> entityClass, String collectionName) {
        return resolveMongoTemplate().upsert(query, update, entityClass, collectionName);
    }

    @Override
    public WriteResult updateFirst(Query query, Update update, Class<?> entityClass) {
        return resolveMongoTemplate().updateFirst(query, update, entityClass);
    }

    @Override
    public WriteResult updateFirst(Query query, Update update, String collectionName) {
        return resolveMongoTemplate().updateFirst(query, update, collectionName);
    }

    @Override
    public WriteResult updateFirst(Query query, Update update, Class<?> entityClass, String collectionName) {
        return resolveMongoTemplate().updateFirst(query, update, entityClass, collectionName);
    }

    @Override
    public WriteResult updateMulti(Query query, Update update, Class<?> entityClass) {
        return resolveMongoTemplate().updateMulti(query, update, entityClass);
    }

    @Override
    public WriteResult updateMulti(Query query, Update update, String collectionName) {
        return resolveMongoTemplate().updateMulti(query, update, collectionName);
    }

    @Override
    public WriteResult updateMulti(Query query, Update update, Class<?> entityClass, String collectionName) {
        return resolveMongoTemplate().updateMulti(query, update, entityClass, collectionName);
    }

    @Override
    protected WriteResult doUpdate(String collectionName, Query query, Update update, Class<?> entityClass, boolean upsert, boolean multi) {
        return resolveMongoTemplate().doUpdate(collectionName, query, update, entityClass, upsert, multi);
    }

    @Override
    public WriteResult remove(Object object) {
        return resolveMongoTemplate().remove(object);
    }

    @Override
    public WriteResult remove(Object object, String collection) {
        return resolveMongoTemplate().remove(object, collection);
    }

    @Override
    public WriteResult remove(Query query, String collectionName) {
        return resolveMongoTemplate().remove(query, collectionName);
    }

    @Override
    public WriteResult remove(Query query, Class<?> entityClass) {
        return resolveMongoTemplate().remove(query, entityClass);
    }

    @Override
    public WriteResult remove(Query query, Class<?> entityClass, String collectionName) {
        return resolveMongoTemplate().remove(query, entityClass, collectionName);
    }

    @Override
    protected <T> WriteResult doRemove(String collectionName, Query query, Class<T> entityClass) {
        return resolveMongoTemplate().doRemove(collectionName, query, entityClass);
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        return resolveMongoTemplate().findAll(entityClass);
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass, String collectionName) {
        return resolveMongoTemplate().findAll(entityClass, collectionName);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction, Class<T> entityClass) {
        return resolveMongoTemplate().mapReduce(inputCollectionName, mapFunction, reduceFunction, entityClass);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction, MapReduceOptions mapReduceOptions, Class<T> entityClass) {
        return resolveMongoTemplate().mapReduce(inputCollectionName, mapFunction, reduceFunction, mapReduceOptions, entityClass);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction, Class<T> entityClass) {
        return resolveMongoTemplate().mapReduce(query, inputCollectionName, mapFunction, reduceFunction, entityClass);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction, MapReduceOptions mapReduceOptions, Class<T> entityClass) {
        return resolveMongoTemplate().mapReduce(query, inputCollectionName, mapFunction, reduceFunction, mapReduceOptions, entityClass);
    }

    @Override
    public <T> GroupByResults<T> group(String inputCollectionName, GroupBy groupBy, Class<T> entityClass) {
        return resolveMongoTemplate().group(inputCollectionName, groupBy, entityClass);
    }

    @Override
    public <T> GroupByResults<T> group(Criteria criteria, String inputCollectionName, GroupBy groupBy, Class<T> entityClass) {
        return resolveMongoTemplate().group(criteria, inputCollectionName, groupBy, entityClass);
    }

    @Override
    public <O> AggregationResults<O> aggregate(TypedAggregation<?> aggregation, Class<O> outputType) {
        return resolveMongoTemplate().aggregate(aggregation, outputType);
    }

    @Override
    public <O> AggregationResults<O> aggregate(TypedAggregation<?> aggregation, String inputCollectionName, Class<O> outputType) {
        return resolveMongoTemplate().aggregate(aggregation, inputCollectionName, outputType);
    }

    @Override
    public <O> AggregationResults<O> aggregate(Aggregation aggregation, Class<?> inputType, Class<O> outputType) {
        return resolveMongoTemplate().aggregate(aggregation, inputType, outputType);
    }

    @Override
    public <O> AggregationResults<O> aggregate(Aggregation aggregation, String collectionName, Class<O> outputType) {
        return resolveMongoTemplate().aggregate(aggregation, collectionName, outputType);
    }

    @Override
    public <T> List<T> findAllAndRemove(Query query, String collectionName) {
        return resolveMongoTemplate().findAllAndRemove(query, collectionName);
    }

    @Override
    public <T> List<T> findAllAndRemove(Query query, Class<T> entityClass) {
        return resolveMongoTemplate().findAllAndRemove(query, entityClass);
    }

    @Override
    public <T> List<T> findAllAndRemove(Query query, Class<T> entityClass, String collectionName) {
        return resolveMongoTemplate().findAllAndRemove(query, entityClass, collectionName);
    }

    @Override
    protected <T> List<T> doFindAndDelete(String collectionName, Query query, Class<T> entityClass) {
        return resolveMongoTemplate().doFindAndDelete(collectionName, query, entityClass);
    }

    @Override
    protected <O> AggregationResults<O> aggregate(Aggregation aggregation, String collectionName, Class<O> outputType, AggregationOperationContext context) {
        return resolveMongoTemplate().aggregate(aggregation, collectionName, outputType, context);
    }

    @Override
    protected String replaceWithResourceIfNecessary(String function) {
        return resolveMongoTemplate().replaceWithResourceIfNecessary(function);
    }

    @Override
    public Set<String> getCollectionNames() {
        return resolveMongoTemplate().getCollectionNames();
    }

    @Override
    public DB getDb() {
        return resolveMongoTemplate().getDb();
    }

    @Override
    protected <T> void maybeEmitEvent(MongoMappingEvent<T> event) {
        resolveMongoTemplate().maybeEmitEvent(event);
    }

    @Override
    protected DBCollection doCreateCollection(String collectionName, DBObject collectionOptions) {
        return resolveMongoTemplate().doCreateCollection(collectionName, collectionOptions);
    }

    @Override
    protected <T> T doFindOne(String collectionName, DBObject query, DBObject fields, Class<T> entityClass) {
        return resolveMongoTemplate().doFindOne(collectionName, query, fields, entityClass);
    }

    @Override
    protected <T> List<T> doFind(String collectionName, DBObject query, DBObject fields, Class<T> entityClass) {
        return resolveMongoTemplate().doFind(collectionName, query, fields, entityClass);
    }

    @Override
    protected <T> List<T> doFind(String collectionName, DBObject query, DBObject fields, Class<T> entityClass, CursorPreparer preparer) {
        return resolveMongoTemplate().doFind(collectionName, query, fields, entityClass, preparer);
    }

    @Override
    protected <S, T> List<T> doFind(String collectionName, DBObject query, DBObject fields, Class<S> entityClass, CursorPreparer preparer, MongoTemplate.DbObjectCallback<T> objectCallback) {
        return resolveMongoTemplate().doFind(collectionName, query, fields, entityClass, preparer, objectCallback);
    }

    @Override
    protected DBObject convertToDbObject(CollectionOptions collectionOptions) {
        return resolveMongoTemplate().convertToDbObject(collectionOptions);
    }

    @Override
    protected <T> T doFindAndRemove(String collectionName, DBObject query, DBObject fields, DBObject sort, Class<T> entityClass) {
        return resolveMongoTemplate().doFindAndRemove(collectionName, query, fields, sort, entityClass);
    }

    @Override
    protected <T> T doFindAndModify(String collectionName, DBObject query, DBObject fields, DBObject sort, Class<T> entityClass, Update update, FindAndModifyOptions options) {
        return resolveMongoTemplate().doFindAndModify(collectionName, query, fields, sort, entityClass, update, options);
    }

    @Override
    protected void populateIdIfNecessary(Object savedObject, Object id) {
        resolveMongoTemplate().populateIdIfNecessary(savedObject, id);
    }

    @Override
    protected void handleAnyWriteResultErrors(WriteResult writeResult, DBObject query, MongoActionOperation operation) {
        resolveMongoTemplate().handleAnyWriteResultErrors(writeResult, query, operation);
    }
}
