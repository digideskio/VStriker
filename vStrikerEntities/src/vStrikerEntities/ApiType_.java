package vStrikerEntities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-30T13:37:45.107-0800")
@StaticMetamodel(ApiType.class)
public class ApiType_ {
	public static volatile SingularAttribute<ApiType, Integer> apiTypeId;
	public static volatile SingularAttribute<ApiType, String> apiTypeDescription;
	public static volatile SingularAttribute<ApiType, String> apiTypeName;
	public static volatile SingularAttribute<ApiType, String> apiTypeUrl;
	public static volatile SingularAttribute<ApiType, CrudOperationType> crudOperationType;
}