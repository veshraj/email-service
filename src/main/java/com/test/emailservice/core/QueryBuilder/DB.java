package com.test.emailservice.core.QueryBuilder;

import com.test.emailservice.EmailserviceApplication;
import com.test.emailservice.core.QueryBuilder.interfaces.DBWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DB<T> {
    DataSource dataSource;
    PreparedStatement preparedStatement;
    Connection connection;
    ResultSet resultSet;
    String queryString;
    String procedureQueryString;
    String orderString = "";
    String joinString = "";
    Logger logger = LoggerFactory.getLogger(DB.class);
    ArrayList<Object> list;
    private String SQLPattern = "";
    private String conditionStrings = "";
    private ArrayList<Object> params;
    private ArrayList<Object> callParams;
    private String tableName;
    private Boolean paginate = false;
    private int pageSize;
    private int pageNumber;
    private Map<String, Object> relationQueries = new HashMap<>();
    private String entityPrimaryKey = "";
    private List primaryIds = new ArrayList();
    private Map<String, List> relationalFieldValues;
    private String setQuery;

    /**
     * @param tableName
     * @return
     * @Description creates new object of DB class and all changes should be reflected to this object
     */
    public synchronized DB table(String tableName) {
        queryString = "";
        SQLPattern = "";
        conditionStrings = "";
        joinString = "";
        params = new ArrayList<>();
        this.tableName = "";
        resultSet = null;
        list = new ArrayList<>();
        this.tableName = tableName;
        entityPrimaryKey = "";
        relationQueries = new HashMap<>();
        relationalFieldValues = new HashMap<>();
        setQuery = "";
        return this;
    }

    /**
     * @param columns - name of list of columns
     * @return
     * @Description adds the columns in the select statement to be fired
     */
    public DB select(String columns) {
        this.SQLPattern += (this.SQLPattern.isEmpty()) ?columns : ", " + columns;
        return this;
    }

    /**
     * @param columns
     * @return
     * @Description adds the columns provided in array in the SELECT statuement to be fired
     */
    public synchronized DB select(String[] columns) {
        this.SQLPattern += String.join(", ", columns);
        return this;
    }

    /**
     * @param fieldName - Name of the column
     * @param operator  - relational operator used in database
     * @param value     - value to be used against the relational operator
     * @return
     * @Description this function add a predicate to the SELECT statement with operator
     */
    public synchronized DB where(String fieldName, String operator, Object value) {
        this.conditionStrings += (this.conditionStrings.isEmpty() ? "" : " AND ") + "(" + fieldName + " " + operator + " ?) ";
        params.add(value);
        return this;
    }

    /**
     * @param fieldName - name of the column
     * @param value     - predicate against the value
     * @return
     * @Description adds new predicate to the SELECT statement with = relational operator
     */
    public synchronized DB where(String fieldName, Object value) {
        this.conditionStrings += (this.conditionStrings.isEmpty() ? "" : " AND ") + "(" + fieldName + " =  ? ) ";
        params.add(value);
        return this;
    }

    /**
     * @param fieldName - name of the field
     * @return
     * @Description add predicate to the SELECT statement with IS NULL relational operator
     */
    public synchronized DB whereNull(String fieldName) {
        this.conditionStrings += (this.conditionStrings.isEmpty() ? "" : " AND ") + "( " + fieldName + " IS NULL ) ";
        return this;
    }

    /**
     * @return
     * @throws SQLException
     * @Description return the result of SELECT statement
     */
    public synchronized ResultSet getResult() {
        if (dataSource == null) {
            dataSource = EmailserviceApplication.getDataSource();
        }
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
            }

            preparedStatement = connection.prepareStatement(this.getRawQuery());
            bindParameters();
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException sqlie) {
            logger.debug(sqlie.getMessage());
        }
        return resultSet;
    }


    /**
     * @param queryString
     * @return
     */
    public synchronized ResultSet getResult(String queryString) {
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
            }
            preparedStatement = connection.prepareStatement(queryString);
            bindParameters();
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException sqlie) {
            logger.debug(sqlie.getMessage());
        }
        return resultSet;
    }

    public synchronized ArrayList<Object> getParams() {
        return params;
    }

    /**
     * @param pstmt
     * @param index
     * @param value
     * @throws SQLException
     */
    public synchronized void setInt(PreparedStatement pstmt, int index, Object value) throws SQLException {
        if (value instanceof Integer) {
            pstmt.setInt(index, (int) value);
        }
    }

    /**
     * @param pstmt
     * @param index
     * @param value
     * @throws SQLException
     */
    public synchronized void setLong(PreparedStatement pstmt, int index, Object value) throws SQLException {
        if (value instanceof Long) {
            pstmt.setLong(index, (long) value);
        }
    }

    /**
     * @param pstmt
     * @param index
     * @param value
     * @throws SQLException
     */
    public synchronized void setDouble(PreparedStatement pstmt, int index, Object value) throws SQLException {
        if (value instanceof Double) {
            pstmt.setDouble(index, (double) value);
        }
    }

    /**
     * @param pstmt
     * @param index
     * @param value
     * @throws SQLException
     */
    public synchronized void setFloat(PreparedStatement pstmt, int index, Object value) throws SQLException {
        if (value instanceof Float) {
            pstmt.setFloat(index, (float) value);
        }
    }

    /**
     * @param pstmt
     * @param index
     * @param value
     * @throws SQLException
     */
    public synchronized void setString(PreparedStatement pstmt, int index, Object value) throws SQLException {
        if (value instanceof String) {
            pstmt.setString(index, value.toString());
        }
    }

    /**
     * @param pstmt prepare statement  used in current object
     * @param index index of parameterized query used in predicate
     * @param value value of the predicate
     * @throws SQLException
     */
    public synchronized void setDate(PreparedStatement pstmt, int index, Object value) throws SQLException {
        if (value instanceof Date) {
            pstmt.setDate(index, (Date) value);
        }

        if (value instanceof Timestamp) {
            pstmt.setTimestamp(index, (Timestamp) value);
        }
    }

    /**
     * @param fieldName
     * @param value
     * @return current object
     */
    public synchronized DB orWhere(String fieldName, Object value) {
        this.conditionStrings += (this.conditionStrings.isEmpty() ? "" : " OR ") + "(" + fieldName + " = ?) ";
        params.add(value);
        return this;
    }

    /**
     * @param fieldName
     * @param operator
     * @param value
     * @return current object
     */
    public synchronized DB orWhere(String fieldName, String operator, Object value) {
        this.conditionStrings += (this.conditionStrings.isEmpty() ? "" : " OR ") + "(" + fieldName + " " + operator + " ?) ";
        params.add(value);
        return this;
    }

    /**
     * @param orWhere
     * @return current object
     */
    public synchronized DB orWhere(DBWhere orWhere) {
        DB conditionState = orWhere.whereCondition(new DB());
        this.conditionStrings += "OR (" + conditionState.conditionStrings + ")";
        this.params.addAll(conditionState.getParams());
        return this;
    }

    /**
     * @param orWhere
     * @return current object
     */
    public synchronized DB where(DBWhere orWhere) {
        DB conditionState = orWhere.whereCondition(new DB());
        this.conditionStrings += "AND (" + conditionState.conditionStrings + ")";
        this.params.addAll(conditionState.getParams());
        return this;
    }

    /**
     * @param fieldName
     * @param values
     * @return
     */
    public synchronized DB whereIn(String fieldName, Object[] values) {
        this.conditionStrings += (this.conditionStrings.isEmpty() ? "" : " AND ") + "(" + fieldName + " IN (";
        int i = 0;
        for (Object value : values) {
            this.conditionStrings += ((i > 0) ? ", ?" : "?");
            this.params.add(value);
            i++;
        }
        this.conditionStrings += "))";


        return this;
    }

    /**
     * @return String
     * @Description compose current SELECT statement to be fired
     */
    public synchronized String getRawQuery() {
        queryString = "SELECT " + ((this.SQLPattern.isEmpty() ? " * " : this.SQLPattern)) + " FROM " + this.tableName + " " + joinString;
        if (!this.conditionStrings.isEmpty()) {
            queryString += " WHERE " + this.conditionStrings;
        }
        return queryString + ((paginate) ? " LIMIT "
                                                   + String.valueOf(pageSize) + " OFFSET "
                                                   + ((pageNumber - 1) * pageSize) : "");
    }

    /**
     * @return
     */
    public synchronized String getCountRawQeury() {
        queryString = "SELECT count(*) as totalItems FROM " + this.tableName + " " + joinString;
        if (!this.conditionStrings.isEmpty()) {
            queryString += " WHERE " + this.conditionStrings;
        }
        return queryString;
    }

    /**
     * @param joiningTable
     * @param leftTableJoin
     * @param operator
     * @param rightTableJoin
     * @return
     */
    public synchronized DB join(String joiningTable, String leftTableJoin, String operator, String rightTableJoin) {
        joinString += " JOIN " + joiningTable + " ON " + leftTableJoin + " = " + rightTableJoin;
        return this;
    }

    /**
     * @param joiningTable
     * @param leftTableJoin
     * @param operator
     * @param rightTableJoin
     * @return
     */
    public synchronized DB leftJoin(String joiningTable, String leftTableJoin, String operator, String rightTableJoin) {
        joinString += " LEFT JOIN " + joiningTable + " ON " + leftTableJoin + " = " + rightTableJoin;
        return this;
    }

    /**
     * @param joiningTable
     * @param leftTableJoin
     * @param operator
     * @param rightTableJoin
     * @return
     */
    public synchronized DB rightJoin(String joiningTable, String leftTableJoin, String operator, String rightTableJoin) {
        joinString += " RIGHT JOIN " + joiningTable + " ON " + leftTableJoin + " = " + rightTableJoin;
        return this;
    }

    /**
     * @param className
     * @return
     */

    public synchronized List<Object> map(Class className) {
        primaryIds = new ArrayList();
        try {

            Object object = Class.forName(className.getName()).getDeclaredConstructor().newInstance();
            ResultSet resultSet = getResult();
            if (resultSet != null) {
                while (resultSet.next()) {
                    primaryIds.add(resultSet.getObject(1));
                    Method method = object.getClass().getDeclaredMethod("map", ResultSet.class);
                    list.add(method.invoke(object, resultSet));
                }
            }
            mapRelations();

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            logger.debug(e.getMessage());
        }
        return list;
    }

    public synchronized Object mapRelations(Class className, ResultSet resultSet) {
        Object relationObject = new Object();
        try {
            Object object = Class.forName(className.getName()).getDeclaredConstructor().newInstance();
            if (resultSet != null) {
                Method method = object.getClass().getDeclaredMethod("map", ResultSet.class);
                relationObject = method.invoke(object, resultSet);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            logger.debug(e.getMessage());
        }
        return relationObject;
    }

    /**
     * @param className
     * @return
     */
    public synchronized Object first(Class className) {
        try {
            Object object = Class.forName(className.getName()).getDeclaredConstructor().newInstance();
            ResultSet resultSet = getResult();
            if (resultSet != null && resultSet.next()) {
                resultSet.first();
                Method method = object.getClass().getDeclaredMethod("map", ResultSet.class);
                return method.invoke(object, resultSet);
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
        }
        return null;
    }

    /**
     * @param className
     * @return
     */
    public synchronized Object last(Class className) throws Exception {
        try {

            Object object = Class.forName(className.getName()).getDeclaredConstructor().newInstance();
            ResultSet resultSet = getResult();
            if (resultSet != null && resultSet.next()) {
                resultSet.last();
                Method method = object.getClass().getDeclaredMethod("map", ResultSet.class);
                return method.invoke(object, resultSet);
            }
        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | IllegalArgumentException | InstantiationException e) {
            logger.debug(e.getMessage());
        }
        logger.debug("No record found");
        throw new Exception("Record not found");
    }

    /**
     * @param fieldName
     * @param order
     * @return
     */
    public synchronized DB sortBy(String fieldName, String order) {
        orderString = " ORDER BY " + fieldName + " " + order;
        return this;
    }

    /**
     * @param relationFieldName
     * @param entity
     * @param relationPresenter
     * @return
     */
    public synchronized DB with(String relationFieldName, Class entity, Class relationPresenter) {
        try {
            Object entityObject = Class.forName(entity.getName()).getDeclaredConstructor().newInstance();
            Object presenterObject = Class.forName(relationPresenter.getName()).getDeclaredConstructor().newInstance();
            Map<String, Object> relationInfo = new HashMap();


            Field relationField = entityObject.getClass().getDeclaredField(relationFieldName);
            Annotation[] annotations = relationField.getDeclaredAnnotations();
            int annotationCount = annotations.length;

            for (int i = 0; i < annotationCount; i++) {
                Annotation annotation = annotations[i];
                if (annotation.annotationType().equals(javax.persistence.ManyToMany.class)) {
                    relationInfo.put("presenter", relationPresenter);
                    relationInfo.put("type", "ManyToMany");
                    relationInfo.putAll(manageManyToManyRelation(annotations));
                    relationQueries.put(relationFieldName, relationInfo);
                    this.entityPrimaryKey = "";
                    break;
                } else if (annotation.annotationType().equals(javax.persistence.ManyToOne.class)) {
                    relationInfo.put("presenter", relationPresenter);
                    relationInfo.put("type", "ManyToOne");
                    relationInfo.putAll(manageManyToOneRelation(annotations));
                    relationQueries.put(relationFieldName, relationInfo);
                    break;
                } else if (annotation.annotationType().equals(javax.persistence.OneToMany.class)) {
                    relationInfo.put("presenter", relationPresenter);
                    relationInfo.put("type", "OneToMany");
                    relationInfo.putAll(manageOneToMany(annotations));
                    relationQueries.put(relationFieldName, relationInfo);
                    break;
                } else if (annotation.annotationType().equals(javax.persistence.OneToOne.class)) {
                    relationQueries.put(relationFieldName, manageOneToOne(annotations));
                    break;
                }
            }

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
        return this;
    }

    /**
     * @param relationFieldName
     * @param entity
     * @param presenter
     * @param entitiyPrimaryKey
     * @return
     */
    public synchronized DB with(String relationFieldName, Class entity, Class presenter, String entitiyPrimaryKey) {
        this.entityPrimaryKey = entitiyPrimaryKey;
        with(relationFieldName, entity, presenter);
        return this;
    }

    /**
     * @param annotations
     */
    private synchronized Map<String, Object> manageOneToMany(Annotation[] annotations) {
        Map<String, Object> relationInfo = new HashMap<>();
        String relationTable = "";
        String foreignKey = "";

        try {
            for (Annotation annotation : annotations) {
                /**
                 * this checks the many to many relation
                 */

                if (annotation.annotationType().equals(javax.persistence.OneToMany.class)) {
                    relationTable = (String) getEntityClassTableName((Class) getAnnotationValues(annotation, "targetEntity"), "name");
                }
                if (annotation.annotationType().equals(javax.persistence.JoinColumn.class)) {
                    /**
                     * joinColumns are in array for now we are checking for single item
                     */
                    foreignKey = (String) getAnnotationValues(annotation, "name");
                }
            }
            relationInfo.put("query", " SELECT * FROM " + relationTable
                                              + " WHERE " + foreignKey + " in ");
            String primaryKey = ((this.entityPrimaryKey != null && this.entityPrimaryKey.isEmpty()) ? "id" : this.entityPrimaryKey);
            relationInfo.put("resultSetKey", foreignKey);
            relationInfo.put("getter", getGetterName(primaryKey));
            relationInfo.put("getterKey", primaryKey);
            return relationInfo;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
        }
        return relationInfo;
    }

    /**
     * @param annotations
     */
    private synchronized String manageOneToOne(Annotation[] annotations) {
        return "";
    }

    /**
     * @param annotations
     */
    private Map<String, Object> manageManyToOneRelation(Annotation[] annotations) {
        Map<String, Object> relationInfo = new HashMap<>();
        String relationTable = "";
        String foreignKey = "";
        ;
        try {
            for (Annotation annotation : annotations) {
                /**
                 * this checks the many to many relation
                 */

                if (annotation.annotationType().equals(javax.persistence.ManyToOne.class)) {
                    relationTable = (String) getEntityClassTableName((Class) getAnnotationValues(annotation, "targetEntity"), "name");
                }

                if (annotation.annotationType().equals(javax.persistence.JoinColumn.class)) {
                    /**
                     * joinColumns are in array for now we are checking for single item
                     */
                    foreignKey = (String) getAnnotationValues(annotation, "name");
                }
            }
            String primaryKey = ((this.entityPrimaryKey != null && this.entityPrimaryKey.isEmpty()) ? "id" : this.entityPrimaryKey);
            relationInfo.put("query", " SELECT * FROM " + relationTable
                                              + " WHERE " + primaryKey + " in ");

            relationInfo.put("resultSetKey", primaryKey);
            relationInfo.put("getter", getGetterName(foreignKey));
            relationInfo.put("getterKey", foreignKey);
            return relationInfo;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
        }

        return relationInfo;
    }

    /**
     * @param annotations
     */
    private synchronized Map<String, Object> manageManyToManyRelation(Annotation[] annotations) {
        Map<String, Object> relationInfo = new HashMap<>();
        String relationTable = "";
        String joinTableName = "";
        String joinColumnsName = "";
        String inverseJoinColumnsName = "";
        try {
            for (Annotation annotation : annotations) {
                /**
                 * this checks the many to many relation
                 */

                if (annotation.annotationType().equals(javax.persistence.ManyToMany.class)) {
//                    getEntityClassPrimaryKey((Class) getAnnotationValues(annotation, "targetEntity"));
                    relationTable = (String) getEntityClassTableName((Class) getAnnotationValues(annotation, "targetEntity"), "name");
                }
                if (annotation.annotationType().equals(javax.persistence.JoinTable.class)) {
                    joinTableName = (String) getAnnotationValues(annotation, "name");
                    /**
                     * joinColumns are in array for now we are checking for single item
                     */
                    Annotation[] joinColumnAnnotations = (Annotation[]) getAnnotationValues(annotation, "joinColumns");
                    inverseJoinColumnsName = (String) getAnnotationValues(joinColumnAnnotations[0], "name");
                    /**
                     * inverseJoinColumns are in array for now we are checking for single item
                     */
                    Annotation[] inverseJoinColumnAnnotations = (Annotation[]) getAnnotationValues(annotation, "inverseJoinColumns");
                    joinColumnsName = (String) getAnnotationValues(inverseJoinColumnAnnotations[0], "name");
                }
            }
            String primaryKey = ((this.entityPrimaryKey != null && this.entityPrimaryKey.isEmpty()) ? "id" : this.entityPrimaryKey);

            relationInfo.put("query", " SELECT * FROM " + relationTable
                                              + " INNER JOIN " + joinTableName + " ON "
                                              + relationTable + "." + primaryKey
                                              + " = " + joinTableName + "." + joinColumnsName
                                              + " WHERE " + inverseJoinColumnsName + " in ");
            relationInfo.put("resultSetKey", inverseJoinColumnsName);
            relationInfo.put("getter", getGetterName(primaryKey));
            relationInfo.put("getterKey", primaryKey);
            return relationInfo;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
        }


        return relationInfo;
    }

    /**
     * @param pagination
     * @param classPath
     * @return
     */
//    public synchronized Map<String, Object> paginate(Pagination pagination, Class classPath) {
//        paginate = true;
//        pageSize = pagination.getPageSize();
//        pageNumber = pagination.getPage();
//
//        Map<String, Object> paginateMap = new HashMap<String, Object>();
//        paginateMap.put("data", map(classPath));
//        paginateMap.put("currentPage", pageNumber);
//        paginateMap.put("pageSize", pagination.getPageSize());
//        paginateMap.put("totalItems", 0);
//        paginateMap.put("from", 0);
//        paginateMap.put("to", 0);
//        paginateMap.put("lastPage", 0);
//
//        try {
//            ResultSet resultSet = getResult(getCountRawQeury());
//            if (resultSet != null) {
//                resultSet.next();
//                int form = (pagination.getPage() - 1) * pagination.getPageSize() + 1;
//                int to = pagination.getPage() * pagination.getPageSize();
//                paginateMap.put("totalItems", resultSet.getInt("totalItems"));
//                paginateMap.put("from", form);
//                paginateMap.put("to", (to > resultSet.getInt("totalItems")) ? resultSet.getInt("totalItems") : to);
//                paginateMap.put("lastPage", resultSet.getInt("totalItems") / pageSize + ((resultSet.getInt("totalItems") % pageSize == 0) ? 0 : 1));
//            }
//        } catch (SQLException e) {
//            logger.debug(e.getMessage());
//        }
//        return paginateMap;
//    }

    /**
     * bind the parameters in the prepared statements
     */
    private synchronized void bindParameters() {
        int paramLength = params.size();
        for (int index = 1; index <= paramLength; index++) {
            Object obj = this.params.get(index - 1);
            System.out.println(obj.toString());
            try {
                this.setInt(preparedStatement, index, obj);
                this.setLong(preparedStatement, index, obj);
                this.setDouble(preparedStatement, index, obj);
                this.setFloat(preparedStatement, index, obj);
                this.setString(preparedStatement, index, obj);
                this.setDate(preparedStatement, index, obj);
            } catch (SQLException e) {
                logger.debug(e.getMessage());
            }
        }
    }

    private synchronized void bindParameters(ArrayList params) {
        this.params = params;
        bindParameters();
    }


    /**
     * @param entityClass
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public synchronized Object getEntityClassTableName(Class entityClass, String functionName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Annotation annotation = entityClass.getAnnotation(javax.persistence.Table.class);
        Method method = annotation.getClass().getDeclaredMethod(functionName);
        return method.invoke(annotation);
    }

    private synchronized String getEntityClassPrimaryKey(Class entityClass) {
        Field[] fields = entityClass.getFields();
        Field[] primaryKey = (Field[]) Arrays.stream(fields).filter(field -> {
            return field.getDeclaredAnnotation(entityClass).equals(javax.persistence.Id.class);
        })
                                               .toArray();
        return "id";

    }

    /**
     * @param annotation
     * @param functionName
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private synchronized Object getAnnotationValues(Object annotation, String functionName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = annotation.getClass().getDeclaredMethod(functionName);
        return method.invoke(annotation);
    }

    private synchronized Map<String, List> mapRelations() {
        Map<String, ResultSet> relationMap = new HashMap<>();
        setProperQueriesToRealtions();
        relationQueries.entrySet().stream().forEach(relation -> {

            Map<String, Object> relationInfo = (HashMap) relation.getValue();
            params = new ArrayList<>();
            ResultSet resultSet = getResult((String) relationInfo.get("query"));
            relationMap.put(relation.getKey(), resultSet);
        });

        list.stream().forEach(object -> {
            relationMap.entrySet().stream().forEach(relationInfo -> {
                try {
                    String relationName = relationInfo.getKey();
                    Map relationQuery = (HashMap) relationQueries.get(relationName);
                    Class presenter = (Class) relationQuery.get("presenter");
                    Object relationObject = Class.forName(presenter.getName()).getDeclaredConstructor().newInstance();

                    List relationalList = new ArrayList();

                    ResultSet rs = relationInfo.getValue();
                    while (rs.next()) {
                        Method method = object.getClass().getDeclaredMethod(relationQuery.get("getter").toString());
                        String id = method.invoke(object).toString();

                        if (rs.getObject(relationQuery.get("resultSetKey").toString()).toString().equals(id)) {
                            Method methodSetRelationObject = presenter.getDeclaredMethod("map", ResultSet.class);
                            relationalList.add(methodSetRelationObject.invoke(relationObject, rs));
                        }
                    }
                    rs.beforeFirst();
                    if (relationQuery.get("type").toString().equals("ManyToOne") || relationQuery.get("type").toString().equals("OneToOne")) {
                        Method method = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(relationInfo.getKey()), Object.class);
                        method.invoke(object, relationalList.get(0));
                    } else {
                        Method method = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(relationInfo.getKey()), List.class);
                        method.invoke(object, relationalList);
                    }

                } catch (NoSuchMethodException | IllegalAccessException | SQLException | InvocationTargetException | ClassNotFoundException | InstantiationException | IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            });
        });
        return new HashMap<>();
    }

    private synchronized String getRelationalFieldValues(String fieldName) {

        List fieldValues = new ArrayList();
        if (relationalFieldValues.get(fieldName) == null) {

            try {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    fieldValues.add(resultSet.getObject(fieldName));
                }
            } catch (SQLException sqlie) {
                sqlie.printStackTrace();
                logger.info(sqlie.toString());
            }
        }

        return (String) fieldValues.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    /**
     * @Description
     */
    private void setProperQueriesToRealtions() {
        List strings = Arrays.asList(("module_id").split("_"));
        relationQueries.entrySet().stream().forEach(relation -> {
            Map<String, Object> relationInfo = (HashMap) relation.getValue();
            relationInfo.put("query", relationInfo.get("query").toString() + "(" + getRelationalFieldValues(relationInfo.get("getterKey").toString()) + ")");
        });
    }

    private String getGetterName(String fieldName) {
        return "get" + Arrays.asList(fieldName.split("_")).stream().map(string -> StringUtils.capitalize(string.toString())).collect(Collectors.joining(""));
    }

    public synchronized DB callProc(String procedureName, List paramValues) {
        callParams = new ArrayList<>();
        procedureQueryString = "{ CALL " + procedureName + "(";
        int paramSize = paramValues.size();
        for (int i =0 ; i < paramSize; i++) {
            procedureQueryString += (i == 0) ? "?" : ", ? ";
        }

        procedureQueryString += ") }";
        callParams.addAll(paramValues);
        return this;
    }

    public synchronized String getCallRawQuery() {
        return procedureQueryString;
    }

    public synchronized ResultSet getProcResult() {
        if (dataSource == null) {
            dataSource = EmailserviceApplication.getDataSource();
        }
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
            }

            preparedStatement = connection.prepareCall(this.getCallRawQuery());
            bindParameters(callParams);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException sqlie) {
            logger.debug(sqlie.getMessage());
        }
        return resultSet;
    }

    public synchronized DB set(Map<String, Object> valueSet) {

        valueSet.forEach((key, value)->{
            setQuery += ((setQuery.isEmpty())? key:", "+key)+" = ?";
            params.add(value);
        });
        queryString = " SET "+setQuery;

        return this;
    }

    public synchronized int update() {
        if (dataSource == null) {
            dataSource = EmailserviceApplication.getDataSource();
        }
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
            }
            String updateQuery = "UPDATE "+tableName+queryString+joinString;
            if (!this.conditionStrings.isEmpty()) {
                queryString += " WHERE " + this.conditionStrings;
            }
            preparedStatement = connection.prepareStatement(updateQuery);
            bindParameters();

            return preparedStatement.executeUpdate();
        } catch (SQLException sqlie) {
            logger.debug(sqlie.getMessage());
        }
        return 0;
    }

}
