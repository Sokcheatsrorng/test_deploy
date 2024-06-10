package co.istad.idata.feature.api_generation;

import co.istad.idata.feature.user_definition.user_data.dto.UserDataResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DynamicService {

    List<Map<String, Object>> getUserDataByUserAndType(String uuid, String tableName);

    Map<String, Object> getUserDataById(String uuid, String tableName, String id);

    UserDataResponse createUserData(String uuid, String tableName, Map<String, Object> data) throws IOException;

    Map<String, Object> updateUserData(String uuid, String tableName, String id, Map<String, Object> data) throws IOException;

    void deleteUserData(String uuid, String tableName, String id);


}
