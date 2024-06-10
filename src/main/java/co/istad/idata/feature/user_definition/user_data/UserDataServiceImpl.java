package co.istad.idata.feature.user_definition.user_data;

import co.istad.idata.domains.User;
import co.istad.idata.domains.UserData;
import co.istad.idata.domains.UserDefinition;
import co.istad.idata.feature.api_generation.json_field.DefinitionSchema;
import co.istad.idata.feature.api_generation.json_field.json_property.Property;
import co.istad.idata.feature.api_generation.json_field.json_property.RelationshipKey;
import co.istad.idata.feature.api_generation.json_field.UserJsonData;
import co.istad.idata.feature.user.UserRepository;
import co.istad.idata.feature.user_definition.UserDefinitionRepository;
import co.istad.idata.feature.user_definition.user_data.dto.UserDataResponse;
import co.istad.idata.mapper.UserDataMapper;
import co.istad.idata.util.DataTypeParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataServiceImpl implements UserDataService{

    private final UserDefinitionRepository userDefinitionRepository;
    private final UserDataRepository userDataRepository;
    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;

    @Override
    public UserDataResponse createUserData(String uuid, String tableName, Map<String, Object> inputData) {

        UserDefinition definition = userDefinitionRepository.findByTableName(tableName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Definition has not been found"));

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        // Retrieve the UserData list associated with the user and table name
        List<UserData> userDataList = userDataRepository.findByUserAndTableName(user, definition.getTableName());

        UserData userData;
        UserJsonData existingUserJsonData;

        if (userDataList.isEmpty()) {

            // Create new UserData if none exists
            userData = new UserData();
            userData.setId(UUID.randomUUID().toString());
            userData.setUser(user);
            userData.setDefinition(definition);
            userData.setTableName(tableName);

            existingUserJsonData = new UserJsonData();
            existingUserJsonData.setData(new ArrayList<>());
            userData.setJsonData(existingUserJsonData);

        } else {

            // Use the existing UserData if it already exists
            userData = userDataList.get(0);
            existingUserJsonData = userData.getJsonData();

        }

        // Retrieve existing data
        List<Map<String, Object>> existingData = existingUserJsonData.getData();
        if (existingData == null) {
            existingData = new ArrayList<>();
        }

        // Create a map for new data
        List<Property> properties = definition.getSchema().getProperties();

        Map<String, Object> dataMap = new HashMap<>();

        for (Property attribute : properties) {

            String columnName = attribute.getColumnName();

            if (columnName.equals("id")) {

                dataMap.put(columnName, UUID.randomUUID().toString());
                continue;

            }
            String dataType = attribute.getDataType();
            Object value = inputData.get(columnName);

            if (value != null) {

                Object parsedValue = DataTypeParser.parse(value.toString(), dataType);
                dataMap.put(columnName, parsedValue);

            }

        }

        // Append new data to existing data
        existingData.add(dataMap);

        // Update userJsonData with the appended data
        existingUserJsonData.setData(existingData);

        // Update userData with the updated userJsonData
        userData.setJsonData(existingUserJsonData);

        // Save updated userData back to the repository
        userDataRepository.save(userData);

        return userDataMapper.toUserDataResponse(userData);
    }


    @Override
    public Map<String, Object> updateUserData(String uuid, String tableName, String id, Map<String, Object> inputData) {

        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User has not been found"
            );
        }

        UserDefinition definition = userDefinitionRepository.findByTableName(tableName)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Definition has not been found"
                        )
                );

        UserData existsData = definition.getUserData().get(0);

        List<Map<String, Object>> existsJsonData = existsData.getJsonData().getData();

        Map<String, Object> entryToUpdate = existsJsonData.stream()
                .filter(data -> id.equals(data.get("id")))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No JSON data found with the given ID"
                        )
                );

        for (Property property: definition.getSchema().getProperties()) {

            String columnName = property.getColumnName();

            if (!"id".equals(columnName)) {
                String dataType = property.getDataType();
                Object value = inputData.get(columnName);
                if (value != null) {
                    Object parsedValue = DataTypeParser.parse(value.toString(), dataType);
                    entryToUpdate.put(columnName, parsedValue);
                }
            }
        }

        userDataRepository.save(existsData);

        return entryToUpdate;

    }

    @Override
    public List<Map<String, Object>> getUserJsonData(String uuid, String tableName) {

        if (!(userRepository.existsByUuid(uuid) && userDefinitionRepository.existsByTableName(tableName))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't find data, please try again");
        }

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        List<UserData> userDataList = userDataRepository.findByUserAndTableName(user, tableName);

        if (userDataList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User data has not been found");
        }

        // Assuming we return data for the first matched entry
        UserJsonData jsonData = userDataList.get(0).getJsonData();
        List<Map<String, Object>> data = jsonData.getData();

        UserDefinition definition = userDataList.get(0).getDefinition();
        DefinitionSchema schema = definition.getSchema();

        List<RelationshipKey> keys = schema.getKeys();

        for (Map<String, Object> entry : data) {
            setJsonData(entry, keys);
        }

        return data;

    }

    private Map<String, Object> fetchRelatedObject(String foreignKey, String tableName) {

        if (!userDefinitionRepository.existsByTableName(tableName)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Table has not been found"
            );
        }

        List<UserData> userDataList = userDataRepository.findByTableName(tableName)
                .stream().toList();

        if (userDataList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, tableName + " data not found");
        }

        for (UserData userData : userDataList) {
            UserJsonData jsonData = userData.getJsonData();
            List<Map<String, Object>> data = jsonData.getData();

            for (Map<String, Object> entry : data) {
                if (foreignKey.equals(entry.get("id"))) {
                    return entry;
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No related data found with the given ID");
    }

    @Override
    public Map<String, Object> getUserJsonDataById(String uuid, String tableName, String id) {

        if (!(userRepository.existsByUuid(uuid) && userDefinitionRepository.existsByTableName(tableName))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't find data, please try again");
        }

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        List<UserData> userDataList = userDataRepository.findByUserAndTableName(user, tableName);

        if (userDataList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User data has not been found");
        }

        UserJsonData jsonData = userDataList.get(0).getJsonData();

        Map<String, Object> data = jsonData.getData().stream()
                .filter(entry -> id.equals(entry.get("id")))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Data has not been found"
                        )
                );

        UserDefinition definition = userDataList.get(0).getDefinition();
        List<RelationshipKey> keys = definition.getSchema().getKeys();

        setJsonData(data, keys);

        return data;
    }

    private void setJsonData(Map<String, Object> data, List<RelationshipKey> keys) {
        for (RelationshipKey key: keys) {
            if (key.isForeignKey()) {
                String foreignKey = (String) data.get(key.getColumnName());
                if (foreignKey != null) {
                    Map<String, Object> relatedObject = fetchRelatedObject(foreignKey, key.getReferenceTable());
                    data.put(key.getColumnName().replace("Id".toLowerCase(), ""), relatedObject);
                    data.remove(key.getColumnName());
                }
            }
        }
    }

    @Override
    public void deleteUserData(String uuid, String tableName, String id) {

        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User has not been found"
            );
        }

        if (!userDataRepository.existsByTableName(tableName)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "tABLE has not been found"
            );
        }

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        List<UserData> userDataList = userDataRepository.findByUserAndTableName(user, tableName);

        if (userDataList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User data has not been found");
        }

        UserData existedUserData = userDataList.get(0);

        List<Map<String, Object>> existedData = existedUserData.getJsonData().getData();

        boolean removed = existedData.removeIf(deleteData -> deleteData.get("id").equals(id));

        if (removed) {
            existedUserData.setJsonData(existedUserData.getJsonData());
            userDataRepository.save(existedUserData);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data with the given ID is not found");
        }
    }
}
