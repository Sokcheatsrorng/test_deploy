package co.istad.idata.feature.user_definition;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.feature.user_definition.dto.DefinitionRequest;
import co.istad.idata.feature.user_definition.dto.DefinitionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/definition")
public class UserDefinitionController {

    private final UserDefinitionService userDefinitionService;

    @PostMapping
    public void createDefinition(@Valid @RequestBody DefinitionRequest definitionRequest) {
        userDefinitionService.createUserDefinition(definitionRequest);
    }

    @GetMapping
    List<DefinitionResponse> findAll() {

        return userDefinitionService.findAll();

    }

    @PutMapping("/{id}")
    BasedMessage updateTableName(@PathVariable Long id,
                                 @RequestParam String tableName) {

        return userDefinitionService.updateTableName(id, tableName);

    }

    @GetMapping("/{tableName}")
    DefinitionResponse getDefinitionByTableName(@PathVariable String tableName) {

        return userDefinitionService.getDefinitionByTableName(tableName);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{tableName}")
    void deleteUserDefinition(@PathVariable String tableName) {

        userDefinitionService.deleteDefinition(tableName);

    }

}
