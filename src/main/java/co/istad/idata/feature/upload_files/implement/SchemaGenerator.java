package co.istad.idata.feature.upload_files.implement;

import co.istad.idata.feature.api_generation.json_field.DefinitionSchema;
import co.istad.idata.feature.api_generation.json_field.json_property.Property;
import co.istad.idata.feature.api_generation.json_field.json_property.RelationshipKey;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class SchemaGenerator{

    public static DefinitionSchema generateSchemaFromJson(JsonNode jsonNode, String name, String description) {
        List<Property> properties = new ArrayList<>();
        List<RelationshipKey> keys = new ArrayList<>();
        traverseJson(jsonNode, "", properties);

        // Adding primary key to keys list
        RelationshipKey primaryKey = new RelationshipKey();
        primaryKey.setColumnName("id");
        primaryKey.setPrimaryKey(true);
        primaryKey.setForeignKey(false);
        keys.add(primaryKey);

        return new DefinitionSchema(name, description, null, keys, properties);
    }

    // Traversing JSON nodes to read object and array properties
    private static void traverseJson(JsonNode node, String parent, List<Property> properties) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();
                JsonNode childNode = field.getValue();
                String newParent = parent.isEmpty() ? fieldName : parent + "." + fieldName;

                // Adding property for the current field
                properties.add(new Property(getJsonNodeType(childNode), newParent, false));

                // If the current field is an object or array, recursively traverse it
                if (childNode.isObject() || childNode.isArray()) {
                    traverseJson(childNode, newParent, properties);
                }
            }
        } else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                JsonNode arrayItem = node.get(i);
                String newParent = parent + "[" + i + "]";

                // Adding property for the current array item
                properties.add(new Property(getJsonNodeType(arrayItem), newParent, false));

                // If the current array item is an object or array, recursively traverse it
                if (arrayItem.isObject() || arrayItem.isArray()) {
                    traverseJson(arrayItem, newParent, properties);
                }
            }
        }
    }

    // Determine JSON node type
    private static String getJsonNodeType(JsonNode jsonNode) {
        if (jsonNode.isTextual()) {
            return "string";
        } else if (jsonNode.isNumber()) {
            return "float";
        } else if (jsonNode.isBoolean()) {
            return "boolean";
        } else if (jsonNode.isArray()) {
            return "array";
        } else if (jsonNode.isObject()) {
            return "object";
        } else {
            return "unknown";
        }
    }
}
