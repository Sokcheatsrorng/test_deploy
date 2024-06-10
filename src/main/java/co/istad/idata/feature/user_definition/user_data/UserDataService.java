package co.istad.idata.feature.user_definition.user_data;

import co.istad.idata.feature.user_definition.user_data.dto.UserDataResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserDataService {

    UserDataResponse createUserData(String uuid, String tableName, Map<String, Object> inputData) throws IOException;

    Map<String, Object> updateUserData(String uuid, String tableName, String id, Map<String, Object> inputData) throws IOException;

    List<Map<String, Object>> getUserJsonData(String uuid, String tableName);

    Map<String, Object> getUserJsonDataById(String uuid, String tableName, String id);

    void deleteUserData(String uuid, String tableName, String id);


}
