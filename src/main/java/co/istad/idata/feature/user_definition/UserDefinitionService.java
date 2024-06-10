package co.istad.idata.feature.user_definition;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.feature.user_definition.dto.DefinitionRequest;
import co.istad.idata.feature.user_definition.dto.DefinitionResponse;

import java.util.List;

public interface UserDefinitionService {

    void createUserDefinition(DefinitionRequest definitionRequest);

    List<DefinitionResponse> findAll();

    BasedMessage updateTableName(Long id, String newTableName);

    DefinitionResponse getDefinitionByTableName(String tableName);

    void deleteDefinition(String tableName);
}
