package co.istad.idata.feature.api_generation;

import co.istad.idata.feature.user_definition.user_data.UserDataService;
import co.istad.idata.feature.user_definition.user_data.dto.UserDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DynamicServiceImpl implements DynamicService{

    private final UserDataService userDataService;

    @Override
    public void deleteUserData(String uuid, String tableName, String id) {

        userDataService.deleteUserData(uuid, tableName, id);
    }

    @Override
    public UserDataResponse createUserData(String uuid, String tableName, Map<String, Object> data) throws IOException {

        return userDataService.createUserData(uuid, tableName, data);

    }

    @Override
    public Map<String, Object> getUserDataById(String uuid, String tableName, String id) {
        return userDataService.getUserJsonDataById(uuid, tableName, id);
    }

    @Override
    public List<Map<String, Object>> getUserDataByUserAndType(String uuid, String tableName) {
        return userDataService.getUserJsonData(uuid, tableName);
    }

    @Override
    public Map<String, Object> updateUserData(String uuid, String tableName, String id, Map<String, Object> data) throws IOException {
        return userDataService.updateUserData(uuid, tableName, id, data);
    }


}
