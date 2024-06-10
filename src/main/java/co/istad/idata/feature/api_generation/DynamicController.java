package co.istad.idata.feature.api_generation;

import co.istad.idata.feature.user_definition.user_data.dto.UserDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/rest-api")
@RequiredArgsConstructor
public class DynamicController {

    private final DynamicServiceImpl dynamicService;

    @GetMapping("/{uuid}/{tableName}")
    public List<Map<String, Object>> getUserDataList(@PathVariable String uuid,
                                                      @PathVariable String tableName) {
        return dynamicService.getUserDataByUserAndType(uuid, tableName);
    }

    @GetMapping("/{uuid}/{tableName}/{id}")
    public Map<String, Object> getUserDataById(@PathVariable String uuid,
                                               @PathVariable String tableName,
                                               @PathVariable String id) {
        return dynamicService.getUserDataById(uuid, tableName, id);
    }

    @PostMapping("/{uuid}/{tableName}")
    public UserDataResponse createUserData(@PathVariable String uuid,
                                           @PathVariable String tableName,
                                           @RequestBody Map<String, Object> dataInput) throws IOException {
        return dynamicService.createUserData(uuid, tableName, dataInput);
    }

    @PutMapping("/{uuid}/{tableName}/{id}")
    public Map<String, Object> updateUserData(@PathVariable String uuid,
                                       @PathVariable String tableName,
                                       @PathVariable String id,
                                       @RequestBody Map<String, Object> data) throws IOException {
        return dynamicService.updateUserData(uuid, tableName, id, data);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}/{tableName}/{id}")
    public void deleteUserData(@PathVariable String uuid,
                               @PathVariable String tableName,
                               @PathVariable String id) {
        dynamicService.deleteUserData(uuid, tableName, id);
    }
}

