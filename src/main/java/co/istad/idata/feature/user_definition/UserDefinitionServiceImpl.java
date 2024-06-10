package co.istad.idata.feature.user_definition;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.domains.UserDefinition;
import co.istad.idata.feature.api_generation.json_field.DefinitionSchema;
import co.istad.idata.feature.api_generation.json_field.json_property.Property;
import co.istad.idata.feature.api_generation.json_field.json_property.RelationshipKey;
import co.istad.idata.feature.user_definition.dto.DefinitionRequest;
import co.istad.idata.feature.user_definition.dto.DefinitionResponse;
import co.istad.idata.mapper.UserDefinitionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDefinitionServiceImpl implements UserDefinitionService{

    private final UserDefinitionRepository userDefinitionRepository;

    private final UserDefinitionMapper userDefinitionMapper;

    @Override
    public void createUserDefinition(DefinitionRequest request) {

        if (userDefinitionRepository.existsByTableName(request.schema().getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Table is already existed"
            );
        }

        UserDefinition userDefinition = new UserDefinition();
        userDefinition.setSchema(request.schema());
        userDefinition.setTableName(request.schema().getName());

        userDefinitionRepository.save(userDefinition);

        List<UserDefinition> definitions = userDefinitionRepository.findAll();

        for (UserDefinition definition : definitions) {

            List<RelationshipKey> keys = definition.getSchema().getKeys();

            // Initialize keys to an empty list if it is null
            if (keys == null) {
                keys = new ArrayList<>();
            }

            for (RelationshipKey key : keys) {

                if (key.isForeignKey() && key.getReferenceTable().equals(userDefinition.getSchema().getName())) {

                    // Fetch the existing definition with the foreign key reference
                    UserDefinition existedDefinition = userDefinitionRepository.findByTableName(definition.getSchema().getName())
                            .orElseThrow(
                                    () -> new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "Definition has not been found"
                                    )
                            );

                    List<Property> existedProperties = existedDefinition.getSchema().getProperties();

                    boolean foreignKeyExists = existedProperties.stream()
                            .anyMatch(property -> property.getColumnName().equals(definition.getSchema().getName()));

                    if (!foreignKeyExists) {
                        // Create a new attribute for the foreign key
                        Property foreignKeyProperty = new Property();
                        foreignKeyProperty.setColumnName(definition.getSchema().getName() + "_id");
                        foreignKeyProperty.setDataType("string");

                        // Add the foreign key attribute to the list of attributes
                        existedProperties.add(foreignKeyProperty);

                        // Update the schema with the new attribute
                        existedDefinition.getSchema().setProperties(existedProperties);

                        // Save the updated definition back to the repository
                        userDefinitionRepository.save(existedDefinition);
                    }

                }
            }
        }
    }


    @Override
    public List<DefinitionResponse> findAll() {

        List<UserDefinition> definitions = userDefinitionRepository.findAll();

        return userDefinitionMapper.toDefinitionResponseList(definitions);

    }

    @Override
    public BasedMessage updateTableName(Long id, String newTableName) {

        UserDefinition existedDefinition = userDefinitionRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Definition has not been found"
                        )
                );

        String oldTableName = existedDefinition.getSchema().getName();

        if (oldTableName.equals(newTableName)) {
            return new BasedMessage(
                    "Enter a new table name!"
            );
        }

        DefinitionSchema existedSchema = existedDefinition.getSchema();

        existedSchema.setName(newTableName);

        userDefinitionRepository.save(existedDefinition);

        return new BasedMessage(
                "Table name has been updated"
        );

    }

    @Override
    public DefinitionResponse getDefinitionByTableName(String tableName) {

        UserDefinition definition = userDefinitionRepository.findByTableName(tableName)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Table has not been found"
                        )
                );


        return userDefinitionMapper.toDefinitionResponse(definition);
    }

    @Override
    public void deleteDefinition(String tableName) {

        UserDefinition existsDefinition = userDefinitionRepository.findByTableName(tableName)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Table has not been found"
                        )
                );

        userDefinitionRepository.delete(existsDefinition);

    }

}
